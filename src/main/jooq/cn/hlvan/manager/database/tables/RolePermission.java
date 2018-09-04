/*
 * This file is generated by jOOQ.
*/
package cn.hlvan.manager.database.tables;


import cn.hlvan.manager.database.Indexes;
import cn.hlvan.manager.database.Keys;
import cn.hlvan.manager.database.Zhulin;
import cn.hlvan.manager.database.tables.records.RolePermissionRecord;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Identity;
import org.jooq.Index;
import org.jooq.Name;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.TableImpl;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.10.7"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class RolePermission extends TableImpl<RolePermissionRecord> {

    private static final long serialVersionUID = -1426680343;

    /**
     * The reference instance of <code>zhulin.role_permission</code>
     */
    public static final RolePermission ROLE_PERMISSION = new RolePermission();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<RolePermissionRecord> getRecordType() {
        return RolePermissionRecord.class;
    }

    /**
     * The column <code>zhulin.role_permission.id</code>. 角色权限id
     */
    public final TableField<RolePermissionRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).identity(true), this, "角色权限id");

    /**
     * The column <code>zhulin.role_permission.role_id</code>. 角色id
     */
    public final TableField<RolePermissionRecord, Integer> ROLE_ID = createField("role_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "角色id");

    /**
     * The column <code>zhulin.role_permission.permission_id</code>. 权限id
     */
    public final TableField<RolePermissionRecord, Integer> PERMISSION_ID = createField("permission_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "权限id");

    /**
     * The column <code>zhulin.role_permission.created_at</code>.
     */
    public final TableField<RolePermissionRecord, Timestamp> CREATED_AT = createField("created_at", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.field("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "");

    /**
     * Create a <code>zhulin.role_permission</code> table reference
     */
    public RolePermission() {
        this(DSL.name("role_permission"), null);
    }

    /**
     * Create an aliased <code>zhulin.role_permission</code> table reference
     */
    public RolePermission(String alias) {
        this(DSL.name(alias), ROLE_PERMISSION);
    }

    /**
     * Create an aliased <code>zhulin.role_permission</code> table reference
     */
    public RolePermission(Name alias) {
        this(alias, ROLE_PERMISSION);
    }

    private RolePermission(Name alias, Table<RolePermissionRecord> aliased) {
        this(alias, aliased, null);
    }

    private RolePermission(Name alias, Table<RolePermissionRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, "");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Schema getSchema() {
        return Zhulin.ZHULIN;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Index> getIndexes() {
        return Arrays.<Index>asList(Indexes.ROLE_PERMISSION_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<RolePermissionRecord, Integer> getIdentity() {
        return Keys.IDENTITY_ROLE_PERMISSION;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<RolePermissionRecord> getPrimaryKey() {
        return Keys.KEY_ROLE_PERMISSION_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<RolePermissionRecord>> getKeys() {
        return Arrays.<UniqueKey<RolePermissionRecord>>asList(Keys.KEY_ROLE_PERMISSION_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RolePermission as(String alias) {
        return new RolePermission(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RolePermission as(Name alias) {
        return new RolePermission(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public RolePermission rename(String name) {
        return new RolePermission(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public RolePermission rename(Name name) {
        return new RolePermission(name, null);
    }
}
