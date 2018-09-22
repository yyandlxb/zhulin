/*
 * This file is generated by jOOQ.
*/
package cn.hlvan.manager.database.tables.records;


import cn.hlvan.manager.database.tables.Order;

import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Record1;
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
public class OrderRecord extends UpdatableRecordImpl<OrderRecord> {

    private static final long serialVersionUID = 28522878;

    /**
     * Setter for <code>zhulin.order.id</code>.
     */
    public OrderRecord setId(Integer value) {
        set(0, value);
        return this;
    }

    /**
     * Getter for <code>zhulin.order.id</code>.
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>zhulin.order.order_code</code>. 订单号
     */
    public OrderRecord setOrderCode(String value) {
        set(1, value);
        return this;
    }

    /**
     * Getter for <code>zhulin.order.order_code</code>. 订单号
     */
    public String getOrderCode() {
        return (String) get(1);
    }

    /**
     * Setter for <code>zhulin.order.order_status</code>. 0：待审核 1：发布中 2：已完成 3：待点评 4：商家已打款5：取消 6：已截稿 7：管理员已完成（已打款）,8-审核未通过
     */
    public OrderRecord setOrderStatus(Byte value) {
        set(2, value);
        return this;
    }

    /**
     * Getter for <code>zhulin.order.order_status</code>. 0：待审核 1：发布中 2：已完成 3：待点评 4：商家已打款5：取消 6：已截稿 7：管理员已完成（已打款）,8-审核未通过
     */
    public Byte getOrderStatus() {
        return (Byte) get(2);
    }

    /**
     * Setter for <code>zhulin.order.pay_type</code>. 1、微信 2、支付宝 3、银联 4、余额 5、现金 6、chinaPay
     */
    public OrderRecord setPayType(Byte value) {
        set(3, value);
        return this;
    }

    /**
     * Getter for <code>zhulin.order.pay_type</code>. 1、微信 2、支付宝 3、银联 4、余额 5、现金 6、chinaPay
     */
    public Byte getPayType() {
        return (Byte) get(3);
    }

    /**
     * Setter for <code>zhulin.order.user_id</code>. 用户id
     */
    public OrderRecord setUserId(Integer value) {
        set(4, value);
        return this;
    }

    /**
     * Getter for <code>zhulin.order.user_id</code>. 用户id
     */
    public Integer getUserId() {
        return (Integer) get(4);
    }

    /**
     * Setter for <code>zhulin.order.total</code>. 数量
     */
    public OrderRecord setTotal(Integer value) {
        set(5, value);
        return this;
    }

    /**
     * Getter for <code>zhulin.order.total</code>. 数量
     */
    public Integer getTotal() {
        return (Integer) get(5);
    }

    /**
     * Setter for <code>zhulin.order.merchant_price</code>. 商家价格
     */
    public OrderRecord setMerchantPrice(BigDecimal value) {
        set(6, value);
        return this;
    }

    /**
     * Getter for <code>zhulin.order.merchant_price</code>. 商家价格
     */
    public BigDecimal getMerchantPrice() {
        return (BigDecimal) get(6);
    }

    /**
     * Setter for <code>zhulin.order.admin_price</code>. 管理员价格
     */
    public OrderRecord setAdminPrice(BigDecimal value) {
        set(7, value);
        return this;
    }

    /**
     * Getter for <code>zhulin.order.admin_price</code>. 管理员价格
     */
    public BigDecimal getAdminPrice() {
        return (BigDecimal) get(7);
    }

    /**
     * Setter for <code>zhulin.order.eassy_type</code>. 文章领域
     */
    public OrderRecord setEassyType(String value) {
        set(8, value);
        return this;
    }

    /**
     * Getter for <code>zhulin.order.eassy_type</code>. 文章领域
     */
    public String getEassyType() {
        return (String) get(8);
    }

    /**
     * Setter for <code>zhulin.order.notes</code>. 备注
     */
    public OrderRecord setNotes(String value) {
        set(9, value);
        return this;
    }

    /**
     * Getter for <code>zhulin.order.notes</code>. 备注
     */
    public String getNotes() {
        return (String) get(9);
    }

    /**
     * Setter for <code>zhulin.order.created_at</code>.
     */
    public OrderRecord setCreatedAt(Timestamp value) {
        set(10, value);
        return this;
    }

    /**
     * Getter for <code>zhulin.order.created_at</code>.
     */
    public Timestamp getCreatedAt() {
        return (Timestamp) get(10);
    }

    /**
     * Setter for <code>zhulin.order.updated_at</code>.
     */
    public OrderRecord setUpdatedAt(Timestamp value) {
        set(11, value);
        return this;
    }

    /**
     * Getter for <code>zhulin.order.updated_at</code>.
     */
    public Timestamp getUpdatedAt() {
        return (Timestamp) get(11);
    }

    /**
     * Setter for <code>zhulin.order.order_title</code>. 文章标题
     */
    public OrderRecord setOrderTitle(String value) {
        set(12, value);
        return this;
    }

