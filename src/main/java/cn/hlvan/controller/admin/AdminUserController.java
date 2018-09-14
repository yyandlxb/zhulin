package cn.hlvan.controller.admin;

import cn.hlvan.configure.RequestJson;
import cn.hlvan.manager.database.tables.records.UserRecord;
import cn.hlvan.security.session.Authenticated;
import cn.hlvan.service.admin.AdminService;
import cn.hlvan.security.AuthorizedUser;
import cn.hlvan.util.Page;
import cn.hlvan.util.Reply;
import org.apache.commons.lang3.StringUtils;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static cn.hlvan.manager.database.Tables.USER;

@RestController("adminMerchantWriterController")
@RequestMapping("/admin/user")
public class AdminUserController {

    private static Logger logger = LoggerFactory.getLogger(AdminUserController.class);
    private DSLContext dsl;
    @Autowired
    public void setDsl(DSLContext dsl) {
        this.dsl = dsl;
    }
    private AdminService adminService;
    @Autowired
    public void setAdminService(AdminService adminService) {
        this.adminService = adminService;
    }

    /**
     * 查询
     */
    @GetMapping("/list")
    public Reply query( @Authenticated AuthorizedUser user,Pageable pageable,String status,String account){

        List<Condition> conditions = new ArrayList<>();
        if (StringUtils.isNotBlank(status)){
            conditions.add(USER.STATUS.eq(Byte.valueOf(status)));
        }
        if (StringUtils.isNotBlank(account)){
            conditions.add(USER.ACCOUNT.contains(account));
        }
        conditions.add(USER.PID.eq(user.getId()));
        int count = dsl.selectCount().from(USER).where(conditions).fetchOne().value1();
        List<UserRecord> userRecords;
        if (pageable.getOffset() >= count) {
            userRecords = Collections.emptyList();
        } else {
            userRecords = dsl.selectFrom(USER).where(conditions)
                             .limit((int) pageable.getOffset(), pageable.getPageSize())
                             .fetchInto(UserRecord.class);
        }
        return Reply.success().data(new Page<>(userRecords, pageable, count));
    }

    /**
     * 审核成功
     */
    @PostMapping("/auditing")
    public Reply auditingSuccess(@RequestJson(value = "id") Integer id,@RequestJson(value = "result") String result,
                                 @RequestJson(value = "status") Byte status,
                                 @Authenticated AuthorizedUser user){
        boolean b = adminService.updateSuccess(id,user.getId(),result,status);
        if (b){
            return Reply.success();
        }else {
            return Reply.fail().data("审核失败");
        }
    }


    @GetMapping("/detail")
    public Reply userInfo(Integer id){
        UserRecord userRecord = dsl.selectFrom(USER).where(USER.ID.eq(id)).fetchOneInto(UserRecord.class);
        userRecord.setPassword(null);
        return Reply.success().data(userRecord);
    }

    @PostMapping("/delete")
    public Reply delete(@RequestJson(value = "id") Integer id,@Authenticated AuthorizedUser user){
        Integer b = adminService.delete(id,user);
        return Reply.success().data(b);
    }
    @PostMapping("/enable")
    public Reply enable(@RequestJson(value = "id") Integer id,@Authenticated AuthorizedUser user){
        Integer b = adminService.enable(id,user);
        return Reply.success().data(b);
    }
}