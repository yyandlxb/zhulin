package cn.hlvan.controller;

import cn.hlvan.manager.database.tables.records.PictureRecord;
import cn.hlvan.util.Reply;
import org.jooq.DSLContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static cn.hlvan.manager.database.tables.Picture.PICTURE;

@RestController
@RequestMapping("/order/essay")
public class EssayController {
    private DSLContext dsl;

    public void setDsl(org.jooq.DSLContext dsl) {
        this.dsl = dsl;
    }
    @GetMapping("/picture/list")
    public Reply pictureList(@RequestParam Integer orderEssayId){

        List<PictureRecord> pictureRecords = dsl.selectFrom(PICTURE).where(PICTURE.ORDER_EASSY_ID.eq(orderEssayId)).fetch();
        return Reply.success().data(pictureRecords);
    }
}
