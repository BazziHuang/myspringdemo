package per.huang.demo.mystock.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import per.huang.demo.mystock.service.MailService;

@Controller
@RequestMapping("mail/send")
public class MailController {

    @Autowired
    private MailService mailService;

    @GetMapping("/")
    @ResponseBody
    public String hello(){
        mailService.prepareAndSend("huangtomy1026@gmail.com", "Hello", "This is testing mail!");
        return "Mail send!";
    }
}
