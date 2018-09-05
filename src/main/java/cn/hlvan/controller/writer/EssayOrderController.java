package cn.hlvan.controller.writer;

import cn.hlvan.util.Reply;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static cn.hlvan.manager.database.Tables.USER_ORDER;

@RestController("essayOrderController")
@RequestMapping("/writer/essay")
public class EssayOrderController {
    private DSLContext dsl;

    @Autowired
    public void setDsl(DSLContext dsl) {
        this.dsl = dsl;
    }

    @PostMapping("/create")
    public Reply addEssay(@RequestParam Integer userOrderId, @RequestParam String essayTitle){

        dsl.select(USER_ORDER.fields()).from(USER_ORDER).where(USER_ORDER.ID.eq(userOrderId));
        return Reply.success();

    }
}
