package cn.hlvan.service;

import org.jooq.DSLContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import static cn.hlvan.constant.OrderStatus.END;
import static cn.hlvan.constant.OrderStatus.PUBLICING;
import static cn.hlvan.constant.WriterOrderStatus.*;
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

    @Value("${appoint.time}")
    private Integer appointTime;

    @Scheduled(cron = "0 0/5 * * * ?")
    @Transactional
    public void order() {
        dsl.update(ORDER).set(ORDER.ORDER_STATUS, END).where(ORDER.ORDER_STATUS.eq(PUBLICING))
           .and(ORDER.END_TIME.lessOrEqual(Timestamp.valueOf(LocalDateTime.now()))).execute();
        List<String> list = dsl.select(ORDER.ORDER_CODE).from(ORDER).where(ORDER.ORDER_STATUS.eq(END)).fetchInto(String.class);
        dsl.update(USER_ORDER).set(USER_ORDER.STATUS, ALREADY_END).where(USER_ORDER.ORDER_CODE.in(list)).execute();

    }

    //定时取消订单
    @Scheduled(cron = "0 0/3 * * * ?")
    @Transactional
    public void cancelOrder() {
        dsl.selectFrom(USER_ORDER).where(USER_ORDER.STATUS.eq(WAIT_APPOINT)).forUpdate().fetch().forEach(e -> {
            if (e.getCreatedAt().toLocalDateTime().plusHours(appointTime).compareTo(LocalDateTime.now()) <= 0) {
                dsl.update(USER_ORDER).set(USER_ORDER.STATUS, DELETE).where(USER_ORDER.ID.eq(e.getId())).execute();
                //增加订单数量
                dsl.selectFrom(ORDER).where(ORDER.ORDER_CODE.eq(e.getOrderCode())).fetch().forEach(r ->

                    dsl.update(ORDER).set(ORDER.TOTAL, r.getTotal() + e.getReserveTotal())
                       .set(ORDER.APPOINT_TOTAL, r.getAppointTotal() - e.getReserveTotal())
                       .where(ORDER.ORDER_CODE.eq(r.getOrderCode())).execute()
                );
            }
        });
    }

}
