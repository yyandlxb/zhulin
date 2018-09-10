package cn.hlvan.service.admin;

import cn.hlvan.controller.admin.AdminUserController;
import cn.hlvan.security.AuthorizedUser;
import org.jooq.DSLContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static cn.hlvan.constant.UserStatus.AUDUTING_FAIL;
import static cn.hlvan.constant.UserStatus.AUDUTING_SUCCESS;
import static cn.hlvan.constant.UserStatus.DISABLED;
import static cn.hlvan.manager.database.Tables.USER;

@Service
public class AdminService {

    private static Logger logger = LoggerFactory.getLogger(AdminUserController.class);
    private DSLContext dsl;
    @Autowired
    public void setDsl(DSLContext dsl) {
        this.dsl = dsl;
    }

    public Boolean updateSuccess(Integer ids, Integer userId,String result,Byte status) {
        logger.info("审核");
        return dsl.update(USER).set(USER.STATUS,status)
                  .set(USER.RESULT,result)
                  .where(USER.ID.in(ids)).and(USER.PID.eq(userId)).execute() > 0;
    }

    public Integer delete(Integer ids, AuthorizedUser user) {
        logger.info("删除");
        return dsl.update(USER).set(USER.STATUS,DISABLED)
                  .where(USER.ID.in(ids)).and(USER.PID.eq(user.getId())).execute();
    }

}
