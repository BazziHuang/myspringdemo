package per.huang.demo.mystock.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import per.huang.demo.mystock.entity.Fund;
import per.huang.demo.mystock.repository.FundDao;
import per.huang.demo.mystock.service.FundService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/fund/test")
public class FundController {

    @Autowired
    FundDao fundDao;

    @GetMapping("/alldata")
    public List<Fund> findAllData() {
        return fundDao.findAll();
    }

    @GetMapping("/name/{name}")
    public Fund getFundByName(@PathVariable("name") String name){
        return fundDao.findByName(name).orElse(null);
    }

    @GetMapping("/id/{id}")
    public Fund findFundById(@PathVariable("id") Integer id){
        return fundDao.findById(id).orElse(null);
    }

    @GetMapping("/insert/demo")
    public Integer insertFundById(){
        return fundDao.insertIntoFund("Shipping");
    }

    @GetMapping("/update/demo1")
    public Integer updateShareById1(){
      return fundDao.updateFund(7, "Computer");
    }

    @GetMapping("/update/demo2")
    public Integer updateShareById2(){
      return fundDao.updateFund(7, "AI");
    }

    @GetMapping("/delete/{id}")
    public Integer deleteFundById(@PathVariable("id") Integer id){
        return fundDao.deleteFundById(id);
    }



    
    
}
