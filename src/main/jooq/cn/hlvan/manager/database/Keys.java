/*
 * This file is generated by jOOQ.
*/
package cn.hlvan.manager.database;


import cn.hlvan.manager.database.tables.LimitTime;
import cn.hlvan.manager.database.tables.Order;
import cn.hlvan.manager.database.tables.OrderEssay;
import cn.hlvan.manager.database.tables.Permission;
import cn.hlvan.manager.database.tables.Picture;
import cn.hlvan.manager.database.tables.Role;
import cn.hlvan.manager.database.tables.RolePermission;
import cn.hlvan.manager.database.tables.User;
import cn.hlvan.manager.database.tables.UserOrder;
import cn.hlvan.manager.database.tables.UserPermission;
import cn.hlvan.manager.database.tables.UserRole;
import cn.hlvan.manager.database.tables.records.LimitTimeRecord;
import cn.hlvan.manager.database.tables.records.OrderEssayRecord;
import cn.hlvan.manager.database.tables.records.OrderRecord;
import cn.hlvan.manager.database.tables.records.PermissionRecord;
import cn.hlvan.manager.database.tables.records.PictureRecord;
import cn.hlvan.manager.database.tables.records.RolePermissionRecord;
import cn.hlvan.manager.database.tables.records.RoleRecord;
import cn.hlvan.manager.database.tables.records.UserOrderRecord;
import cn.hlvan.manager.database.tables.records.UserPermissionRecord;
import cn.hlvan.manager.database.tables.records.UserRecord;
import cn.hlvan.manager.database.tables.records.UserRoleRecord;

import javax.annotation.Generated;

import org.jooq.Identity;
import org.jooq.UniqueKey;
import org.jooq.impl.Internal;


