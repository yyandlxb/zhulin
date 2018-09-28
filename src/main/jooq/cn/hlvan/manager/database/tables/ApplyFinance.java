/*
 * This file is generated by jOOQ.
*/
package cn.hlvan.manager.database.tables;


import cn.hlvan.manager.database.Indexes;
import cn.hlvan.manager.database.Keys;
import cn.hlvan.manager.database.Zhulin;
import cn.hlvan.manager.database.tables.records.ApplyFinanceRecord;

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
public class ApplyFinance extends TableImpl<ApplyFinanceRecord> {

    private static final long serialVersionUID = -828266420;

    /**
     * The reference instance of <code>zhulin.apply_finance</code>
     */
    public static final ApplyFinance APPLY_FINANCE = new ApplyFinance();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<ApplyFinanceRecord> getRecordType() {
        return ApplyFinanceRecord.class;
    }

    /**
     * The column <code>zhulin.apply_finance.id</code>. 写手申请打款
     */
    public final TableField<ApplyFinanceRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).identity(true), this, "写手申请打款");

    /**
     * The column <code>zhulin.apply_finance.money</code>. 申请金额
     */
    public final TableField<ApplyFinanceRecord, BigDecimal> MONEY = createField("money", org.jooq.impl.SQLDataType.DECIMAL(10, 2).nullable(false), this, "申请金额");

    /**
     * The column <code>zhulin.apply_finance.user_id</code>. 用户Id
     */
    public final TableField<ApplyFinanceRecord, Integer> USER_ID = createField("user_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "用户Id");

    /**
     * The column <code>zhulin.apply_finance.created_at</code>.
     */
    public final TableField<ApplyFinanceRecord, Timestamp> CREATED_AT = createField("created_at", org.jooq.impl.SQLDataType.TIMESTAMP.defaultValue(org.jooq.impl.DSL.field("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "");

    /**
     * The column <code>zhulin.apply_finance.updated_at</code>.
     */
    public final TableField<ApplyFinanceRecord, Timestamp> UPDATED_AT = createField("updated_at", org.jooq.impl.SQLDataType.TIMESTAMP.defaultValue(org.jooq.impl.DSL.field("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "");

    /**
     * The column <code>zhulin.apply_finance.status</code>. 0-待打款，1-已打款
     */
    public final TableField<ApplyFinanceRecord, Byte> STATUS = createField("status", org.jooq.impl.SQLDataType.TINYINT.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.TINYINT)), this, "0-待打款，1-已打款");

    /**
     * Create a <code>zhulin.apply_finance</code> table reference
     */
    public ApplyFinance() {
        this(DSL.name("apply_finance"), null);
    }

    /**
     * Create an aliased <code>zhulin.apply_finance</code> table reference
     */
    public ApplyFinance(String alias) {
        this(DSL.name(alias), APPLY_FINANCE);
    }

    /**
     * Create an aliased <code>zhulin.apply_finance</code> table reference
     */
    public ApplyFinance(Name alias) {
        this(alias, APPLY_FINANCE);
    }

    private ApplyFinance(Name alias, Table<ApplyFinanceRecord> aliased) {
        this(alias, aliased, null);
    }

    private ApplyFinance(Name alias, Table<ApplyFinanceRecord> aliased, Field<?>[] parameters) {
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
        return Arrays.<Index>asList(Indexes.APPLY_FINANCE_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<ApplyFinanceRecord, Integer> getIdentity() {
        return Keys.IDENTITY_APPLY_FINANCE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<ApplyFinanceRecord> getPrimaryKey() {
        return Keys.KEY_APPLY_FINANCE_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<ApplyFinanceRecord>> getKeys() {
        return Arrays.<UniqueKey<ApplyFinanceRecord>>asList(Keys.KEY_APPLY_FINANCE_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ApplyFinance as(String alias) {
        return new ApplyFinance(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ApplyFinance as(Name alias) {
        return new ApplyFinance(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public ApplyFinance rename(String name) {
        return new ApplyFinance(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public ApplyFinance rename(Name name) {
        return new ApplyFinance(name, null);
    }
}
