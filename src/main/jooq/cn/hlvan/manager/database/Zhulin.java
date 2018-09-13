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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Catalog;
import org.jooq.Table;
import org.jooq.impl.SchemaImpl;


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
public class Zhulin extends SchemaImpl {

    private static final long serialVersionUID = 722425047;

    /**
     * The reference instance of <code>zhulin</code>
     */
    public static final Zhulin ZHULIN = new Zhulin();

    /**
     * The table <code>zhulin.apply_finance</code>.
     */
    public final ApplyFinance APPLY_FINANCE = cn.hlvan.manager.database.tables.ApplyFinance.APPLY_FINANCE;

    /**
     * 文章领域
     */
    public final LimitTime LIMIT_TIME = cn.hlvan.manager.database.tables.LimitTime.LIMIT_TIME;

    /**
     * The table <code>zhulin.order</code>.
     */
    public final Order ORDER = cn.hlvan.manager.database.tables.Order.ORDER;

    /**
     * The table <code>zhulin.order_essay</code>.
     */
    public final OrderEssay ORDER_ESSAY = cn.hlvan.manager.database.tables.OrderEssay.ORDER_ESSAY;

    /**
     * The table <code>zhulin.permission</code>.
     */
    public final Permission PERMISSION = cn.hlvan.manager.database.tables.Permission.PERMISSION;

    /**
     * The table <code>zhulin.picture</code>.
     */
    public final Picture PICTURE = cn.hlvan.manager.database.tables.Picture.PICTURE;

    /**
     * The table <code>zhulin.role</code>.
     */
    public final Role ROLE = cn.hlvan.manager.database.tables.Role.ROLE;

    /**
     * The table <code>zhulin.role_permission</code>.
     */
    public final RolePermission ROLE_PERMISSION = cn.hlvan.manager.database.tables.RolePermission.ROLE_PERMISSION;

    /**
     * The table <code>zhulin.trade_record</code>.
     */
    public final TradeRecord TRADE_RECORD = cn.hlvan.manager.database.tables.TradeRecord.TRADE_RECORD;

    /**
     * The table <code>zhulin.user</code>.
     */
    public final User USER = cn.hlvan.manager.database.tables.User.USER;

    /**
     * The table <code>zhulin.user_money</code>.
     */
    public final UserMoney USER_MONEY = cn.hlvan.manager.database.tables.UserMoney.USER_MONEY;

    /**
     * The table <code>zhulin.user_order</code>.
     */
    public final UserOrder USER_ORDER = cn.hlvan.manager.database.tables.UserOrder.USER_ORDER;

    /**
     * The table <code>zhulin.user_permission</code>.
     */
    public final UserPermission USER_PERMISSION = cn.hlvan.manager.database.tables.UserPermission.USER_PERMISSION;

    /**
     * The table <code>zhulin.user_role</code>.
     */
    public final UserRole USER_ROLE = cn.hlvan.manager.database.tables.UserRole.USER_ROLE;

    /**
     * No further instances allowed
     */
    private Zhulin() {
        super("zhulin", null);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public Catalog getCatalog() {
        return DefaultCatalog.DEFAULT_CATALOG;
    }

    @Override
    public final List<Table<?>> getTables() {
        List result = new ArrayList();
        result.addAll(getTables0());
        return result;
    }

    private final List<Table<?>> getTables0() {
        return Arrays.<Table<?>>asList(
            ApplyFinance.APPLY_FINANCE,
            LimitTime.LIMIT_TIME,
            Order.ORDER,
            OrderEssay.ORDER_ESSAY,
            Permission.PERMISSION,
            Picture.PICTURE,
            Role.ROLE,
            RolePermission.ROLE_PERMISSION,
            TradeRecord.TRADE_RECORD,
            User.USER,
            UserMoney.USER_MONEY,
            UserOrder.USER_ORDER,
            UserPermission.USER_PERMISSION,
            UserRole.USER_ROLE);
    }
}
