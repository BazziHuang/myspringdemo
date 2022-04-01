package per.huang.demo.mystock.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import per.huang.demo.mystock.entity.Fundstock;
import per.huang.demo.mystock.repository.FundDao;

@RestController
@RequestMapping("/fundstock/test")
public class FundstockTestController {

    @Autowired
    FundDao<Fundstock> fundstockDao;

    @GetMapping("/alldata")
    public List<Fundstock> findAllData() {
        return fundstockDao.findAll().get();
    }


    @GetMapping("/id/{id}")
    public Fundstock findFundstockById(@PathVariable("id") Integer id){
        return fundstockDao.findById(id).orElse(null);
    }

    @GetMapping("/name/{name}")
    public List<Fundstock> getFundstocksByName(@PathVariable("name") String name){
        return fundstockDao.findByName(name).orElse(null);
    }

    @GetMapping("/delete/{id}")
    public Integer deleteFundstockById(@PathVariable("id") Integer id){
        return fundstockDao.deleteById(id);  
        //return fundstockDao.deleteFundstockById(id);
    }

    @GetMapping("/insert/demo")
    public Integer insertFundstock(){
        Fundstock fundstock = getAFundstock();
        return fundstockDao.insert(fundstock);
    }

    @GetMapping("/update/demo")
    public Integer updateFundstock(){
        Fundstock fundstock = getAFundstock2();
        return fundstockDao.update(fundstock);
    }

    @GetMapping("/offset/{offset}")
    public List<Fundstock> QueryPage(@PathVariable Integer offset){
        return fundstockDao.findDataWithLimit(offset, 5).get();
    }

    @GetMapping("/count")
    public int getCount(){
      return fundstockDao.count();
    }

    private Fundstock getAFundstock(){
        Fundstock fundstock = new Fundstock();
        fundstock.setFund_id(8);
        fundstock.setSymbol("1536.TW");
        fundstock.setShare(20000);
        return fundstock;
    }

    private Fundstock getAFundstock2(){
        Fundstock fundstock = new Fundstock();
        fundstock.setId(39);
        fundstock.setFund_id(5);
        fundstock.setSymbol("2204.TW");
        fundstock.setShare(50000);
        return fundstock;
    }
    
}
