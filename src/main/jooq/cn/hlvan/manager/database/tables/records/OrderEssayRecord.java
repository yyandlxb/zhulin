/*
 * This file is generated by jOOQ.
*/
package cn.hlvan.manager.database.tables.records;


import cn.hlvan.manager.database.tables.OrderEssay;

import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record13;
import org.jooq.Row13;
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
public class OrderEssayRecord extends UpdatableRecordImpl<OrderEssayRecord> implements Record13<Integer, Integer, String, String, String, Double, Integer, Integer, String, Timestamp, Timestamp, Byte, String> {

    private static final long serialVersionUID = -1873697463;

    /**
     * Setter for <code>zhulin.order_essay.id</code>.
     */
    public OrderEssayRecord setId(Integer value) {
        set(0, value);
        return this;
    }

    /**
     * Getter for <code>zhulin.order_essay.id</code>.
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>zhulin.order_essay.user_order_id</code>. 用户订单号id
     */
    public OrderEssayRecord setUserOrderId(Integer value) {
        set(1, value);
        return this;
    }

    /**
     * Getter for <code>zhulin.order_essay.user_order_id</code>. 用户订单号id
     */
    public Integer getUserOrderId() {
        return (Integer) get(1);
    }

    /**
     * Setter for <code>zhulin.order_essay.order_code</code>. 订单号
     */
    public OrderEssayRecord setOrderCode(String value) {
        set(2, value);
        return this;
    }

    /**
     * Getter for <code>zhulin.order_essay.order_code</code>. 订单号
     */
    public String getOrderCode() {
        return (String) get(2);
    }

    /**
     * Setter for <code>zhulin.order_essay.essay_title</code>. 文章标题
     */
    public OrderEssayRecord setEssayTitle(String value) {
        set(3, value);
        return this;
    }

    /**
     * Getter for <code>zhulin.order_essay.essay_title</code>. 文章标题
     */
    public String getEssayTitle() {
        return (String) get(3);
    }

    /**
     * Setter for <code>zhulin.order_essay.eassy_file</code>. 文件路径
     */
    public OrderEssayRecord setEassyFile(String value) {
        set(4, value);
        return this;
    }

    /**
     * Getter for <code>zhulin.order_essay.eassy_file</code>. 文件路径
     */
    public String getEassyFile() {
        return (String) get(4);
    }

    /**
     * Setter for <code>zhulin.order_essay.original_level</code>. 原创度
     */
    public OrderEssayRecord setOriginalLevel(Double value) {
        set(5, value);
        return this;
    }

    /**
     * Getter for <code>zhulin.order_essay.original_level</code>. 原创度
     */
    public Double getOriginalLevel() {
        return (Double) get(5);
    }

    /**
     * Setter for <code>zhulin.order_essay.picture_total</code>. 图片数量
     */
    public OrderEssayRecord setPictureTotal(Integer value) {
        set(6, value);
        return this;
    }

    /**
     * Getter for <code>zhulin.order_essay.picture_total</code>. 图片数量
     */
    public Integer getPictureTotal() {
        return (Integer) get(6);
    }

    /**
     * Setter for <code>zhulin.order_essay.picture_id</code>. 图片id
     */
    public OrderEssayRecord setPictureId(Integer value) {
        set(7, value);
        return this;
    }

    /**
     * Getter for <code>zhulin.order_essay.picture_id</code>. 图片id
     */
    public Integer getPictureId() {
        return (Integer) get(7);
    }

    /**
     * Setter for <code>zhulin.order_essay.notes</code>. 备注
     */
    public OrderEssayRecord setNotes(String value) {
        set(8, value);
        return this;
    }

    /**
     * Getter for <code>zhulin.order_essay.notes</code>. 备注
     */
    public String getNotes() {
        return (String) get(8);
    }

    /**
     * Setter for <code>zhulin.order_essay.created_at</code>.
     */
    public OrderEssayRecord setCreatedAt(Timestamp value) {
        set(9, value);
        return this;
    }

    /**
     * Getter for <code>zhulin.order_essay.created_at</code>.
     */
    public Timestamp getCreatedAt() {
        return (Timestamp) get(9);
    }

    /**
     * Setter for <code>zhulin.order_essay.updated_at</code>.
     */
    public OrderEssayRecord setUpdatedAt(Timestamp value) {
        set(10, value);
        return this;
    }

    /**
     * Getter for <code>zhulin.order_essay.updated_at</code>.
     */
    public Timestamp getUpdatedAt() {
        return (Timestamp) get(10);
    }

    /**
     * Setter for <code>zhulin.order_essay.status</code>. 0-待管理员审核，1-商家退稿，2-收稿成功，3-商家已打款，4待商家审核，5管理员退稿，6管理员已打款
     */
    public OrderEssayRecord setStatus(Byte value) {
        set(11, value);
        return this;
    }

