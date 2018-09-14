package cn.hlvan.service;

import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static cn.hlvan.manager.database.Tables.*;
import static cn.hlvan.manager.database.tables.UserRole.USER_ROLE;

@Service
public class PermissionService {
    private DSLContext dsl;

    @Autowired
    public void setDsl(DSLContext dsl) {
        this.dsl = dsl;
    }

    public boolean checkPermission(Integer userId, String code) {
        return dsl.selectOne().from(USER_ROLE).innerJoin(ROLE).on(USER_ROLE.ROLE_ID.eq(ROLE.ID))
                  .innerJoin(ROLE_PERMISSION).on(ROLE_PERMISSION.ROLE_ID.eq(ROLE.ID))
                  .innerJoin(PERMISSION).on(PERMISSION.ID.eq(ROLE_PERMISSION.PERMISSION_ID))
                  .and(PERMISSION.CODE.eq(code)).and(USER_ROLE.USER_ID.eq(userId)).fetchOptional().isPresent();
    }

}