/**
 * A class modelling foreign key relationships and constraints of tables of 
 * the <code>zhulin</code> schema.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.10.7"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Keys {

    // -------------------------------------------------------------------------
    // IDENTITY definitions
    // -------------------------------------------------------------------------

    public static final Identity<LimitTimeRecord, Integer> IDENTITY_LIMIT_TIME = Identities0.IDENTITY_LIMIT_TIME;
    public static final Identity<OrderRecord, Integer> IDENTITY_ORDER = Identities0.IDENTITY_ORDER;
    public static final Identity<OrderEssayRecord, Integer> IDENTITY_ORDER_ESSAY = Identities0.IDENTITY_ORDER_ESSAY;
    public static final Identity<PermissionRecord, Integer> IDENTITY_PERMISSION = Identities0.IDENTITY_PERMISSION;
    public static final Identity<PictureRecord, Integer> IDENTITY_PICTURE = Identities0.IDENTITY_PICTURE;
    public static final Identity<RoleRecord, Integer> IDENTITY_ROLE = Identities0.IDENTITY_ROLE;
    public static final Identity<RolePermissionRecord, Integer> IDENTITY_ROLE_PERMISSION = Identities0.IDENTITY_ROLE_PERMISSION;
    public static final Identity<UserRecord, Integer> IDENTITY_USER = Identities0.IDENTITY_USER;
    public static final Identity<UserOrderRecord, Integer> IDENTITY_USER_ORDER = Identities0.IDENTITY_USER_ORDER;
    public static final Identity<UserPermissionRecord, Integer> IDENTITY_USER_PERMISSION = Identities0.IDENTITY_USER_PERMISSION;
    public static final Identity<UserRoleRecord, Integer> IDENTITY_USER_ROLE = Identities0.IDENTITY_USER_ROLE;

    // -------------------------------------------------------------------------
    // UNIQUE and PRIMARY KEY definitions
    // -------------------------------------------------------------------------

    public static final UniqueKey<LimitTimeRecord> KEY_LIMIT_TIME_PRIMARY = UniqueKeys0.KEY_LIMIT_TIME_PRIMARY;
    public static final UniqueKey<OrderRecord> KEY_ORDER_PRIMARY = UniqueKeys0.KEY_ORDER_PRIMARY;
    public static final UniqueKey<OrderEssayRecord> KEY_ORDER_ESSAY_PRIMARY = UniqueKeys0.KEY_ORDER_ESSAY_PRIMARY;
    public static final UniqueKey<PermissionRecord> KEY_PERMISSION_PRIMARY = UniqueKeys0.KEY_PERMISSION_PRIMARY;
    public static final UniqueKey<PermissionRecord> KEY_PERMISSION_UK_CODE_SYSTEM = UniqueKeys0.KEY_PERMISSION_UK_CODE_SYSTEM;
    public static final UniqueKey<PictureRecord> KEY_PICTURE_PRIMARY = UniqueKeys0.KEY_PICTURE_PRIMARY;
    public static final UniqueKey<RoleRecord> KEY_ROLE_PRIMARY = UniqueKeys0.KEY_ROLE_PRIMARY;
    public static final UniqueKey<RolePermissionRecord> KEY_ROLE_PERMISSION_PRIMARY = UniqueKeys0.KEY_ROLE_PERMISSION_PRIMARY;
    public static final UniqueKey<UserRecord> KEY_USER_PRIMARY = UniqueKeys0.KEY_USER_PRIMARY;
    public static final UniqueKey<UserRecord> KEY_USER_UK_ACCOUNT = UniqueKeys0.KEY_USER_UK_ACCOUNT;
    public static final UniqueKey<UserRecord> KEY_USER_UK_NUMBER = UniqueKeys0.KEY_USER_UK_NUMBER;
    public static final UniqueKey<UserOrderRecord> KEY_USER_ORDER_PRIMARY = UniqueKeys0.KEY_USER_ORDER_PRIMARY;
    public static final UniqueKey<UserPermissionRecord> KEY_USER_PERMISSION_PRIMARY = UniqueKeys0.KEY_USER_PERMISSION_PRIMARY;
    public static final UniqueKey<UserRoleRecord> KEY_USER_ROLE_PRIMARY = UniqueKeys0.KEY_USER_ROLE_PRIMARY;

    // -------------------------------------------------------------------------
    // FOREIGN KEY definitions
    // -------------------------------------------------------------------------


    // -------------------------------------------------------------------------
    // [#1459] distribute members to avoid static initialisers > 64kb
    // -------------------------------------------------------------------------

    private static class Identities0 {
        public static Identity<LimitTimeRecord, Integer> IDENTITY_LIMIT_TIME = Internal.createIdentity(LimitTime.LIMIT_TIME, LimitTime.LIMIT_TIME.ID);
        public static Identity<OrderRecord, Integer> IDENTITY_ORDER = Internal.createIdentity(Order.ORDER, Order.ORDER.ID);
        public static Identity<OrderEssayRecord, Integer> IDENTITY_ORDER_ESSAY = Internal.createIdentity(OrderEssay.ORDER_ESSAY, OrderEssay.ORDER_ESSAY.ID);
        public static Identity<PermissionRecord, Integer> IDENTITY_PERMISSION = Internal.createIdentity(Permission.PERMISSION, Permission.PERMISSION.ID);
        public static Identity<PictureRecord, Integer> IDENTITY_PICTURE = Internal.createIdentity(Picture.PICTURE, Picture.PICTURE.ID);
        public static Identity<RoleRecord, Integer> IDENTITY_ROLE = Internal.createIdentity(Role.ROLE, Role.ROLE.ID);
        public static Identity<RolePermissionRecord, Integer> IDENTITY_ROLE_PERMISSION = Internal.createIdentity(RolePermission.ROLE_PERMISSION, RolePermission.ROLE_PERMISSION.ID);
        public static Identity<UserRecord, Integer> IDENTITY_USER = Internal.createIdentity(User.USER, User.USER.ID);
        public static Identity<UserOrderRecord, Integer> IDENTITY_USER_ORDER = Internal.createIdentity(UserOrder.USER_ORDER, UserOrder.USER_ORDER.ID);
        public static Identity<UserPermissionRecord, Integer> IDENTITY_USER_PERMISSION = Internal.createIdentity(UserPermission.USER_PERMISSION, UserPermission.USER_PERMISSION.ID);
        public static Identity<UserRoleRecord, Integer> IDENTITY_USER_ROLE = Internal.createIdentity(UserRole.USER_ROLE, UserRole.USER_ROLE.ID);
    }

    private static class UniqueKeys0 {
        public static final UniqueKey<LimitTimeRecord> KEY_LIMIT_TIME_PRIMARY = Internal.createUniqueKey(LimitTime.LIMIT_TIME, "KEY_limit_time_PRIMARY", LimitTime.LIMIT_TIME.ID);
        public static final UniqueKey<OrderRecord> KEY_ORDER_PRIMARY = Internal.createUniqueKey(Order.ORDER, "KEY_order_PRIMARY", Order.ORDER.ID);
        public static final UniqueKey<OrderEssayRecord> KEY_ORDER_ESSAY_PRIMARY = Internal.createUniqueKey(OrderEssay.ORDER_ESSAY, "KEY_order_essay_PRIMARY", OrderEssay.ORDER_ESSAY.ID);
        public static final UniqueKey<PermissionRecord> KEY_PERMISSION_PRIMARY = Internal.createUniqueKey(Permission.PERMISSION, "KEY_permission_PRIMARY", Permission.PERMISSION.ID);
        public static final UniqueKey<PermissionRecord> KEY_PERMISSION_UK_CODE_SYSTEM = Internal.createUniqueKey(Permission.PERMISSION, "KEY_permission_uk_code_system", Permission.PERMISSION.CODE, Permission.PERMISSION.SYSTEM_ID);
        public static final UniqueKey<PictureRecord> KEY_PICTURE_PRIMARY = Internal.createUniqueKey(Picture.PICTURE, "KEY_picture_PRIMARY", Picture.PICTURE.ID);
        public static final UniqueKey<RoleRecord> KEY_ROLE_PRIMARY = Internal.createUniqueKey(Role.ROLE, "KEY_role_PRIMARY", Role.ROLE.ID);
        public static final UniqueKey<RolePermissionRecord> KEY_ROLE_PERMISSION_PRIMARY = Internal.createUniqueKey(RolePermission.ROLE_PERMISSION, "KEY_role_permission_PRIMARY", RolePermission.ROLE_PERMISSION.ID);
        public static final UniqueKey<UserRecord> KEY_USER_PRIMARY = Internal.createUniqueKey(User.USER, "KEY_user_PRIMARY", User.USER.ID);
        public static final UniqueKey<UserRecord> KEY_USER_UK_ACCOUNT = Internal.createUniqueKey(User.USER, "KEY_user_uk_account", User.USER.ACCOUNT);
        public static final UniqueKey<UserRecord> KEY_USER_UK_NUMBER = Internal.createUniqueKey(User.USER, "KEY_user_uk_number", User.USER.NUMBER);
        public static final UniqueKey<UserOrderRecord> KEY_USER_ORDER_PRIMARY = Internal.createUniqueKey(UserOrder.USER_ORDER, "KEY_user_order_PRIMARY", UserOrder.USER_ORDER.ID);
        public static final UniqueKey<UserPermissionRecord> KEY_USER_PERMISSION_PRIMARY = Internal.createUniqueKey(UserPermission.USER_PERMISSION, "KEY_user_permission_PRIMARY", UserPermission.USER_PERMISSION.ID);
        public static final UniqueKey<UserRoleRecord> KEY_USER_ROLE_PRIMARY = Internal.createUniqueKey(UserRole.USER_ROLE, "KEY_user_role_PRIMARY", UserRole.USER_ROLE.ID);
    }
}
