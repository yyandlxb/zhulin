/*
 * This file is generated by jOOQ.
*/
package cn.hlvan.manager.database.tables;


import cn.hlvan.manager.database.Indexes;
import cn.hlvan.manager.database.Keys;
import cn.hlvan.manager.database.Zhulin;
import cn.hlvan.manager.database.tables.records.LimitTimeRecord;

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
 * 文章领域
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.10.7"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class LimitTime extends TableImpl<LimitTimeRecord> {

    private static final long serialVersionUID = -542846822;

    /**
     * The reference instance of <code>zhulin.limit_time</code>
     */
    public static final LimitTime LIMIT_TIME = new LimitTime();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<LimitTimeRecord> getRecordType() {
        return LimitTimeRecord.class;
    }

    /**
     * The column <code>zhulin.limit_time.id</code>.
     */
    public final TableField<LimitTimeRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).identity(true), this, "");

    /**
     * The column <code>zhulin.limit_time.limit_time</code>. 限制小时
     */
    public final TableField<LimitTimeRecord, Integer> LIMIT_TIME_ = createField("limit_time", org.jooq.impl.SQLDataType.INTEGER, this, "限制小时");

    /**
     * The column <code>zhulin.limit_time.created_at</code>.
     */
    public final TableField<LimitTimeRecord, Timestamp> CREATED_AT = createField("created_at", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false), this, "");

    /**
     * The column <code>zhulin.limit_time.updated_at</code>.
     */
    public final TableField<LimitTimeRecord, Timestamp> UPDATED_AT = createField("updated_at", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false), this, "");

    /**
     * Create a <code>zhulin.limit_time</code> table reference
     */
    public LimitTime() {
        this(DSL.name("limit_time"), null);
    }

    /**
     * Create an aliased <code>zhulin.limit_time</code> table reference
     */
    public LimitTime(String alias) {
        this(DSL.name(alias), LIMIT_TIME);
    }

    /**
     * Create an aliased <code>zhulin.limit_time</code> table reference
     */
    public LimitTime(Name alias) {
        this(alias, LIMIT_TIME);
    }

    private LimitTime(Name alias, Table<LimitTimeRecord> aliased) {
        this(alias, aliased, null);
    }

    private LimitTime(Name alias, Table<LimitTimeRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, "文章领域");
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
        return Arrays.<Index>asList(Indexes.LIMIT_TIME_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<LimitTimeRecord, Integer> getIdentity() {
        return Keys.IDENTITY_LIMIT_TIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<LimitTimeRecord> getPrimaryKey() {
        return Keys.KEY_LIMIT_TIME_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<LimitTimeRecord>> getKeys() {
        return Arrays.<UniqueKey<LimitTimeRecord>>asList(Keys.KEY_LIMIT_TIME_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LimitTime as(String alias) {
        return new LimitTime(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LimitTime as(Name alias) {
        return new LimitTime(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public LimitTime rename(String name) {
        return new LimitTime(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public LimitTime rename(Name name) {
        return new LimitTime(name, null);
    }
}
