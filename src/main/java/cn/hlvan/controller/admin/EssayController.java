package cn.hlvan.controller.admin;

import cn.hlvan.service.OrderService;
import cn.hlvan.util.Reply;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController("essayOrderController")
@RequestMapping("/admin/writer/essay")
public class EssayController {
    @Value("${spring.mail.username}")
    private String fromMail;
    private JavaMailSenderImpl javaMailSender;

    @Autowired
    public void setJavaMailSender(JavaMailSenderImpl javaMailSender) {
        this.javaMailSender = javaMailSender;
    }
    @PostMapping("/driver_record/create")
    @ResponseBody
    public Reply driverRecodeAdd( @Valid OrderService.DriverForm driverForm) {
//        packService.createDriverRecord(driverForm);
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromMail);
        message.setTo("ayaoyuan@aliyun.com");
        message.setSubject("管理员分配任务");
        message.setText("管理员分配了文章任务给您，请注意查收，订单号为："
                        + "系统邮件，请勿回复");
        javaMailSender.send(message);
        System.out.println(driverForm);
        return Reply.success();
    }
}
