package cn.hlvan.controller.admin;

import cn.hlvan.configure.RequestJson;
import cn.hlvan.constant.UserType;
import cn.hlvan.form.UserForm;
import cn.hlvan.manager.database.tables.records.UserRecord;
import cn.hlvan.service.admin.UserService;
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

@RestController("adminAccountController")
@RequestMapping("/user")
public class SuperAdminController {
    private DSLContext dsl;
    private static Logger logger = LoggerFactory.getLogger(SuperAdminController.class);
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
    public Reply addUser(@RequestBody UserForm userForm) {
        UserRecord userRecord = new UserRecord();
        userRecord.setAccount(userForm.getPhoneNumber());
        userRecord.setName(userForm.getName());
        userRecord.setCode(userForm.getCode());
        userRecord.setPassword(DigestUtils.md5Hex(userForm.getPassword()));
        userRecord.setNumber(UUID.randomUUID().toString());
        userRecord.setType(UserType.MANAGER);
        Integer integer = dsl.selectCount().from(USER).where(USER.CODE.eq(userForm.getCode())).fetchOne().value1();
        if (integer > 0)
            return  Reply.fail().message("邀请码已存在");
        boolean b;
        try {
            b = userService.addUser(userRecord);
        }catch (Exception e){
            logger.info("添加管理员",e);
            return Reply.fail().message("添加管理员失败，管理员可能已经被添加");
        }
        if (b){
            return Reply.success();
        }else {
            return Reply.fail().message("用户添加失败");
        }
    }
    @PostMapping("/delete")
    public Reply delete(@RequestJson(value ="id") Integer id) {
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
    public Reply update(@RequestBody UserForm form) {
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

    //把商家或者写手分配至其它管理员
    @PostMapping("/update_admin")
    public Reply distribute(@RequestJson(value = "id") Integer id, @RequestJson(value = "adminId") Integer adminId){

        boolean b = userService.updateAdmin(id,adminId);
        if (b){
            return Reply.success();
        }else {
            return Reply.fail().message("更换管理员失败");
        }

    }

    //列出管理员下的商家与写手
    @GetMapping("/merchant_list")
    public Reply findMerchantAndWriter(Integer id ){
        List<UserRecord> userRecords = dsl.selectFrom(USER).where(USER.PID.eq(id)).fetchInto(UserRecord.class);
        return Reply.success().data(userRecords);

    }


}
