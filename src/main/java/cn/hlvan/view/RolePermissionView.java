package cn.hlvan.view;

import cn.hlvan.manager.database.tables.records.PermissionRecord;
import lombok.Data;

import java.util.List;

@Data
public class RolePermissionView {
    private List<PermissionRecord> permissionRecords;
}
