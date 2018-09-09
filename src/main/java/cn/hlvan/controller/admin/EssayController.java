package cn.hlvan.controller.admin;

import cn.hlvan.service.OrderService;
import cn.hlvan.util.Reply;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController("essayOrderController")
@RequestMapping("/admin/writer/essay")
public class EssayController {

    @PostMapping("/driver_record/create")
    @ResponseBody
    public Reply driverRecodeAdd( @Valid OrderService.DriverForm driverForm) {
//        packService.createDriverRecord(driverForm);
        System.out.println(driverForm);
        return Reply.success();
    }
}
