package per.huang.demo.mystock.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user/stock")
public class StockController {

    @GetMapping("/")
    public String index(){
        return "stock";
    }
    
}
