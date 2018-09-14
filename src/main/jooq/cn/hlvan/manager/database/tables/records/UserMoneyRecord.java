/*
 * This file is generated by jOOQ.
*/
package cn.hlvan.manager.database.tables.records;


import cn.hlvan.manager.database.tables.UserMoney;

import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record5;
import org.jooq.Row5;
import org.jooq.impl.UpdatableRecordImpl;


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
public class UserMoneyRecord extends UpdatableRecordImpl<UserMoneyRecord> implements Record5<Integer, BigDecimal, Integer, Timestamp, Timestamp> {

    private static final long serialVersionUID = -1807454532;

    /**
     * Setter for <code>zhulin.user_money.id</code>. 用户余额表
     */
    public UserMoneyRecord setId(Integer value) {
        set(0, value);
        return this;
    }

    /**
     * Getter for <code>zhulin.user_money.id</code>. 用户余额表
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>zhulin.user_money.money</code>. 余额
     */
    public UserMoneyRecord setMoney(BigDecimal value) {
        set(1, value);
        return this;
    }

    /**
     * Getter for <code>zhulin.user_money.money</code>. 余额
     */
    public BigDecimal getMoney() {
        return (BigDecimal) get(1);
    }

    /**
     * Setter for <code>zhulin.user_money.user_id</code>. 用户Id
     */
    public UserMoneyRecord setUserId(Integer value) {
        set(2, value);
        return this;
    }

    /**
     * Getter for <code>zhulin.user_money.user_id</code>. 用户Id
     */
    public Integer getUserId() {
        return (Integer) get(2);
    }

    /**
     * Setter for <code>zhulin.user_money.created_at</code>.
     */
    public UserMoneyRecord setCreatedAt(Timestamp value) {
        set(3, value);
        return this;
    }

    /**
     * Getter for <code>zhulin.user_money.created_at</code>.
     */
    public Timestamp getCreatedAt() {
        return (Timestamp) get(3);
    }

    /**
     * Setter for <code>zhulin.user_money.updated_at</code>.
     */
    public UserMoneyRecord setUpdatedAt(Timestamp value) {
        set(4, value);
        return this;
    }

    /**
     * Getter for <code>zhulin.user_money.updated_at</code>.
     */
    public Timestamp getUpdatedAt() {
        return (Timestamp) get(4);
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
    public Row5<Integer, BigDecimal, Integer, Timestamp, Timestamp> fieldsRow() {
        return (Row5) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row5<Integer, BigDecimal, Integer, Timestamp, Timestamp> valuesRow() {
        return (Row5) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field1() {
        return UserMoney.USER_MONEY.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<BigDecimal> field2() {
        return UserMoney.USER_MONEY.MONEY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field3() {
        return UserMoney.USER_MONEY.USER_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field4() {
        return UserMoney.USER_MONEY.CREATED_AT;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field5() {
        return UserMoney.USER_MONEY.UPDATED_AT;
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
    public BigDecimal component2() {
        return getMoney();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component3() {
        return getUserId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp component4() {
        return getCreatedAt();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp component5() {
        return getUpdatedAt();
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
    public BigDecimal value2() {
        return getMoney();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value3() {
        return getUserId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value4() {
        return getCreatedAt();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value5() {
        return getUpdatedAt();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserMoneyRecord value1(Integer value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserMoneyRecord value2(BigDecimal value) {
        setMoney(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserMoneyRecord value3(Integer value) {
        setUserId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserMoneyRecord value4(Timestamp value) {
        setCreatedAt(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserMoneyRecord value5(Timestamp value) {
        setUpdatedAt(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserMoneyRecord values(Integer value1, BigDecimal value2, Integer value3, Timestamp value4, Timestamp value5) {
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
     * Create a detached UserMoneyRecord
     */
    public UserMoneyRecord() {
        super(UserMoney.USER_MONEY);
    }

    /**
     * Create a detached, initialised UserMoneyRecord
     */
    public UserMoneyRecord(Integer id, BigDecimal money, Integer userId, Timestamp createdAt, Timestamp updatedAt) {
        super(UserMoney.USER_MONEY);

        set(0, id);
        set(1, money);
        set(2, userId);
        set(3, createdAt);
        set(4, updatedAt);
    }
}
