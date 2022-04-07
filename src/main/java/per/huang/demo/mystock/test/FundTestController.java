package per.huang.demo.mystock.test;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import per.huang.demo.mystock.entity.Fund;
import per.huang.demo.mystock.repository.FundDao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/fund/test")
public class FundTestController {

    @Autowired
    FundDao<Fund> fundDao;

    @GetMapping("/alldata")
    public List<Fund> findAllData() {
        return fundDao.findAll().get();
    }

    @GetMapping("/offset/{offset}")
    public List<Fund> QueryPage(@PathVariable Integer offset){
        return fundDao.findDataWithLimit(offset, 5).get();
    }

    @GetMapping("/name/{name}")
    public Fund getFundByName(@PathVariable("name") String name){
        List<Fund> fund = fundDao.findByName(name).orElse(null);
        if(fund.isEmpty()){
            return null;
        }
        return fund.get(0);
    }

    @GetMapping("/id/{id}")
    public Fund findFundById(@PathVariable("id") Integer id){
        return fundDao.findById(id).orElse(null);
    }

    @GetMapping("/insert/demo")
    public Integer insertFund(){
        return fundDao.insert(getAFund());
    }

    

    @GetMapping("/update/demo2")
    public Integer update(){
        return fundDao.update(getAFund2());
    }

    @GetMapping("/delete/{id}")
    public Integer deleteFundById(@PathVariable("id") Integer id){
        return fundDao.deleteById(id);
    }

    private Fund getAFund(){
        Fund fund = new Fund();
        fund.setName("ship");
        return fund;
    }

    private Fund getAFund2(){
        Fund fund = new Fund();
        fund.setId(8);
        fund.setName("plastic");
        return fund;
    }



    
    
}
