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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import per.huang.demo.mystock.component.validator.PasswordValidator;
import per.huang.demo.mystock.entity.Userdata;
import per.huang.demo.mystock.service.MailService;
import per.huang.demo.mystock.service.UserdataService;

@Controller
@RequestMapping("login/reset")
public class PasswordResetController {

    @Autowired
    private MailService mailService;
    @Autowired
    private UserdataService userdataService;
    @Autowired
    private PasswordValidator passwordValidator;
    @Autowired
    HttpSession session;
    /*
    session:
    Boolean　passwordreset_flag: 用於識別網站action應使用POST or PUT
    Integer passwordreset_count: 用來防止提出過多請求
    Boolean passwordreset_validated: 用於確認是否已寄發驗證信，並且驗證成功
    Boolean passwordreset_success: 用於確認使否已經完成重置，引導頁面跳轉
    */
    

    private String hashcode;
    private Userdata userdata;
    private boolean validated = false;

    @GetMapping("/")
    public String index(
            @ModelAttribute Userdata userdata
            ) {
        if (validated) {
            session.setAttribute("passwordreset_flag", true);
        } else{
            session.setAttribute("passwordreset_flag", false);
        }

        return "passwordreset";
    }

    @PostMapping("/")
    public String checkmail(
            @Valid @ModelAttribute Userdata userdata,
            BindingResult result,
            Model model,
            HttpServletRequest request) {
        this.userdata = userdata;
        // 驗證填入的資訊
        passwordValidator.validate(userdata, result);
        if (result.hasErrors()) {
            model.addAttribute("userdata", userdata);
            return "passwordreset";
        }
        // 產生隨機碼
        Faker faker = Faker.instance();
        hashcode = faker.random().hex(12);
        // 建立session防止多次註冊
        Integer passwordreset_count = (Integer) session.getAttribute("passwordreset_count");
        if (passwordreset_count == null) {
            session.setAttribute("passwordreset_count", 1);
        } else if (passwordreset_count > 3) {
            return "wrong";
        } else {
            passwordreset_count++;
            session.setAttribute("passwordreset_count", passwordreset_count);
        }
        // 重新封裝userdata
        this.userdata = userdataService.getDataByNameOrEmail(userdata.getName());
        // 寄出驗證信
        String email = this.userdata.getEmail();
        String subject = "myspringdemo網頁密碼重設信";
        String path = "https://" + request.getHeader("Host");
        path += "/login/reset/validate/" + hashcode;
        String message = "請點擊以下連結進行密碼重置作業: " + path;
        mailService.prepareAndSend(email, subject, message);
        // 建立session防止隨意訪問認證頁面
        session.setAttribute("passwordreset_validated", false);
        return "passwordreset";
    }

    @GetMapping("/validate/{hashcode}")
    public String validate(
            @PathVariable String hashcode
            ) {
        if (this.hashcode.equals(hashcode)) {
            session.setAttribute("passwordreset_validated", true);
            validated = true;
            return "redirect:../";
        } else {
            return "wrong";
        }
        
    }

    @PutMapping("/")
    public String resetpassword(
            @Valid @ModelAttribute Userdata userdata,
            BindingResult result,
            Model model) {
        passwordValidator.validate(userdata, result);
        if (result.hasErrors()) {
            model.addAttribute("userdata", userdata);
            return "passwordreset";
        }
        String newPassword = userdata.getPassword();
        this.userdata.setPassword(newPassword);
        userdataService.updateData(this.userdata);
        session.setAttribute("passwordreset_success", true);
        return "passwordreset";
    }

    @GetMapping("/success")
    @ResponseBody
    public void success(){
        session.removeAttribute("passwordreset_flag");
        session.removeAttribute("passwordreset_validated");
        session.removeAttribute("passwordreset_success");
    }


}
