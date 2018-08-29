package cn.hlvan.controller.admin;


import cn.hlvan.util.Reply;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("adminAccountController")
@RequestMapping("/user")
public class AdminController {

    @PostMapping("/auditing")
    public Reply auditing(){


        return Reply.success();

    }


}