    /**
     * Getter for <code>zhulin.order_essay.status</code>. 0-待管理员审核，1-商家退稿，2-收稿成功，3-商家已打款，4待商家审核，5管理员退稿，6管理员已打款
     */
    public Byte getStatus() {
        return (Byte) get(11);
    }

    /**
     * Setter for <code>zhulin.order_essay.result</code>. 审核结果
     */
    public OrderEssayRecord setResult(String value) {
        set(12, value);
        return this;
    }

    /**
     * Getter for <code>zhulin.order_essay.result</code>. 审核结果
     */
    public String getResult() {
        return (String) get(12);
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
    // Record13 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row13<Integer, Integer, String, String, String, Double, Integer, Integer, String, Timestamp, Timestamp, Byte, String> fieldsRow() {
        return (Row13) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row13<Integer, Integer, String, String, String, Double, Integer, Integer, String, Timestamp, Timestamp, Byte, String> valuesRow() {
        return (Row13) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field1() {
        return OrderEssay.ORDER_ESSAY.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field2() {
        return OrderEssay.ORDER_ESSAY.USER_ORDER_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field3() {
        return OrderEssay.ORDER_ESSAY.ORDER_CODE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field4() {
        return OrderEssay.ORDER_ESSAY.ESSAY_TITLE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field5() {
        return OrderEssay.ORDER_ESSAY.EASSY_FILE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Double> field6() {
        return OrderEssay.ORDER_ESSAY.ORIGINAL_LEVEL;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field7() {
        return OrderEssay.ORDER_ESSAY.PICTURE_TOTAL;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field8() {
        return OrderEssay.ORDER_ESSAY.PICTURE_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field9() {
        return OrderEssay.ORDER_ESSAY.NOTES;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field10() {
        return OrderEssay.ORDER_ESSAY.CREATED_AT;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field11() {
        return OrderEssay.ORDER_ESSAY.UPDATED_AT;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Byte> field12() {
        return OrderEssay.ORDER_ESSAY.STATUS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field13() {
        return OrderEssay.ORDER_ESSAY.RESULT;
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
        return getUserOrderId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component3() {
        return getOrderCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component4() {
        return getEssayTitle();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component5() {
        return getEassyFile();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Double component6() {
        return getOriginalLevel();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component7() {
        return getPictureTotal();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component8() {
        return getPictureId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component9() {
        return getNotes();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp component10() {
        return getCreatedAt();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp component11() {
        return getUpdatedAt();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte component12() {
        return getStatus();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component13() {
        return getResult();
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
        return getUserOrderId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value3() {
        return getOrderCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value4() {
        return getEssayTitle();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value5() {
        return getEassyFile();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Double value6() {
        return getOriginalLevel();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value7() {
        return getPictureTotal();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value8() {
        return getPictureId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value9() {
        return getNotes();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value10() {
        return getCreatedAt();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value11() {
        return getUpdatedAt();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte value12() {
        return getStatus();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value13() {
        return getResult();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OrderEssayRecord value1(Integer value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OrderEssayRecord value2(Integer value) {
        setUserOrderId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OrderEssayRecord value3(String value) {
        setOrderCode(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OrderEssayRecord value4(String value) {
        setEssayTitle(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OrderEssayRecord value5(String value) {
        setEassyFile(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OrderEssayRecord value6(Double value) {
        setOriginalLevel(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OrderEssayRecord value7(Integer value) {
        setPictureTotal(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OrderEssayRecord value8(Integer value) {
        setPictureId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OrderEssayRecord value9(String value) {
        setNotes(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OrderEssayRecord value10(Timestamp value) {
        setCreatedAt(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OrderEssayRecord value11(Timestamp value) {
        setUpdatedAt(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OrderEssayRecord value12(Byte value) {
        setStatus(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OrderEssayRecord value13(String value) {
        setResult(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OrderEssayRecord values(Integer value1, Integer value2, String value3, String value4, String value5, Double value6, Integer value7, Integer value8, String value9, Timestamp value10, Timestamp value11, Byte value12, String value13) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        value7(value7);
        value8(value8);
        value9(value9);
        value10(value10);
        value11(value11);
        value12(value12);
        value13(value13);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached OrderEssayRecord
     */
    public OrderEssayRecord() {
        super(OrderEssay.ORDER_ESSAY);
    }

    /**
     * Create a detached, initialised OrderEssayRecord
     */
    public OrderEssayRecord(Integer id, Integer userOrderId, String orderCode, String essayTitle, String eassyFile, Double originalLevel, Integer pictureTotal, Integer pictureId, String notes, Timestamp createdAt, Timestamp updatedAt, Byte status, String result) {
        super(OrderEssay.ORDER_ESSAY);

        set(0, id);
        set(1, userOrderId);
        set(2, orderCode);
        set(3, essayTitle);
        set(4, eassyFile);
        set(5, originalLevel);
        set(6, pictureTotal);
        set(7, pictureId);
        set(8, notes);
        set(9, createdAt);
        set(10, updatedAt);
        set(11, status);
        set(12, result);
    }
}
