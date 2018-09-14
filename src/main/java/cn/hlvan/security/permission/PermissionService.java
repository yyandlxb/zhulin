package cn.hlvan.security.permission;

import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import static cn.hlvan.manager.database.Tables.PERMISSION;

@Component
public class PermissionService  implements ApplicationRunner {

    private DSLContext dsl;
    @Autowired
    public void setDsl(DSLContext dsl) {
        this.dsl = dsl;
    }

    @Override
    public void run(ApplicationArguments args) {
        for (PermissionEnum permissionEnum : PermissionEnum.values()) {
            dsl.insertInto(PERMISSION).set(PERMISSION.SYSTEM_ID,1)
               .set(PERMISSION.CODE,permissionEnum.name())
               .set(PERMISSION.NAME,permissionEnum.getName())
               .onDuplicateKeyUpdate()
               .set(PERMISSION.DESCRIPTION,"")
               .execute();
        }
    }
}
