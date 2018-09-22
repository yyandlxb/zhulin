/*
 * This file is generated by jOOQ.
*/
package cn.hlvan.manager.database.tables;


import cn.hlvan.manager.database.Indexes;
import cn.hlvan.manager.database.Keys;
import cn.hlvan.manager.database.Zhulin;
import cn.hlvan.manager.database.tables.records.UserRecord;

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
public class User extends TableImpl<UserRecord> {

    private static final long serialVersionUID = -888880548;

    /**
     * The reference instance of <code>zhulin.user</code>
     */
    public static final User USER = new User();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<UserRecord> getRecordType() {
        return UserRecord.class;
    }

    /**
     * The column <code>zhulin.user.id</code>. 用户id
     */
    public final TableField<UserRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).identity(true), this, "用户id");

    /**
     * The column <code>zhulin.user.account</code>.
     */
    public final TableField<UserRecord, String> ACCOUNT = createField("account", org.jooq.impl.SQLDataType.VARCHAR(50).nullable(false), this, "");

    /**
     * The column <code>zhulin.user.name</code>. 用户名称
     */
    public final TableField<UserRecord, String> NAME = createField("name", org.jooq.impl.SQLDataType.VARCHAR(50), this, "用户名称");

    /**
     * The column <code>zhulin.user.password</code>. 用户密码
     */
    public final TableField<UserRecord, String> PASSWORD = createField("password", org.jooq.impl.SQLDataType.VARCHAR(50).nullable(false), this, "用户密码");

    /**
     * The column <code>zhulin.user.qq</code>. qq号
     */
    public final TableField<UserRecord, String> QQ = createField("qq", org.jooq.impl.SQLDataType.VARCHAR(15), this, "qq号");

    /**
     * The column <code>zhulin.user.we_chat</code>. 微信号
     */
    public final TableField<UserRecord, String> WE_CHAT = createField("we_chat", org.jooq.impl.SQLDataType.VARCHAR(50), this, "微信号");

    /**
     * The column <code>zhulin.user.code</code>. 用户编码
     */
    public final TableField<UserRecord, String> CODE = createField("code", org.jooq.impl.SQLDataType.VARCHAR(50), this, "用户编码");

    /**
     * The column <code>zhulin.user.address</code>. 用户地址
     */
    public final TableField<UserRecord, String> ADDRESS = createField("address", org.jooq.impl.SQLDataType.VARCHAR(255), this, "用户地址");

    /**
     * The column <code>zhulin.user.status</code>. 1-待提交信息，2-待审核，3-审核成功，4-审核失败，5-禁用
     */
    public final TableField<UserRecord, Byte> STATUS = createField("status", org.jooq.impl.SQLDataType.TINYINT.nullable(false).defaultValue(org.jooq.impl.DSL.inline("1", org.jooq.impl.SQLDataType.TINYINT)), this, "1-待提交信息，2-待审核，3-审核成功，4-审核失败，5-禁用");

    /**
     * The column <code>zhulin.user.credit_level</code>. 信用等级
     */
    public final TableField<UserRecord, Integer> CREDIT_LEVEL = createField("credit_level", org.jooq.impl.SQLDataType.INTEGER.defaultValue(org.jooq.impl.DSL.inline("100", org.jooq.impl.SQLDataType.INTEGER)), this, "信用等级");

    /**
     * The column <code>zhulin.user.type</code>. 用户身份(1-超级管理员，2-管理员，3-商家，4-写手)
     */
    public final TableField<UserRecord, Byte> TYPE = createField("type", org.jooq.impl.SQLDataType.TINYINT.nullable(false), this, "用户身份(1-超级管理员，2-管理员，3-商家，4-写手)");

    /**
     * The column <code>zhulin.user.pid</code>. 用户所属id
     */
    public final TableField<UserRecord, Integer> PID = createField("pid", org.jooq.impl.SQLDataType.INTEGER, this, "用户所属id");

    /**
     * The column <code>zhulin.user.created_at</code>.
     */
    public final TableField<UserRecord, Timestamp> CREATED_AT = createField("created_at", org.jooq.impl.SQLDataType.TIMESTAMP.defaultValue(org.jooq.impl.DSL.field("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "");

    /**
     * The column <code>zhulin.user.updated_at</code>.
     */
    public final TableField<UserRecord, Timestamp> UPDATED_AT = createField("updated_at", org.jooq.impl.SQLDataType.TIMESTAMP.defaultValue(org.jooq.impl.DSL.field("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "");

    /**
     * The column <code>zhulin.user.number</code>. 编号
     */
    public final TableField<UserRecord, String> NUMBER = createField("number", org.jooq.impl.SQLDataType.VARCHAR(60), this, "编号");

    /**
     * The column <code>zhulin.user.remark</code>. 备注
     */
    public final TableField<UserRecord, String> REMARK = createField("remark", org.jooq.impl.SQLDataType.CLOB, this, "备注");

    /**
     * The column <code>zhulin.user.sex</code>. 性别：0-男，1-女
     */
    public final TableField<UserRecord, Integer> SEX = createField("sex", org.jooq.impl.SQLDataType.INTEGER, this, "性别：0-男，1-女");

    /**
     * The column <code>zhulin.user.age</code>. 年龄
     */
    public final TableField<UserRecord, Integer> AGE = createField("age", org.jooq.impl.SQLDataType.INTEGER, this, "年龄");

    /**
     * The column <code>zhulin.user.profession</code>. 职业
     */
    public final TableField<UserRecord, String> PROFESSION = createField("profession", org.jooq.impl.SQLDataType.VARCHAR(50), this, "职业");

    /**
     * The column <code>zhulin.user.good</code>. 擅长领域
     */
    public final TableField<UserRecord, String> GOOD = createField("good", org.jooq.impl.SQLDataType.VARCHAR(100), this, "擅长领域");

    /**
     * The column <code>zhulin.user.full_time</code>. 0-全职，1-兼职
     */
    public final TableField<UserRecord, Integer> FULL_TIME = createField("full_time", org.jooq.impl.SQLDataType.INTEGER, this, "0-全职，1-兼职");

    /**
     * The column <code>zhulin.user.pay_picture</code>. 支付宝二维码
     */
    public final TableField<UserRecord, String> PAY_PICTURE = createField("pay_picture", org.jooq.impl.SQLDataType.VARCHAR(100), this, "支付宝二维码");

    /**
     * The column <code>zhulin.user.start_level</code>. 用户星级别
     */
    public final TableField<UserRecord, Integer> START_LEVEL = createField("start_level", org.jooq.impl.SQLDataType.INTEGER, this, "用户星级别");

    /**
     * The column <code>zhulin.user.email</code>. 邮箱
     */
    public final TableField<UserRecord, String> EMAIL = createField("email", org.jooq.impl.SQLDataType.VARCHAR(50), this, "邮箱");

    /**
     * The column <code>zhulin.user.result</code>. 审核结果
     */
    public final TableField<UserRecord, String> RESULT = createField("result", org.jooq.impl.SQLDataType.VARCHAR(255), this, "审核结果");

    /**
     * Create a <code>zhulin.user</code> table reference
     */
    public User() {
        this(DSL.name("user"), null);
    }

    /**
     * Create an aliased <code>zhulin.user</code> table reference
     */
    public User(String alias) {
        this(DSL.name(alias), USER);
    }

    /**
     * Create an aliased <code>zhulin.user</code> table reference
     */
    public User(Name alias) {
        this(alias, USER);
    }

    private User(Name alias, Table<UserRecord> aliased) {
        this(alias, aliased, null);
    }

    private User(Name alias, Table<UserRecord> aliased, Field<?>[] parameters) {
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
        return Arrays.<Index>asList(Indexes.USER_PRIMARY, Indexes.USER_UK_ACCOUNT, Indexes.USER_UK_NUMBER);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<UserRecord, Integer> getIdentity() {
        return Keys.IDENTITY_USER;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<UserRecord> getPrimaryKey() {
        return Keys.KEY_USER_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<UserRecord>> getKeys() {
        return Arrays.<UniqueKey<UserRecord>>asList(Keys.KEY_USER_PRIMARY, Keys.KEY_USER_UK_ACCOUNT, Keys.KEY_USER_UK_NUMBER);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User as(String alias) {
        return new User(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User as(Name alias) {
        return new User(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public User rename(String name) {
        return new User(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public User rename(Name name) {
        return new User(name, null);
    }
}
