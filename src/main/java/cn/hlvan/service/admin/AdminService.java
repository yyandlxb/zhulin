package cn.hlvan.service.admin;

import cn.hlvan.controller.admin.AdminController;
import cn.hlvan.user.controller.AuthorizedUser;
import org.jooq.DSLContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static cn.hlvan.constant.UserStatus.AUDUTING_FAIL;
import static cn.hlvan.constant.UserStatus.AUDUTING_SUCCESS;
import static cn.hlvan.manager.database.Tables.USER;

@Service
public class AdminService {

    private static Logger logger = LoggerFactory.getLogger(AdminController.class);
    private DSLContext dsl;
    @Autowired
    public void setDsl(DSLContext dsl) {
        this.dsl = dsl;
    }

    public Integer updateSuccess(Integer[] ids, AuthorizedUser user) {
        logger.info("审核成功");
        return dsl.update(USER).set(USER.STATUS,AUDUTING_SUCCESS).where(USER.ID.in(ids)).and(USER.PID.eq(user.getId())).execute();
    }

    public Integer updateFail(Integer[] ids, AuthorizedUser user) {
        logger.info("审核失败");
        return dsl.update(USER).set(USER.STATUS,AUDUTING_FAIL).where(USER.ID.in(ids)).and(USER.PID.eq(user.getId())).execute();
    }
}
