package cn.hlvan.service.merchant;

import cn.hlvan.controller.merchant.OrderController;
import cn.hlvan.manager.database.tables.records.OrderRecord;
import lombok.Data;
import org.jooq.DSLContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Service
public class OrderService {

    private static Logger logger = LoggerFactory.getLogger(OrderService.class);
    private DSLContext dsl;
    @Autowired
    public void setDsl(DSLContext dsl) {
        this.dsl = dsl;
    }

    @Transactional
    public Boolean addOrder(OrderRecord orderRecord) {

        return dsl.executeInsert(orderRecord)>0;
    }

    @Data
    public class OrderFrom{
        @NotNull
        private Integer total;
        private BigDecimal merchantPrice;
        @NotNull
        private String eassyType;
        private String notes;
        @NotNull
        private String orderTitle;
        private Double originalLevel;
        private Integer picture;
        private Byte type;
        @NotNull
        private Timestamp endTime;
        private String require;
        private Integer eassyTotal;
        private Integer wordCount;


    }

}
