package cn.hlvan.view;

import cn.hlvan.manager.database.tables.records.PermissionRecord;
import cn.hlvan.manager.database.tables.records.UserRecord;
import lombok.Data;

import java.util.List;

@Data
public class Permission  {

    private List<PermissionRecord> permissionRecord;
    private UserRecord userRecord;
}
