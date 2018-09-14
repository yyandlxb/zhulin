package cn.hlvan.service.admin;

import cn.hlvan.manager.database.tables.records.UserMoneyRecord;
import cn.hlvan.manager.database.tables.records.UserRecord;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.commons.codec.digest.DigestUtils;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import java.math.BigInteger;

import static cn.hlvan.manager.database.tables.User.USER;

@Service
@Transactional
public class UserService {

    @Autowired
    private DSLContext dsl;
    @Transactional
    public  boolean updateAdmin(Integer[] id, Integer adminId) {
        return dsl.update(USER).set(USER.PID,adminId).where(USER.ID.in(id)).execute() > 0;
    }

    @Transactional
    public boolean addUser(UserRecord userRecord){
        dsl.executeInsert(userRecord);
        Integer userId = dsl.lastID().intValue();
        //添加余额表
        UserMoneyRecord userMoneyRecord = new UserMoneyRecord();
        userMoneyRecord.setUserId(userId);
        return dsl.executeInsert(userMoneyRecord)>0;
    }

    public void modifyPassword(String password, Integer id) {
        dsl.update(USER).set(USER.PASSWORD,password).where(USER.ID.eq(id)).execute();
    }

    public boolean resetPassword(String phoneNumber, String password) {
       return dsl.update(USER).set(USER.PASSWORD,DigestUtils.md5Hex(password))
           .where(USER.ACCOUNT.eq(phoneNumber)).execute() > 0;
    }

    public boolean delete(Integer id) {
        return dsl.deleteFrom(USER).where(USER.ID.eq(id)).execute() > 0;
    }
    public boolean update(UserRecord userRecord) {
        return dsl.executeUpdate(userRecord) > 0;
    }

    @Transactional
    public boolean updateUser(UserRecord userRecord) {
        return dsl.executeUpdate(userRecord)>0;
    }

    @Data
    public class UserForm{
        @NotBlank
        private Integer id;
        private String phoneNumber;
        private String password;
        private String name;
        private String code;
    }



    @Data
    @AllArgsConstructor
    public class Account{
        public String phoneNUmber;
    }
}
