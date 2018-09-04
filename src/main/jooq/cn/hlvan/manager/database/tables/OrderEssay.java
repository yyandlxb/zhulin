/*
 * This file is generated by jOOQ.
*/
package cn.hlvan.manager.database.tables;


import cn.hlvan.manager.database.Indexes;
import cn.hlvan.manager.database.Keys;
import cn.hlvan.manager.database.Zhulin;
import cn.hlvan.manager.database.tables.records.OrderEssayRecord;

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
public class OrderEssay extends TableImpl<OrderEssayRecord> {

    private static final long serialVersionUID = -1437186396;

    /**
     * The reference instance of <code>zhulin.order_essay</code>
     */
    public static final OrderEssay ORDER_ESSAY = new OrderEssay();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<OrderEssayRecord> getRecordType() {
        return OrderEssayRecord.class;
    }

    /**
     * The column <code>zhulin.order_essay.id</code>.
     */
    public final TableField<OrderEssayRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).identity(true), this, "");

    /**
     * The column <code>zhulin.order_essay.user_order_id</code>. 用户订单号id
     */
    public final TableField<OrderEssayRecord, Integer> USER_ORDER_ID = createField("user_order_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "用户订单号id");

    /**
     * The column <code>zhulin.order_essay.order_code</code>. 订单号
     */
    public final TableField<OrderEssayRecord, String> ORDER_CODE = createField("order_code", org.jooq.impl.SQLDataType.VARCHAR(255), this, "订单号");

    /**
     * The column <code>zhulin.order_essay.essay_title</code>. 文章标题
     */
    public final TableField<OrderEssayRecord, String> ESSAY_TITLE = createField("essay_title", org.jooq.impl.SQLDataType.VARCHAR(255), this, "文章标题");

    /**
     * The column <code>zhulin.order_essay.eassy_file</code>. 文件路径
     */
    public final TableField<OrderEssayRecord, String> EASSY_FILE = createField("eassy_file", org.jooq.impl.SQLDataType.VARCHAR(255), this, "文件路径");

    /**
     * The column <code>zhulin.order_essay.original_level</code>. 原创度
     */
    public final TableField<OrderEssayRecord, Double> ORIGINAL_LEVEL = createField("original_level", org.jooq.impl.SQLDataType.DOUBLE.defaultValue(org.jooq.impl.DSL.inline("0.00", org.jooq.impl.SQLDataType.DOUBLE)), this, "原创度");

    /**
     * The column <code>zhulin.order_essay.picture_total</code>. 图片数量
     */
    public final TableField<OrderEssayRecord, Integer> PICTURE_TOTAL = createField("picture_total", org.jooq.impl.SQLDataType.INTEGER, this, "图片数量");

    /**
     * The column <code>zhulin.order_essay.picture_id</code>. 图片id
     */
    public final TableField<OrderEssayRecord, Integer> PICTURE_ID = createField("picture_id", org.jooq.impl.SQLDataType.INTEGER, this, "图片id");

    /**
     * The column <code>zhulin.order_essay.notes</code>. 备注
     */
    public final TableField<OrderEssayRecord, String> NOTES = createField("notes", org.jooq.impl.SQLDataType.VARCHAR(1024), this, "备注");

    /**
     * The column <code>zhulin.order_essay.created_at</code>.
     */
    public final TableField<OrderEssayRecord, Timestamp> CREATED_AT = createField("created_at", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.field("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "");

    /**
     * The column <code>zhulin.order_essay.updated_at</code>.
     */
    public final TableField<OrderEssayRecord, Timestamp> UPDATED_AT = createField("updated_at", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.field("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "");

    /**
     * The column <code>zhulin.order_essay.status</code>. 0-待审核，1-审核成功，2-退稿
     */
    public final TableField<OrderEssayRecord, Byte> STATUS = createField("status", org.jooq.impl.SQLDataType.TINYINT.defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.TINYINT)), this, "0-待审核，1-审核成功，2-退稿");

    /**
     * The column <code>zhulin.order_essay.result</code>. 审核结果
     */
    public final TableField<OrderEssayRecord, String> RESULT = createField("result", org.jooq.impl.SQLDataType.VARCHAR(1024), this, "审核结果");

    /**
     * Create a <code>zhulin.order_essay</code> table reference
     */
    public OrderEssay() {
        this(DSL.name("order_essay"), null);
    }

    /**
     * Create an aliased <code>zhulin.order_essay</code> table reference
     */
    public OrderEssay(String alias) {
        this(DSL.name(alias), ORDER_ESSAY);
    }

    /**
     * Create an aliased <code>zhulin.order_essay</code> table reference
     */
    public OrderEssay(Name alias) {
        this(alias, ORDER_ESSAY);
    }

    private OrderEssay(Name alias, Table<OrderEssayRecord> aliased) {
        this(alias, aliased, null);
    }

    private OrderEssay(Name alias, Table<OrderEssayRecord> aliased, Field<?>[] parameters) {
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
        return Arrays.<Index>asList(Indexes.ORDER_ESSAY_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<OrderEssayRecord, Integer> getIdentity() {
        return Keys.IDENTITY_ORDER_ESSAY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<OrderEssayRecord> getPrimaryKey() {
        return Keys.KEY_ORDER_ESSAY_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<OrderEssayRecord>> getKeys() {
        return Arrays.<UniqueKey<OrderEssayRecord>>asList(Keys.KEY_ORDER_ESSAY_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OrderEssay as(String alias) {
        return new OrderEssay(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OrderEssay as(Name alias) {
        return new OrderEssay(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public OrderEssay rename(String name) {
        return new OrderEssay(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public OrderEssay rename(Name name) {
        return new OrderEssay(name, null);
    }
}
