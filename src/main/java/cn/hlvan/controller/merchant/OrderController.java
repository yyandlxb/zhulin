package cn.hlvan.controller.merchant;

import cn.hlvan.constant.OrderStatus;
import cn.hlvan.manager.database.tables.records.OrderRecord;
import cn.hlvan.security.session.Authenticated;
import cn.hlvan.service.merchant.OrderService;
import cn.hlvan.user.controller.AuthorizedUser;
import cn.hlvan.util.Page;
import cn.hlvan.util.Reply;
import org.apache.commons.lang.RandomStringUtils;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;

import static cn.hlvan.manager.database.tables.Order.ORDER;

@RestController("merchantOrderController")
@RequestMapping("/merchant/order")
public class OrderController {

    private static Logger logger = LoggerFactory.getLogger(OrderController.class);
    private DSLContext dsl;
    @Autowired
    public void setDsl(DSLContext dsl) {
        this.dsl = dsl;
    }

    private OrderService orderService;

    @Autowired
    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }


    @GetMapping("/list")
    public Reply list(OrderService.OrderQueryForm orderForm,@Authenticated AuthorizedUser user,Pageable pageable){

        orderForm.setId(user.getId());
        List<Condition> conditions = orderService.buildConditions(orderForm);

        int count = dsl.selectCount().from(ORDER).where(conditions).fetchOne().value1();
        List<OrderRecord> orderRecords;
        if (pageable.getOffset() >= count) {
            orderRecords = Collections.emptyList();
        } else {
            orderRecords = dsl.selectFrom(ORDER)
                              .where(conditions)
                              .orderBy(ORDER.ID.desc())
                              .limit((int) pageable.getOffset(), pageable.getPageSize())
                              .fetchInto(OrderRecord.class);
        }
        return Reply.success().data(new Page<>(orderRecords, pageable, count));
    }

    @GetMapping("/detail")
    public Reply list(@RequestParam Integer id){
        OrderRecord orderRecord = dsl.selectFrom(ORDER).where(ORDER.ID.eq(id)).fetchSingleInto(OrderRecord.class);
        return Reply.success().data(orderRecord);
    }
    @PostMapping("/delete")
    public Reply delete(@RequestParam Integer[] ids,@Authenticated AuthorizedUser user){
        Integer count = orderService.delete(ids,user.getId());
        return Reply.success().data(count);
    }
    @PostMapping("/update")
    public Reply update(OrderService.OrderForm orderForm,@RequestParam Integer id){
        OrderRecord orderRecord = new OrderRecord();
        orderRecord.setId(id);
        orderRecord.from(orderForm);
        Integer integer = dsl.selectCount().from(ORDER)
                             .where(ORDER.ORDER_STATUS.eq(OrderStatus.WAIT_AUDITING)
                                                      .or(ORDER.ORDER_STATUS.eq(OrderStatus.AUDITING_FAIL)))
                             .and(ORDER.ID.eq(id))
                             .fetchOne().value1();

        if (integer > 0){
            boolean b = orderService.update(orderRecord);
            if (b){
                return Reply.success();
            }else {
                return Reply.fail().message("更新订单失败");
            }
        }else {
            return Reply.fail().message("此状态不能进行修改");
        }
    }
    /**
     * 添加订单
     */
    @PostMapping("/create")
    public Reply addOrder(OrderService.OrderForm orderFrom,@Authenticated AuthorizedUser user ){
        OrderRecord orderRecord = new OrderRecord();
        orderRecord.setOrderCode(System.currentTimeMillis()+ RandomStringUtils.randomNumeric(3));
        orderRecord.setUserId(user.getId());
        orderRecord.setTotal(orderFrom.getTotal());
        orderRecord.setMerchantPrice(orderFrom.getMerchantPrice());
        orderRecord.setEassyType(orderFrom.getEassyType());
        orderRecord.setNotes(orderFrom.getNotes());
        orderRecord.setOrderTitle(orderFrom.getOrderTitle());
        orderRecord.setOriginalLevel(orderFrom.getOriginalLevel());
        orderRecord.setPicture(orderFrom.getPicture());
        orderRecord.setType(orderFrom.getType());
        orderRecord.setEndTime(Timestamp.valueOf(LocalDateTime.of(orderFrom.getEndTime(),LocalTime.MIN)));
        orderRecord.setRequire(orderFrom.getRequire());
        orderRecord.setWordCount(orderFrom.getWordCount());
        Boolean b = orderService.addOrder(orderRecord);
        logger.info("订单添加成功"+orderRecord.getOrderCode());
        if(b){
            return Reply.success();
        }else{
            return Reply.fail().message("订单添加失败");
        }
    }
}
