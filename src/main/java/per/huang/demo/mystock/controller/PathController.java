package per.huang.demo.mystock.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

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
    
}