    /**
     * Getter for <code>zhulin.order.order_title</code>. 文章标题
     */
    public String getOrderTitle() {
        return (String) get(12);
    }

    /**
     * Setter for <code>zhulin.order.original_level</code>. 原创度
     */
    public OrderRecord setOriginalLevel(Double value) {
        set(13, value);
        return this;
    }

    /**
     * Getter for <code>zhulin.order.original_level</code>. 原创度
     */
    public Double getOriginalLevel() {
        return (Double) get(13);
    }

    /**
     * Setter for <code>zhulin.order.picture</code>. 图片数量要求
     */
    public OrderRecord setPicture(Integer value) {
        set(14, value);
        return this;
    }

    /**
     * Getter for <code>zhulin.order.picture</code>. 图片数量要求
     */
    public Integer getPicture() {
        return (Integer) get(14);
    }

    /**
     * Setter for <code>zhulin.order.type</code>. 0-流量文，1-养号文
     */
    public OrderRecord setType(Byte value) {
        set(15, value);
        return this;
    }

    /**
     * Getter for <code>zhulin.order.type</code>. 0-流量文，1-养号文
     */
    public Byte getType() {
        return (Byte) get(15);
    }

    /**
     * Setter for <code>zhulin.order.end_time</code>. 截止日期
     */
    public OrderRecord setEndTime(Timestamp value) {
        set(16, value);
        return this;
    }

    /**
     * Getter for <code>zhulin.order.end_time</code>. 截止日期
     */
    public Timestamp getEndTime() {
        return (Timestamp) get(16);
    }

    /**
     * Setter for <code>zhulin.order.require</code>. 要求
     */
    public OrderRecord setRequire(String value) {
        set(17, value);
        return this;
    }

    /**
     * Getter for <code>zhulin.order.require</code>. 要求
     */
    public String getRequire() {
        return (String) get(17);
    }

    /**
     * Setter for <code>zhulin.order.eassy_total</code>. 文章数量
     */
    public OrderRecord setEassyTotal(Integer value) {
        set(18, value);
        return this;
    }

    /**
     * Getter for <code>zhulin.order.eassy_total</code>. 文章数量
     */
    public Integer getEassyTotal() {
        return (Integer) get(18);
    }

    /**
     * Setter for <code>zhulin.order.word_count</code>. 文章字数
     */
    public OrderRecord setWordCount(String value) {
        set(19, value);
        return this;
    }

    /**
     * Getter for <code>zhulin.order.word_count</code>. 文章字数
     */
    public String getWordCount() {
        return (String) get(19);
    }

    /**
     * Setter for <code>zhulin.order.result</code>. 审核结果
     */
    public OrderRecord setResult(String value) {
        set(20, value);
        return this;
    }

    /**
     * Getter for <code>zhulin.order.result</code>. 审核结果
     */
    public String getResult() {
        return (String) get(20);
    }

    /**
     * Setter for <code>zhulin.order.appoint_total</code>. 已预约数量
     */
    public OrderRecord setAppointTotal(Integer value) {
        set(21, value);
        return this;
    }

    /**
     * Getter for <code>zhulin.order.appoint_total</code>. 已预约数量
     */
    public Integer getAppointTotal() {
        return (Integer) get(21);
    }

    /**
     * Setter for <code>zhulin.order.admin_end_time</code>. 管理员设定的截稿时间
     */
    public OrderRecord setAdminEndTime(Timestamp value) {
        set(22, value);
        return this;
    }

    /**
     * Getter for <code>zhulin.order.admin_end_time</code>. 管理员设定的截稿时间
     */
    public Timestamp getAdminEndTime() {
        return (Timestamp) get(22);
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
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached OrderRecord
     */
    public OrderRecord() {
        super(Order.ORDER);
    }

    /**
     * Create a detached, initialised OrderRecord
     */
    public OrderRecord(Integer id, String orderCode, Byte orderStatus, Byte payType, Integer userId, Integer total, BigDecimal merchantPrice, BigDecimal adminPrice, String eassyType, String notes, Timestamp createdAt, Timestamp updatedAt, String orderTitle, Double originalLevel, Integer picture, Byte type, Timestamp endTime, String require, Integer eassyTotal, String wordCount, String result, Integer appointTotal, Timestamp adminEndTime) {
        super(Order.ORDER);

        set(0, id);
        set(1, orderCode);
        set(2, orderStatus);
        set(3, payType);
        set(4, userId);
        set(5, total);
        set(6, merchantPrice);
        set(7, adminPrice);
        set(8, eassyType);
        set(9, notes);
        set(10, createdAt);
        set(11, updatedAt);
        set(12, orderTitle);
        set(13, originalLevel);
        set(14, picture);
        set(15, type);
        set(16, endTime);
        set(17, require);
        set(18, eassyTotal);
        set(19, wordCount);
        set(20, result);
        set(21, appointTotal);
        set(22, adminEndTime);
    }
}
