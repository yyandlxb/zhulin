package cn.hlvan.service.merchant;

import cn.hlvan.constant.OrderStatus;
import cn.hlvan.manager.database.tables.records.OrderRecord;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static cn.hlvan.manager.database.tables.Order.ORDER;

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

    public List<Condition> buildConditions(OrderQueryForm orderForm) {
        List<Condition> list = new ArrayList<>();
        list.add(ORDER.USER_ID.eq(orderForm.getId()));
        String startTime = orderForm.getStartTime();
        if (StringUtils.isNotBlank(startTime)){
            LocalDateTime start = LocalDateTime.of(LocalDate.parse(startTime), LocalTime.MIN);
            list.add(ORDER.CREATED_AT.greaterOrEqual(Timestamp.valueOf(start)));
        }

        String endTime = orderForm.getEndTime();
        if (StringUtils.isNotBlank(startTime)){
            LocalDateTime end = LocalDateTime.of(LocalDate.parse(endTime), LocalTime.MAX);
            list.add(ORDER.CREATED_AT.lessThan(Timestamp.valueOf(end)));
        }
        Integer status = orderForm.getStatus();
        if (null != status){

            list.add(ORDER.ORDER_STATUS.eq(status.byteValue()));
        }
        String orderCode = orderForm.getOrderCode();
        if (StringUtils.isNotBlank(orderCode)){
            list.add(ORDER.ORDER_CODE.contains(orderCode));
        }
        return list;
    }

    public boolean update(OrderRecord orderRecord) {
        return dsl.executeUpdate(orderRecord) > 0;
    }

    public Integer delete(Integer[] ids,Integer userId) {
       return dsl.update(ORDER).set(ORDER.ORDER_STATUS,OrderStatus.DELETE)
                 .where(ORDER.ORDER_STATUS.eq(OrderStatus.WAIT_AUDITING))
                 .and(ORDER.ID.in(ids))
                 .and(ORDER.USER_ID.eq(userId))
                 .execute() ;
    }

    @Data
    public class OrderForm{
        @NotNull
        private Integer total;//文章数量
        private BigDecimal merchantPrice;//商户定价
        @NotNull
        private String eassyType;//文章领域
        private String notes;//备注
        @NotNull
        private String orderTitle;//订单标题
        private Double originalLevel;//原创度要求
        private Integer picture;//图片数量要求
        private Byte type;//类型 0-流量文，1-养号文
        @NotNull
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        private LocalDate endTime;//截止交稿时间
        private String require;//要求
        private Integer wordCount;//字数要求

    }

    @Data
    public class OrderQueryForm{
        private Integer id;
        private String startTime;
        private String endTime;

        private String orderCode;
        private Integer status;

    }

}
