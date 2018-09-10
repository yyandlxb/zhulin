package cn.hlvan.controller.writer;

import cn.hlvan.configure.RequestJson;
import cn.hlvan.manager.database.tables.records.OrderEssayRecord;
import cn.hlvan.manager.database.tables.records.OrderRecord;
import cn.hlvan.security.AuthorizedUser;
import cn.hlvan.security.session.Authenticated;
import cn.hlvan.service.EssayOrderService;
import cn.hlvan.util.Reply;
import cn.hlvan.view.MerchantOrderDetail;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

import static cn.hlvan.manager.database.tables.Order.ORDER;
import static cn.hlvan.manager.database.tables.OrderEssay.ORDER_ESSAY;
import static cn.hlvan.manager.database.tables.UserOrder.USER_ORDER;

@RestController("writerEssayOrderController")
@RequestMapping("/writer/essay")
public class EssayOrderController {
    private DSLContext dsl;

    @Autowired
    public void setDsl(DSLContext dsl) {
        this.dsl = dsl;
    }

    private EssayOrderService essayOrderService;

    @Autowired
    public void setEssayOrderService(EssayOrderService essayOrderService) {
        this.essayOrderService = essayOrderService;
    }

    @PostMapping("/create")
    public Reply addEssay(@RequestJson(value = "fileName") String fileName, @Authenticated AuthorizedUser user,
                          @RequestJson(value = "userOrderId") Integer userOrderId,
                          @RequestJson(value = "essayTitle") String essayTitle) {
        try {
            boolean b = essayOrderService.createEssay(fileName, user.getId(), userOrderId, essayTitle);
            if (b) {
                return Reply.success();
            } else {
                return Reply.fail().message("添加文章失败");
            }
        } catch (IOException e) {
            return Reply.fail().message("添加文章失败");
        }
    }

    @GetMapping("/list")
    public Reply list(@RequestParam Integer userOrderId, @Authenticated AuthorizedUser user) {
        MerchantOrderDetail merchantOrderDetail = new MerchantOrderDetail();
        OrderRecord orderRecord = dsl.select(ORDER.fields()).from(USER_ORDER)
                                     .innerJoin(ORDER).on(ORDER.ORDER_CODE.eq(USER_ORDER.ORDER_CODE))
                                     .and(USER_ORDER.ID.eq(userOrderId))
                                     .and(USER_ORDER.USER_ID.eq(user.getId()))
                                     .fetchSingleInto(OrderRecord.class);
        List<OrderEssayRecord> orderEssayRecords = dsl.selectFrom(ORDER_ESSAY)
                                                      .where(ORDER_ESSAY.USER_ORDER_ID.eq(userOrderId))
                                                      .fetch();
        merchantOrderDetail.setOrderRecord(orderRecord);
        merchantOrderDetail.setOrderEssayRecords(orderEssayRecords);
        return Reply.success().data(merchantOrderDetail);
    }

    @GetMapping("/detail")
    public Reply detail(@RequestParam Integer essayOrderId) {
        OrderEssayRecord orderEssayRecord = dsl.selectFrom(ORDER_ESSAY)
                                               .where(ORDER_ESSAY.ID.eq(essayOrderId))
                                               .fetchSingle();
        return Reply.success().data(orderEssayRecord);
    }

    @PostMapping("/update")
    public Reply detail(@RequestJson(value = "fileName") String fileName, @Authenticated AuthorizedUser user,
                        @RequestJson(value = "essayOrderId") Integer essayOrderId,
                        @RequestJson(value = "essayTitle") String essayTitle) {
        try {
            boolean b = essayOrderService.updateEssay(fileName, user.getId(), essayOrderId, essayTitle);
            if (b) {
                return Reply.success();
            } else {
                return Reply.fail().message("更新文章失败,此状态不准修改");
            }
        } catch (IOException e) {
            return Reply.fail().message("更新文章失败");
        }
    }

    @PostMapping("/delete")
    public Reply delete(@Authenticated AuthorizedUser user, @RequestJson(value = "essayOrderId") Integer essayOrderId) {
        boolean b = essayOrderService.delete(user.getId(), essayOrderId);
        if (b) {
            return Reply.success();
        } else {
            return Reply.fail().message("删除失败，此状态不准删除");
        }
    }
}
