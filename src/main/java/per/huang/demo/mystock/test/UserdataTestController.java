package per.huang.demo.mystock.test;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import per.huang.demo.mystock.entity.Userdata;
import per.huang.demo.mystock.service.UserdataService;

@Controller
@RequestMapping("test/userdata")
public class UserdataTestController {

    @Autowired
    UserdataService userdataservice;

    //--------------testing---------------
    @GetMapping("/alluser")
    @ResponseBody
    private List<Userdata> userdata(){
        return userdataservice.getAllData();
    }

    @GetMapping("/id/{id}")
    @ResponseBody
    private Userdata getUser(
        @PathVariable("id") int id        
        ){
        return userdataservice.getDataById(id);
    }

    @GetMapping("/name/{name}")
    @ResponseBody
    private Userdata getUser(
        @PathVariable("name") String name        
        ){
        return userdataservice.getDataByNameOrEmail(name);
    }

    @GetMapping("/email/{email}")
    @ResponseBody
    private Userdata getUserByEmail(
        @PathVariable("email") String email        
        ){
        return userdataservice.getDataByNameOrEmail(email);
    }

    @GetMapping("/add/demo")
    @ResponseBody
    private int add(){
        return userdataservice.addData(duck());
    }

    @GetMapping("/update/demo")
    @ResponseBody
    private int update(){
        return userdataservice.updateData(amy());
    }

    @GetMapping("/update/{id}")
    @ResponseBody
    private int updatetime(
        @PathVariable("id") int id
    ){
        return userdataservice.updateLoginTime(id);
    }

    @GetMapping("/delete/{id}")
    @ResponseBody
    private int delete(
        @PathVariable("id") int id
    ){
        return userdataservice.deleteData(id);
    }

    @GetMapping("/getpassword/{name}")
    @ResponseBody
    private String getPassword(
        @PathVariable("name") String name
    ){
        return userdataservice.getPassword(name);
    }

    @GetMapping("/getemail/{name}")
    @ResponseBody
    private String getEmail(
        @PathVariable("name") String name
    ){
        return userdataservice.getEmail(name);
    }

    private Userdata duck(){
        Userdata userdata = new Userdata();
        userdata.setName("Duck");
        userdata.setPassword("Duck");
        userdata.setEmail("duckkkk@gmail.com");
        return userdata;
    }

    private Userdata amy(){
        Userdata userdata = new Userdata();
        userdata.setId(5);
        userdata.setName("amy");
        userdata.setPassword("amy7894");
        userdata.setEmail("amylove@gmail.com");
        return userdata;
    }

}
