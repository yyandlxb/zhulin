/*
 * This file is generated by jOOQ.
*/
package cn.hlvan.manager.database.tables.records;


import cn.hlvan.manager.database.tables.LimitTime;

import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record5;
import org.jooq.Row5;
import org.jooq.impl.UpdatableRecordImpl;


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
public class LimitTimeRecord extends UpdatableRecordImpl<LimitTimeRecord> implements Record5<Integer, Integer, Timestamp, Timestamp, Integer> {

    private static final long serialVersionUID = 976176300;

    /**
     * Setter for <code>zhulin.limit_time.id</code>.
     */
    public LimitTimeRecord setId(Integer value) {
        set(0, value);
        return this;
    }

    /**
     * Getter for <code>zhulin.limit_time.id</code>.
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>zhulin.limit_time.limit_time</code>. 限制小时
     */
    public LimitTimeRecord setLimitTime(Integer value) {
        set(1, value);
        return this;
    }

    /**
     * Getter for <code>zhulin.limit_time.limit_time</code>. 限制小时
     */
    public Integer getLimitTime() {
        return (Integer) get(1);
    }

    /**
     * Setter for <code>zhulin.limit_time.created_at</code>.
     */
    public LimitTimeRecord setCreatedAt(Timestamp value) {
        set(2, value);
        return this;
    }

    /**
     * Getter for <code>zhulin.limit_time.created_at</code>.
     */
    public Timestamp getCreatedAt() {
        return (Timestamp) get(2);
    }

    /**
     * Setter for <code>zhulin.limit_time.updated_at</code>.
     */
    public LimitTimeRecord setUpdatedAt(Timestamp value) {
        set(3, value);
        return this;
    }

    /**
     * Getter for <code>zhulin.limit_time.updated_at</code>.
     */
    public Timestamp getUpdatedAt() {
        return (Timestamp) get(3);
    }

    /**
     * Setter for <code>zhulin.limit_time.user_id</code>.
     */
    public LimitTimeRecord setUserId(Integer value) {
        set(4, value);
        return this;
    }

    /**
     * Getter for <code>zhulin.limit_time.user_id</code>.
     */
    public Integer getUserId() {
        return (Integer) get(4);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Record1<Integer> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record5 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row5<Integer, Integer, Timestamp, Timestamp, Integer> fieldsRow() {
        return (Row5) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row5<Integer, Integer, Timestamp, Timestamp, Integer> valuesRow() {
        return (Row5) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field1() {
        return LimitTime.LIMIT_TIME.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field2() {
        return LimitTime.LIMIT_TIME.LIMIT_TIME_;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field3() {
        return LimitTime.LIMIT_TIME.CREATED_AT;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field4() {
        return LimitTime.LIMIT_TIME.UPDATED_AT;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field5() {
        return LimitTime.LIMIT_TIME.USER_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component1() {
        return getId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component2() {
        return getLimitTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp component3() {
        return getCreatedAt();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp component4() {
        return getUpdatedAt();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component5() {
        return getUserId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value1() {
        return getId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value2() {
        return getLimitTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value3() {
        return getCreatedAt();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value4() {
        return getUpdatedAt();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value5() {
        return getUserId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LimitTimeRecord value1(Integer value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LimitTimeRecord value2(Integer value) {
        setLimitTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LimitTimeRecord value3(Timestamp value) {
        setCreatedAt(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LimitTimeRecord value4(Timestamp value) {
        setUpdatedAt(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LimitTimeRecord value5(Integer value) {
        setUserId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LimitTimeRecord values(Integer value1, Integer value2, Timestamp value3, Timestamp value4, Integer value5) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached LimitTimeRecord
     */
    public LimitTimeRecord() {
        super(LimitTime.LIMIT_TIME);
    }

    /**
     * Create a detached, initialised LimitTimeRecord
     */
    public LimitTimeRecord(Integer id, Integer limitTime, Timestamp createdAt, Timestamp updatedAt, Integer userId) {
        super(LimitTime.LIMIT_TIME);

        set(0, id);
        set(1, limitTime);
        set(2, createdAt);
        set(3, updatedAt);
        set(4, userId);
    }
}
