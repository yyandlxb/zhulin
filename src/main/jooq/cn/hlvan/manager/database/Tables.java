/*
 * This file is generated by jOOQ.
*/
package cn.hlvan.manager.database;


import cn.hlvan.manager.database.tables.ApplyFinance;
import cn.hlvan.manager.database.tables.LimitTime;
import cn.hlvan.manager.database.tables.Order;
import cn.hlvan.manager.database.tables.OrderEssay;
import cn.hlvan.manager.database.tables.Permission;
import cn.hlvan.manager.database.tables.Picture;
import cn.hlvan.manager.database.tables.Role;
import cn.hlvan.manager.database.tables.RolePermission;
import cn.hlvan.manager.database.tables.TradeRecord;
import cn.hlvan.manager.database.tables.User;
import cn.hlvan.manager.database.tables.UserMoney;
import cn.hlvan.manager.database.tables.UserOrder;
import cn.hlvan.manager.database.tables.UserPermission;
import cn.hlvan.manager.database.tables.UserRole;

import javax.annotation.Generated;


/**
 * Convenience access to all tables in zhulin
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.10.7"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Tables {

    /**
     * The table <code>zhulin.apply_finance</code>.
     */
    public static final ApplyFinance APPLY_FINANCE = cn.hlvan.manager.database.tables.ApplyFinance.APPLY_FINANCE;

    /**
     * 文章领域
     */
    public static final LimitTime LIMIT_TIME = cn.hlvan.manager.database.tables.LimitTime.LIMIT_TIME;

    /**
     * The table <code>zhulin.order</code>.
     */
    public static final Order ORDER = cn.hlvan.manager.database.tables.Order.ORDER;

    /**
     * The table <code>zhulin.order_essay</code>.
     */
    public static final OrderEssay ORDER_ESSAY = cn.hlvan.manager.database.tables.OrderEssay.ORDER_ESSAY;

    /**
     * The table <code>zhulin.permission</code>.
     */
    public static final Permission PERMISSION = cn.hlvan.manager.database.tables.Permission.PERMISSION;

    /**
     * The table <code>zhulin.picture</code>.
     */
    public static final Picture PICTURE = cn.hlvan.manager.database.tables.Picture.PICTURE;

    /**
     * The table <code>zhulin.role</code>.
     */
    public static final Role ROLE = cn.hlvan.manager.database.tables.Role.ROLE;

    /**
     * The table <code>zhulin.role_permission</code>.
     */
    public static final RolePermission ROLE_PERMISSION = cn.hlvan.manager.database.tables.RolePermission.ROLE_PERMISSION;

    /**
     * The table <code>zhulin.trade_record</code>.
     */
    public static final TradeRecord TRADE_RECORD = cn.hlvan.manager.database.tables.TradeRecord.TRADE_RECORD;

    /**
     * The table <code>zhulin.user</code>.
     */
    public static final User USER = cn.hlvan.manager.database.tables.User.USER;

    /**
     * The table <code>zhulin.user_money</code>.
     */
    public static final UserMoney USER_MONEY = cn.hlvan.manager.database.tables.UserMoney.USER_MONEY;

    /**
     * The table <code>zhulin.user_order</code>.
     */
    public static final UserOrder USER_ORDER = cn.hlvan.manager.database.tables.UserOrder.USER_ORDER;

    /**
     * The table <code>zhulin.user_permission</code>.
     */
    public static final UserPermission USER_PERMISSION = cn.hlvan.manager.database.tables.UserPermission.USER_PERMISSION;

    /**
     * The table <code>zhulin.user_role</code>.
     */
    public static final UserRole USER_ROLE = cn.hlvan.manager.database.tables.UserRole.USER_ROLE;
}
