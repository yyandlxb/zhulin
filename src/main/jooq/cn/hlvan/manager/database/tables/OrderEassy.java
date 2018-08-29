/*
 * This file is generated by jOOQ.
*/
package cn.hlvan.manager.database.tables;


import cn.hlvan.manager.database.Indexes;
import cn.hlvan.manager.database.Keys;
import cn.hlvan.manager.database.Zhulin;
import cn.hlvan.manager.database.tables.records.OrderEassyRecord;

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
public class OrderEassy extends TableImpl<OrderEassyRecord> {

    private static final long serialVersionUID = -1381707442;

    /**
     * The reference instance of <code>zhulin.order_eassy</code>
     */
    public static final OrderEassy ORDER_EASSY = new OrderEassy();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<OrderEassyRecord> getRecordType() {
        return OrderEassyRecord.class;
    }

    /**
     * The column <code>zhulin.order_eassy.id</code>.
     */
    public final TableField<OrderEassyRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).identity(true), this, "");

    /**
     * The column <code>zhulin.order_eassy.user_order_id</code>. 用户订单号id
     */
    public final TableField<OrderEassyRecord, Integer> USER_ORDER_ID = createField("user_order_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "用户订单号id");

    /**
     * The column <code>zhulin.order_eassy.order_code</code>. 订单号
     */
    public final TableField<OrderEassyRecord, String> ORDER_CODE = createField("order_code", org.jooq.impl.SQLDataType.VARCHAR(255), this, "订单号");

    /**
     * The column <code>zhulin.order_eassy.eassy_tital</code>. 文章标题
     */
    public final TableField<OrderEassyRecord, String> EASSY_TITAL = createField("eassy_tital", org.jooq.impl.SQLDataType.VARCHAR(255), this, "文章标题");

    /**
     * The column <code>zhulin.order_eassy.eassy_file</code>. 文件路径
     */
    public final TableField<OrderEassyRecord, String> EASSY_FILE = createField("eassy_file", org.jooq.impl.SQLDataType.VARCHAR(255), this, "文件路径");

    /**
     * The column <code>zhulin.order_eassy.original_level</code>. 原创度
     */
    public final TableField<OrderEassyRecord, Double> ORIGINAL_LEVEL = createField("original_level", org.jooq.impl.SQLDataType.DOUBLE.defaultValue(org.jooq.impl.DSL.inline("0.00", org.jooq.impl.SQLDataType.DOUBLE)), this, "原创度");

    /**
     * The column <code>zhulin.order_eassy.picture_total</code>. 图片数量
     */
    public final TableField<OrderEassyRecord, Integer> PICTURE_TOTAL = createField("picture_total", org.jooq.impl.SQLDataType.INTEGER, this, "图片数量");

    /**
     * The column <code>zhulin.order_eassy.picture_id</code>. 图片id
     */
    public final TableField<OrderEassyRecord, Integer> PICTURE_ID = createField("picture_id", org.jooq.impl.SQLDataType.INTEGER, this, "图片id");

    /**
     * The column <code>zhulin.order_eassy.notes</code>. 备注
     */
    public final TableField<OrderEassyRecord, String> NOTES = createField("notes", org.jooq.impl.SQLDataType.VARCHAR(1024), this, "备注");

    /**
     * The column <code>zhulin.order_eassy.created_at</code>.
     */
    public final TableField<OrderEassyRecord, Timestamp> CREATED_AT = createField("created_at", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.field("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "");

    /**
     * The column <code>zhulin.order_eassy.updated_at</code>.
     */
    public final TableField<OrderEassyRecord, Timestamp> UPDATED_AT = createField("updated_at", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.field("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "");

    /**
     * The column <code>zhulin.order_eassy.status</code>. 0-待审核，1-
     */
    public final TableField<OrderEassyRecord, Byte> STATUS = createField("status", org.jooq.impl.SQLDataType.TINYINT.defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.TINYINT)), this, "0-待审核，1-");

    /**
     * Create a <code>zhulin.order_eassy</code> table reference
     */
    public OrderEassy() {
        this(DSL.name("order_eassy"), null);
    }

    /**
     * Create an aliased <code>zhulin.order_eassy</code> table reference
     */
    public OrderEassy(String alias) {
        this(DSL.name(alias), ORDER_EASSY);
    }

    /**
     * Create an aliased <code>zhulin.order_eassy</code> table reference
     */
    public OrderEassy(Name alias) {
        this(alias, ORDER_EASSY);
    }

    private OrderEassy(Name alias, Table<OrderEassyRecord> aliased) {
        this(alias, aliased, null);
    }

    private OrderEassy(Name alias, Table<OrderEassyRecord> aliased, Field<?>[] parameters) {
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
        return Arrays.<Index>asList(Indexes.ORDER_EASSY_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<OrderEassyRecord, Integer> getIdentity() {
        return Keys.IDENTITY_ORDER_EASSY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<OrderEassyRecord> getPrimaryKey() {
        return Keys.KEY_ORDER_EASSY_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<OrderEassyRecord>> getKeys() {
        return Arrays.<UniqueKey<OrderEassyRecord>>asList(Keys.KEY_ORDER_EASSY_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OrderEassy as(String alias) {
        return new OrderEassy(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OrderEassy as(Name alias) {
        return new OrderEassy(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public OrderEassy rename(String name) {
        return new OrderEassy(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public OrderEassy rename(Name name) {
        return new OrderEassy(name, null);
    }
}