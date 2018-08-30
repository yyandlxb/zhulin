package cn.hlvan.controller.admin;


import cn.hlvan.manager.database.tables.records.UserRecord;
import cn.hlvan.service.AdminService;
import cn.hlvan.util.Reply;
import org.jooq.DSLContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static cn.hlvan.constant.UserStatus.AWAIT_AUDUTING;
import static cn.hlvan.manager.database.Tables.USER;

@RestController("adminAccountController")
@RequestMapping("/user")
public class AdminController {

    private static Logger logger = LoggerFactory.getLogger(AdminController.class);
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
    public Reply query(){
        List<UserRecord> userRecords = dsl.selectFrom(USER).where(USER.ENABLED.eq(AWAIT_AUDUTING)).fetchInto(UserRecord.class);
        return  Reply.success().data(userRecords);
    }

    /**
     * 审核成功
     */
    @PostMapping("/auditing")
    public Reply auditingSuccess(Integer[] ids){
        Integer b = adminService.updateSuccess(ids);
        return Reply.success().data(b);
    }

    /**
     * 审核失败
     */
    @PostMapping("/auditing")
    public Reply auditingFail(Integer[] ids){
        Integer b = adminService.updateFail(ids);
        return Reply.success().data(b);
    }



}