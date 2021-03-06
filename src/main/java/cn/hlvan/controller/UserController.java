package cn.hlvan.controller;

import cn.hlvan.constant.UserType;
import cn.hlvan.manager.database.tables.records.UserRecord;
import cn.hlvan.service.sso.UserService;
import cn.hlvan.util.Reply;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static cn.hlvan.manager.database.tables.User.USER;

@RestController("userAccountController")
@RequestMapping("/user")
public class UserController {
    private DSLContext dsl;
    private static Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    public void setDsl(DSLContext dsl) {
        this.dsl = dsl;
    }
    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/list")
    public Reply list( String phoneNumber) {
        List<Condition> list = new ArrayList<>();
        if (StringUtils.isNotBlank(phoneNumber)){
            list.add(USER.ACCOUNT.eq(phoneNumber));
        }
        List<UserRecord> userRecords = dsl.selectFrom(USER).where(list)
                                          .and(USER.TYPE.eq(UserType.MANAGER))
                                          .fetchInto(UserRecord.class);
        return Reply.success().data(userRecords);
    }
    @PostMapping("/add")
    public Reply addUser(@RequestParam String phoneNumber,@RequestParam String name,@RequestParam String password,
                         @RequestParam String code) {
        UserRecord userRecord = new UserRecord();
        userRecord.setAccount(phoneNumber);
        userRecord.setName(name);
        userRecord.setCode(code);
        userRecord.setPassword(DigestUtils.md5Hex(password));
        userRecord.setNumber(UUID.randomUUID().toString());
        userRecord.setType(UserType.MANAGER);
        boolean exists =
            dsl.selectOne()
               .from(USER)
               .where(USER.CODE.eq(code))
               .limit(1)
               .fetchOptional()
               .isPresent();

        if (!exists)
            return  Reply.fail().message("邀请码已存在");
        boolean b = userService.addUser(userRecord);
        if (b){
            return Reply.success();
        }else {
            return Reply.fail().message("用户添加失败");
        }
    }
    @PostMapping("/delete")
    public Reply delete(Integer id) {
        boolean b = userService.delete(id);
        return b ? Reply.success() : Reply.fail().message("删除失败");
    }

    @GetMapping("/detail")
    public Reply detail(Integer id) {
        UserRecord userRecord = dsl.selectFrom(USER).where(USER.ID.eq(id))
                                           .fetchSingleInto(UserRecord.class);
        return Reply.success().data(userRecord);
    }
    @PostMapping("/update")
    public Reply update(UserService.UserForm form) {
        UserRecord userRecord = new UserRecord();
        userRecord.setId(form.getId());
        if (StringUtils.isNotBlank(form.getName())){
            userRecord.setName(form.getName());
        }
        if (StringUtils.isNotBlank(form.getCode())){
            userRecord.setCode(form.getCode());
        }
        if (StringUtils.isNotBlank(form.getPassword())){
            userRecord.setPassword(DigestUtils.md5Hex(form.getPassword()));
        }
        boolean b = userService.update(userRecord);
        return b ? Reply.success() : Reply.fail().message("更新失败");
    }

}
