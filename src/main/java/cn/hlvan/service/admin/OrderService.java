package cn.hlvan.service.admin;

import org.jooq.DSLContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static cn.hlvan.constant.OrderStatus.AWAIT_RECEPTION;
import static cn.hlvan.manager.database.Tables.ORDER;

@Service
public class OrderService {
    private static Logger logger = LoggerFactory.getLogger(OrderService.class);
    private DSLContext dsl;

    @Autowired
    public void setDsl(DSLContext dsl) {
        this.dsl = dsl;
    }

    public Integer update(Integer[] ids) {
        return dsl.update(ORDER).set(ORDER.ORDER_STATUS, AWAIT_RECEPTION).where(ORDER.ID.in(ids)).execute();
    }
}
