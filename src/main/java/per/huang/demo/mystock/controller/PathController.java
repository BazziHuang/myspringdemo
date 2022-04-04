package per.huang.demo.mystock.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PathController {

    @GetMapping("/fund/fund/")
    public String fund(){
        return "fund";
    }
    
}
