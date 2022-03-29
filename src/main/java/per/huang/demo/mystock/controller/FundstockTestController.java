package per.huang.demo.mystock.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import per.huang.demo.mystock.entity.Fundstock;
import per.huang.demo.mystock.repository.FundstockDao;

@RestController
@RequestMapping("/fundstock/test")
public class FundstockTestController {

    @Autowired
    FundstockDao fundstockDao;

    @GetMapping("/alldata")
    public List<Fundstock> findAllData() {
        return fundstockDao.findAll();
    }

    @GetMapping("/symbol/{symbol}")
    public List<Fundstock> getFundstockByName(@PathVariable("symbol") String symbol){
        return fundstockDao.findBySymbol(symbol).orElse(null);
    }

    @GetMapping("/id/{id}")
    public Fundstock findFundstockById(@PathVariable("id") Integer id){
        return fundstockDao.findById(id).orElse(null);
    }

    @GetMapping("/delete/{id}")
    public Integer deleteFundstockById(@PathVariable("id") Integer id){
        return fundstockDao.deleteFundstockById(id);
    }

    @GetMapping("/insert/demo")
    public Integer insertFundstockById(){
        return fundstockDao.insertIntoFundstock(5, "4938.TW", 30000);
    }

    @GetMapping("/update/demo")
    public Integer updateShareById(){
      return fundstockDao.updateShareById(35, 20000);
    }

    @GetMapping("/offset/{offset}")
    public List<Fundstock> QueryPage(@PathVariable Integer offset){
      return fundstockDao.findFundstocksWithLimit(offset, 5).get();
    }

    @GetMapping("/count")
    public Long getCount(){
      return fundstockDao.count();
    }
    
}
