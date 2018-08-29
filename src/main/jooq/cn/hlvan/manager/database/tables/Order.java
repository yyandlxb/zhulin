/*
 * This file is generated by jOOQ.
*/
package cn.hlvan.manager.database.tables;


import cn.hlvan.manager.database.Indexes;
import cn.hlvan.manager.database.Keys;
import cn.hlvan.manager.database.Zhulin;
import cn.hlvan.manager.database.tables.records.OrderRecord;

import java.math.BigDecimal;
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
public class Order extends TableImpl<OrderRecord> {

    private static final long serialVersionUID = -1917741736;

    /**
     * The reference instance of <code>zhulin.order</code>
     */
    public static final Order ORDER = new Order();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<OrderRecord> getRecordType() {
        return OrderRecord.class;
    }

    /**
     * The column <code>zhulin.order.id</code>.
     */
    public final TableField<OrderRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).identity(true), this, "");

    /**
     * The column <code>zhulin.order.order_code</code>. 订单号
     */
    public final TableField<OrderRecord, String> ORDER_CODE = createField("order_code", org.jooq.impl.SQLDataType.VARCHAR(255), this, "订单号");

    /**
     * The column <code>zhulin.order.order_status</code>. 1：待接单 2：已接单 3：待审核 4：待点评 5：商家已完成（已打款）6：取消 7：关闭 8：管理员已完成（已打款）
     */
    public final TableField<OrderRecord, String> ORDER_STATUS = createField("order_status", org.jooq.impl.SQLDataType.VARCHAR(10).defaultValue(org.jooq.impl.DSL.inline("1", org.jooq.impl.SQLDataType.VARCHAR)), this, "1：待接单 2：已接单 3：待审核 4：待点评 5：商家已完成（已打款）6：取消 7：关闭 8：管理员已完成（已打款）");

    /**
     * The column <code>zhulin.order.pay_type</code>. 1、微信 2、支付宝 3、银联 4、余额 5、现金 6、chinaPay
     */
    public final TableField<OrderRecord, String> PAY_TYPE = createField("pay_type", org.jooq.impl.SQLDataType.VARCHAR(10), this, "1、微信 2、支付宝 3、银联 4、余额 5、现金 6、chinaPay");

    /**
     * The column <code>zhulin.order.user_id</code>. 用户id
     */
    public final TableField<OrderRecord, Integer> USER_ID = createField("user_id", org.jooq.impl.SQLDataType.INTEGER, this, "用户id");

    /**
     * The column <code>zhulin.order.total</code>. 数量
     */
    public final TableField<OrderRecord, Integer> TOTAL = createField("total", org.jooq.impl.SQLDataType.INTEGER.defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "数量");

    /**
     * The column <code>zhulin.order.merchant_price</code>. 商家价格
     */
    public final TableField<OrderRecord, BigDecimal> MERCHANT_PRICE = createField("merchant_price", org.jooq.impl.SQLDataType.DECIMAL(20, 2).defaultValue(org.jooq.impl.DSL.inline("0.00", org.jooq.impl.SQLDataType.DECIMAL)), this, "商家价格");

    /**
     * The column <code>zhulin.order.admin_price</code>. 管理员价格
     */
    public final TableField<OrderRecord, BigDecimal> ADMIN_PRICE = createField("admin_price", org.jooq.impl.SQLDataType.DECIMAL(20, 2).defaultValue(org.jooq.impl.DSL.inline("0.00", org.jooq.impl.SQLDataType.DECIMAL)), this, "管理员价格");

    /**
     * The column <code>zhulin.order.eassy_type</code>. 文章领域
     */
    public final TableField<OrderRecord, String> EASSY_TYPE = createField("eassy_type", org.jooq.impl.SQLDataType.VARCHAR(255).nullable(false), this, "文章领域");

    /**
     * The column <code>zhulin.order.notes</code>. 备注
     */
    public final TableField<OrderRecord, String> NOTES = createField("notes", org.jooq.impl.SQLDataType.VARCHAR(1024), this, "备注");

    /**
     * The column <code>zhulin.order.created_at</code>.
     */
    public final TableField<OrderRecord, Timestamp> CREATED_AT = createField("created_at", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.field("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "");

    /**
     * The column <code>zhulin.order.updated_at</code>.
     */
    public final TableField<OrderRecord, Timestamp> UPDATED_AT = createField("updated_at", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.field("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "");

    /**
     * The column <code>zhulin.order.order_title</code>. 文章标题
     */
    public final TableField<OrderRecord, String> ORDER_TITLE = createField("order_title", org.jooq.impl.SQLDataType.VARCHAR(255), this, "文章标题");

    /**
     * The column <code>zhulin.order.original_level</code>. 原创度
     */
    public final TableField<OrderRecord, Double> ORIGINAL_LEVEL = createField("original_level", org.jooq.impl.SQLDataType.DOUBLE.defaultValue(org.jooq.impl.DSL.inline("0.00", org.jooq.impl.SQLDataType.DOUBLE)), this, "原创度");

    /**
     * The column <code>zhulin.order.picture</code>. 图片数量要求
     */
    public final TableField<OrderRecord, Integer> PICTURE = createField("picture", org.jooq.impl.SQLDataType.INTEGER, this, "图片数量要求");

    /**
     * The column <code>zhulin.order.type</code>. 0-流量文，1-养号文
     */
    public final TableField<OrderRecord, Byte> TYPE = createField("type", org.jooq.impl.SQLDataType.TINYINT, this, "0-流量文，1-养号文");

    /**
     * The column <code>zhulin.order.end_time</code>. 截止日期
     */
    public final TableField<OrderRecord, Timestamp> END_TIME = createField("end_time", org.jooq.impl.SQLDataType.TIMESTAMP, this, "截止日期");

    /**
     * The column <code>zhulin.order.require</code>. 要求
     */
    public final TableField<OrderRecord, String> REQUIRE = createField("require", org.jooq.impl.SQLDataType.VARCHAR(1024), this, "要求");

    /**
     * The column <code>zhulin.order.eassy_total</code>. 文章数量
     */
    public final TableField<OrderRecord, Integer> EASSY_TOTAL = createField("eassy_total", org.jooq.impl.SQLDataType.INTEGER, this, "文章数量");

    /**
     * The column <code>zhulin.order.word_count</code>. 文章字数
     */
    public final TableField<OrderRecord, Integer> WORD_COUNT = createField("word_count", org.jooq.impl.SQLDataType.INTEGER, this, "文章字数");

    /**
     * The column <code>zhulin.order.open_user</code>. 指定用户id
     */
    public final TableField<OrderRecord, Integer> OPEN_USER = createField("open_user", org.jooq.impl.SQLDataType.INTEGER, this, "指定用户id");

    /**
     * Create a <code>zhulin.order</code> table reference
     */
    public Order() {
        this(DSL.name("order"), null);
    }

    /**
     * Create an aliased <code>zhulin.order</code> table reference
     */
    public Order(String alias) {
        this(DSL.name(alias), ORDER);
    }

    /**
     * Create an aliased <code>zhulin.order</code> table reference
     */
    public Order(Name alias) {
        this(alias, ORDER);
    }

    private Order(Name alias, Table<OrderRecord> aliased) {
        this(alias, aliased, null);
    }

    private Order(Name alias, Table<OrderRecord> aliased, Field<?>[] parameters) {
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
        return Arrays.<Index>asList(Indexes.ORDER_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<OrderRecord, Integer> getIdentity() {
        return Keys.IDENTITY_ORDER;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<OrderRecord> getPrimaryKey() {
        return Keys.KEY_ORDER_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<OrderRecord>> getKeys() {
        return Arrays.<UniqueKey<OrderRecord>>asList(Keys.KEY_ORDER_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Order as(String alias) {
        return new Order(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Order as(Name alias) {
        return new Order(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public Order rename(String name) {
        return new Order(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Order rename(Name name) {
        return new Order(name, null);
    }
}