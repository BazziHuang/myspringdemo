package per.huang.demo.mystock.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import per.huang.demo.mystock.component.validator.LoginValidator;
import per.huang.demo.mystock.entity.Userdata;
import per.huang.demo.mystock.service.UserdataService;

@Controller
@RequestMapping("login")
public class LoginController {

    @Autowired
    UserdataService userdataService;
    @Autowired
    LoginValidator loginValidator;


    @GetMapping("/")
    private String index(
        @ModelAttribute Userdata userdata,
        Model model
    ) {
        model.addAttribute("userdata", userdata);
        return "login";
    }

    @PostMapping("/")
    private String login(
        @Valid @ModelAttribute Userdata userdata,
        BindingResult result,
        HttpSession session,
        Model model
    ) {
        loginValidator.validate(userdata, result);
        if(result.hasErrors()){
            model.addAttribute("userdata", userdata);
            return "login";
        }
        session.setAttribute("user_name", userdata.getName());
        session.setMaxInactiveInterval(60*30);
        return "redirect:../stock/fund/fundstock/";
        
    }

}
