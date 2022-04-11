package per.huang.demo.mystock.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class PathController {

    @GetMapping("/stock/fund/")
    public String fund(){
        return "fund";
    }
    
    //@GetMapping("/fund/fundstock/")
    public String fundstock(){
        return "fundstock";
    }

    @GetMapping("/")
    public String index(){
        return "redirect:./login/";
    }

    @GetMapping("/wrong")
    public String success() {
        return "wrong";
    }

    @GetMapping("/stock/logout")
    @ResponseBody
    public void logout(
        HttpSession session
    ){
        session.removeAttribute("user_name");
    }
    
}
