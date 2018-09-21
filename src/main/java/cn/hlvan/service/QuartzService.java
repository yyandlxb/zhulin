package cn.hlvan.service;

import org.jooq.DSLContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import static cn.hlvan.constant.OrderStatus.END;
import static cn.hlvan.constant.OrderStatus.PUBLICING;
import static cn.hlvan.constant.WriterOrderStatus.ALREADY_END;
import static cn.hlvan.manager.database.tables.Order.ORDER;
import static cn.hlvan.manager.database.tables.UserOrder.USER_ORDER;

@Component
public class QuartzService {
    private DSLContext dsl;
    private static Logger logger = LoggerFactory.getLogger(QuartzService.class);
    @Autowired
    public void setDsl(DSLContext dsl) {
        this.dsl = dsl;
    }

    @Scheduled(cron = "0 0/5 * * * ?")
    @Transactional
    public void order(){
         dsl.update(ORDER).set(ORDER.ORDER_STATUS, END).where(ORDER.ORDER_STATUS.eq(PUBLICING))
                       .and(ORDER.END_TIME.lessOrEqual(Timestamp.valueOf(LocalDateTime.now()))).execute();
        List<String> list = dsl.select(ORDER.ORDER_CODE).from(ORDER).where(ORDER.ORDER_STATUS.eq(END)).fetchInto(String.class);
        dsl.update(USER_ORDER).set(USER_ORDER.STATUS,ALREADY_END).where(USER_ORDER.ORDER_CODE.in(list)).execute();

    }
}
