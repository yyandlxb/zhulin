package cn.hlvan.service;

import cn.hlvan.controller.admin.SuperAdminController;
import org.jooq.DSLContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;

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

    @Scheduled(cron = "0 0/1 * * * ?")
    @Transactional
    public void order(){


        dsl.selectFrom(ORDER).fetch().forEach( e->
        {
            if (e.getAdminEndTime() != null && e.getAdminEndTime().compareTo(new Date()) < 0){
                boolean b = dsl.update(ORDER).set(ORDER.ORDER_STATUS, END).where(ORDER.ORDER_STATUS.eq(PUBLICING))
                               .and(ORDER.ORDER_CODE.eq(e.getOrderCode())).execute() > 0;
                if (b){
                    dsl.update(USER_ORDER).set(USER_ORDER.STATUS,ALREADY_END).where(USER_ORDER.ORDER_CODE.eq(e.getOrderCode())).execute();
                    logger.info("订单被截稿："+e.getOrderCode());
                }
            }
        });

    }
}
