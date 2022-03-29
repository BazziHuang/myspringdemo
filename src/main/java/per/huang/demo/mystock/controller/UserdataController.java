package per.huang.demo.mystock.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import per.huang.demo.mystock.repository.UserdataDao;

@Controller
public class UserdataController {

    @Autowired
    UserdataDao userdataDao;

    //      --------------testing---------------
    @GetMapping("/test/userdata")
    @ResponseBody
    private List<Map<String, Object>> userdata(){
        return userdataDao.queryAll();
    }

}
