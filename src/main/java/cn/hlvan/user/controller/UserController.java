package cn.hlvan.user.controller;

import cn.hlvan.constant.UserType;
import cn.hlvan.manager.database.tables.records.UserRecord;
import cn.hlvan.security.session.Authenticated;
import cn.hlvan.security.session.SessionManager;
import cn.hlvan.service.sso.Message;
import cn.hlvan.service.sso.UserService;
import cn.hlvan.util.Reply;
import cn.hlvan.view.User;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.jooq.DSLContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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
    private Message message;
    @Autowired
    public void setMessage(Message message) {
        this.message = message;
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
    public Reply signIn(@RequestParam String phoneNumber, @RequestParam String password, HttpServletRequest request) {

        String pw = DigestUtils.md5Hex(password);

        User u = dsl.select(USER.fields())
                    .from(USER)
                    .where(USER.ACCOUNT.eq(phoneNumber))
                    .and(USER.PASSWORD.eq(pw))
                    .and(USER.ENABLED.isTrue())
                    .fetchOneInto(User.class);
        if (null == u){
            return Reply.fail().message("用户名或密码错误");
        }
        sessionManager.bind(request.getSession(), new AuthorizedUser(u.getName(),u.getId(),u.getAccount(),u.getType(),
            u.getEnabled()));
        //登录用户数据存在session中
        HttpSession session = request.getSession(true);
        session.setAttribute(Authenticated.class.getName(),u);

        return Reply.success();
    }
    @PostMapping("/register")
    @ResponseBody
    public Reply register(@RequestParam String phoneNumber,@RequestParam String password,@RequestParam Integer type,
                          @RequestParam String msgid,@RequestParam String code,@RequestParam String validCode){
        Boolean b = message.sendValidSMSCode(msgid, validCode);
        if (b){
            UserRecord userRecord = new UserRecord();
            userRecord.setAccount(phoneNumber);
            userRecord.setPassword(DigestUtils.md5Hex(password));
            UserRecord user = dsl.select(USER.fields()).from(USER).where(USER.CODE.eq(code))
                                 .and(USER.TYPE.eq(UserType.MANAGER)).fetchOneInto(UserRecord.class);
            user.setCode(phoneNumber);
            if (user != null){
                userRecord.setType(type);
                userRecord.setPid(user.getPid());
                userRecord.setNumber(UUID.randomUUID().toString());
                userService.addUser(userRecord);
            }else {
                return Reply.fail().message("邀请码不正确");
            }
        }else {
            return Reply.fail().message("验证码不正确");
        }
        return Reply.fail().message("注册失败");
    }
    @PostMapping("/send_code")
    @ResponseBody
    public Reply register(String phoneNumber){

        String s = message.sendSMSCode(phoneNumber);
        if (StringUtils.isNotBlank(s)){
            return Reply.success().data(s);
        }else {
            return Reply.fail().message("获取验证码失败");
        }

    }

    @PostMapping("/sign_out")
    @ResponseBody
    public Reply signOut(HttpServletRequest request){
        request.getSession(false).invalidate();
        return Reply.success();
    }

    @PostMapping("/password/update")
    @ResponseBody
    public Reply updatePassword(@Authenticated AuthorizedUser user, String srcPassword,String password){

        String pw = DigestUtils.md5Hex(srcPassword);
        User u = dsl.selectFrom(USER)
                    .where(USER.ID.eq(user.getId()))
                    .and(USER.PASSWORD.eq(pw))
                    .and(USER.ENABLED.eq(3))
                    .fetchOneInto(User.class);
        if (null != u){
            String pd = DigestUtils.md5Hex(password);
            userService.modifyPassword(pd,user.getId());
            return Reply.success();

        }else {
            return Reply.fail().message("原密码错误");
        }

    }
    @PostMapping("/password/forget")
    @ResponseBody
    public Reply checkAccount(@RequestParam String phoneNumber,HttpServletRequest request){
        UserRecord userRecord = dsl.select(USER.fields()).from(USER).where(USER.ACCOUNT.eq(phoneNumber))
                                   .and(USER.ENABLED.isTrue())
                                   .fetchOneInto(UserRecord.class);

        request.getSession().setAttribute(NUMBER,phoneNumber);
        if (userRecord != null){
            String s = message.sendSMSCode(phoneNumber);
            if (StringUtils.isNotBlank(s) ){
                return Reply.success().data(s);
            }else {
                return Reply.fail().message("获取验证码失败");
            }
        }else {
            return Reply.fail().message("账号不存在");
        }
    }


    @PostMapping("/reset")
    public Reply resetPassword(HttpServletRequest request,@RequestParam String phoneNumber,String password,
                               String msgid,String validCode){

        String attribute = (String) request.getSession().getAttribute(NUMBER);
        if (null != attribute && attribute.equals(phoneNumber)){

            Boolean aBoolean = message.sendValidSMSCode(msgid, validCode);
            if (aBoolean){
                userService.resetPassword(phoneNumber,password);
                return  Reply.success();
            }else {
                return Reply.fail().message("重置密码失败");
            }
        }else {
            return Reply.fail().message("手机号填写不正确");
        }
    }
}
