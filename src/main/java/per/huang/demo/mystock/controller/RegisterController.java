package per.huang.demo.mystock.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import com.github.javafaker.Faker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import per.huang.demo.mystock.component.validator.RegisterValidator;
import per.huang.demo.mystock.entity.Userdata;
import per.huang.demo.mystock.service.MailService;
import per.huang.demo.mystock.service.UserdataService;

@Controller
@RequestMapping("login/register")
public class RegisterController {

    @Autowired
    private UserdataService userdataService;
    @Autowired
    private MailService mailService;
    @Autowired
    private RegisterValidator registerValidator;

    private String hashcode;
    private Userdata userdata;

    @GetMapping("/")
    public String index(
            @ModelAttribute Userdata userdata,
            Model model) {
        model.addAttribute("userdata", userdata);
        return "register";
    }

    @PostMapping("/")
    public String register(
            @Valid @ModelAttribute Userdata userdata,
            BindingResult result,
            HttpSession session,
            HttpServletRequest request,
            Model model) {
        this.userdata = userdata;
        registerValidator.validate(userdata, result);
        if (result.hasErrors()) {
            model.addAttribute("userdata", userdata);
            return "register";
        }
        // 產生隨機碼
        Faker faker = Faker.instance();
        hashcode = faker.random().hex(12);
        // 建立session防止多次註冊
        Integer register_count = (Integer) session.getAttribute("register_count");
        System.out.println("register_count: " + register_count );
        if (register_count == null) {
            session.setAttribute("register_count", 1);
        } else if (register_count > 3) {
            return "wrong";
        } else {
            register_count++;
            session.setAttribute("register_count", register_count);
        }
        // 寄出認證信
        String email = userdata.getEmail();
        String subject = "myspringdemo網頁認證信";
        //String path = request.getHeader("Host");
        String path = "https://" + request.getHeader("Host");
        path += "/login/register/validate/" + hashcode;
        String message = "請點擊以下連結通過驗證: " + path;
        mailService.prepareAndSend(email, subject, message);
        // 建立session防止隨意訪問認證頁面
        session.setAttribute("register_success", false);
        return "validatepage";
    }

    @GetMapping("/validate/{hashcode}")
    public String validate(
            @PathVariable String hashcode,
            HttpSession session) {
        if (this.hashcode.equals(hashcode)) {
            userdataService.addData(userdata);
            session.setAttribute("register_success", true);
            return "redirect:./success";
        } else {
            return "wrong";
        }
    }

    @GetMapping("/validate/success")
    public String success(
            HttpSession session) {
        Boolean success = (Boolean) session.getAttribute("register_success");
        if (success != null && success) {
            return "success";
        } else {
            return "wrong";
        }
    }

}
