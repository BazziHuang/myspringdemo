package per.huang.demo.mystock.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import per.huang.demo.mystock.service.UserdataService;

@Controller
public class UserdataController {

    @Autowired
    UserdataService userdataService;
    
}
