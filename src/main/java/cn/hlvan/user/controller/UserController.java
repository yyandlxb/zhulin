package cn.hlvan.user.controller;

import cn.hlvan.configure.RequestJson;
import cn.hlvan.constant.UserStatus;
import cn.hlvan.constant.UserType;
import cn.hlvan.form.UserMessage;
import cn.hlvan.manager.database.tables.records.UserRecord;
import cn.hlvan.security.AuthorizedUser;
import cn.hlvan.security.session.Authenticated;
import cn.hlvan.security.session.SessionManager;
import cn.hlvan.service.Message;
import cn.hlvan.service.admin.UserService;
import cn.hlvan.util.Reply;
import cn.hlvan.view.User;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.jooq.DSLContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.UUID;

import static cn.hlvan.manager.database.tables.User.USER;

@Controller("userController")
@RequestMapping("/user")
public class UserController {
    private static Logger logger = LoggerFactory.getLogger(UserController.class);
    private static final String NUMBER = "number";
    private DSLContext dsl;
    private SessionManager sessionManager;
    private UserService userService;
    @Value("${file.path}")
    private String path;
    private Message message;
    private int sessionTimeout;
    @Value("${session.timeout.merchant-web}")
    public void setSessionTimeout(int sessionTimeout) {
        this.sessionTimeout = sessionTimeout;
    }
    @Autowired
    public void setMessage(Message message) {
        this.message = message;
    }

    private ResourceLoader resourceLoader;

    @Autowired
    public UserController(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @Autowired
    public void setDsl(DSLContext dsl) {
        this.dsl = dsl;
    }

    @Autowired
    public void setSessionManager(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/sign_in")
    @ResponseBody
    public Reply signIn(@RequestBody User user, HttpServletRequest request, HttpServletResponse response) {

        String pw = DigestUtils.md5Hex(user.getPassword());

        User u = dsl.select(USER.fields())
                    .from(USER)
                    .where(USER.ACCOUNT.eq(user.getPhoneNumber()))
                    .and(USER.PASSWORD.eq(pw))
                    .and(USER.STATUS.notEqual(UserStatus.DISABLED))
                    .fetchOneInto(User.class);
        if (null == u) {
            return Reply.fail().message("用户名或密码错误");
        }
        sessionManager.bind(request.getSession(true), new AuthorizedUser(u.getName(), u.getId(), u.getAccount(),
            Integer.parseInt(u.getType()), u.getStatus(),u.getPid()));
        request.getSession().setMaxInactiveInterval(sessionTimeout);

        return Reply.success();
    }

    @PostMapping("/register")
    @ResponseBody
    public Reply register(@RequestBody User user) {
        Boolean b = message.sendValidSMSCode(user.getMsgid(), user.getValidCode());
        if (b) {
            UserRecord userRecord = new UserRecord();
            userRecord.setAccount(user.getPhoneNumber());
            userRecord.setPassword(DigestUtils.md5Hex(user.getPassword()));
            UserRecord userR = dsl.select(USER.fields()).from(USER).where(USER.CODE.eq(user.getCode()))
                                  .and(USER.TYPE.eq(UserType.MANAGER)).fetchOneInto(UserRecord.class);
            if (null != userR){
                user.setCode(user.getPhoneNumber());
                userRecord.setType(Byte.valueOf(user.getType()));
                userRecord.setPid(userR.getPid());
                userRecord.setName(user.getName());
                userRecord.setNumber(UUID.randomUUID().toString());
                userService.addUser(userRecord);
            }else {
                return Reply.fail().message("邀请码错误");
            }
        } else {
            return Reply.fail().message("验证码不正确");
        }
        return Reply.fail().message("注册失败");
    }

    @PostMapping(value = "/send_code", consumes = "application/json")
    @ResponseBody
    public Reply registerCode(@RequestBody User user) {

        String s = message.sendSMSCode(user.getPhoneNumber());
        if (StringUtils.isNotBlank(s)) {
            return Reply.success().data(s);
        } else {
            return Reply.fail().message("获取验证码失败");
        }

    }

    @PostMapping("/sign_out")
    @ResponseBody
    public Reply signOut(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (null == session) {
            return Reply.fail().message("退出失败");
        } else {
            request.getSession(false).invalidate();
            return Reply.success();
        }
    }

    @PostMapping("/password/update")
    @ResponseBody
    public Reply updatePassword(@Authenticated AuthorizedUser user, @RequestJson(value = "srcPassword") String srcPassword,
                                @RequestJson(value = "password") String password) {
        String pw = DigestUtils.md5Hex(srcPassword);
        User u = dsl.selectFrom(USER)
                    .where(USER.ID.eq(user.getId()))
                    .and(USER.PASSWORD.eq(pw))
                    .fetchOneInto(User.class);
        if (null != u) {
            String pd = DigestUtils.md5Hex(password);
            userService.modifyPassword(pd, user.getId());
            return Reply.success();

        } else {
            return Reply.fail().message("原密码错误");
        }

    }

    @PostMapping("/password/forget")
    @ResponseBody
    public Reply checkAccount(@RequestJson(value = "phoneNumber") String phoneNumber, HttpServletRequest request) {
        UserRecord userRecord = dsl.select(USER.fields()).from(USER).where(USER.ACCOUNT.eq(phoneNumber))
                                   .and(USER.STATUS.eq(UserStatus.AUDUTING_SUCCESS))
                                   .fetchOneInto(UserRecord.class);

        request.getSession().setAttribute(NUMBER, phoneNumber);
        if (userRecord != null) {
            String s = message.sendSMSCode(phoneNumber);
            if (StringUtils.isNotBlank(s)) {
                return Reply.success().data(s);
            } else {
                return Reply.fail().message("获取验证码失败");
            }
        } else {
            return Reply.fail().message("账号不存在,或被禁用");
        }
    }

    @PostMapping("/reset")
    @ResponseBody
    public Reply resetPassword(HttpServletRequest request,@RequestJson(value = "phoneNumber") String phoneNumber,
                               @RequestJson(value = "password") String password,@RequestJson(value = "msgid") String msgid,
                               @RequestJson(value = "validCode") String validCode) {

        String attribute = (String) request.getSession().getAttribute(NUMBER);
        if (null != attribute && attribute.equals(phoneNumber)) {

            Boolean aBoolean = message.sendValidSMSCode(msgid, validCode);
            if (aBoolean) {
                userService.resetPassword(phoneNumber, password);
                return Reply.success();
            } else {
                return Reply.fail().message("重置密码失败");
            }
        } else {
            return Reply.fail().message("手机号填写不正确");
        }
    }

    @PostMapping("/add_message")
    @ResponseBody
    public Reply addMessage(@RequestBody UserMessage userMessage,
                            @Authenticated AuthorizedUser user) {

        UserRecord userRecord = new UserRecord();
        userRecord.from(userMessage);
        userRecord.setId(user.getId());
        userRecord.setStatus(UserStatus.AWAIT_AUDUTING);
        boolean b = userService.updateUser(userRecord);

        if (b) {
            return Reply.success();
        } else {
            return Reply.fail().message("添加信息失败");
        }
    }

    @GetMapping("/info")
    @ResponseBody
    public Reply userInfo(@Authenticated AuthorizedUser user) {
        UserRecord userRecord = dsl.selectFrom(USER).where(USER.ID.eq(user.getId())).fetchSingleInto(UserRecord.class);
        userRecord.setPassword(null);
        return Reply.success().data(userRecord);
    }

    @GetMapping("/file/{fileName}")
    public ResponseEntity preview(@PathVariable String fileName) {
        try {
            // 由于是读取本机的文件，file是一定要加上的， path是在application配置文件中的路径
            return ResponseEntity.ok(resourceLoader.getResource("file:" + path+fileName));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
    @PostMapping("/file/upload")
    @ResponseBody
    public Reply preview(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return Reply.fail().message("上传文件失败");
        }
        String fileName = System.currentTimeMillis()+file.getOriginalFilename();
        String filePathName = path + fileName;
        File dest = new File(filePathName);
        if (!dest.getParentFile().exists()) { //判断文件父目录是否存在
            dest.getParentFile().mkdir();
        }
        try {
            file.transferTo(dest); //保存文件
        } catch (IllegalStateException | IOException e) {
            logger.info("上传文件失败", e);
        }

        return Reply.success().data(fileName);
    }

}
