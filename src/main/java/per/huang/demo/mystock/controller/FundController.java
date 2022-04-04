package per.huang.demo.mystock.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import per.huang.demo.mystock.entity.Fund;
import per.huang.demo.mystock.service.FundService;

@RestController
@RequestMapping("fund/fund/rawdata")
public class FundController {

    @Autowired
    private FundService<Fund> fundService;
    
    @GetMapping("/")
    public List<Fund> index(){
        List<Fund> funds = fundService.getAllData();
        funds = funds.stream().collect(Collectors.mapping(fund ->{
            if(fund.getFundstocks().size()>0 && fund.getFundstocks().get(0).getId() == null)
			fund.setFundstocks(null); 
            return fund;
        }
        , Collectors.toList()));
        return funds;
    }

    @GetMapping("/page/{pageNumber}")
    public List<Fund> showPage(
        @PathVariable("pageNumber") int pageNumber
    ){
        int offset = (pageNumber-1)*FundService.LIMIT;
        return fundService.getDataWithLimit(offset, FundService.LIMIT);
    }

    @GetMapping("/totalPageCount")
    public int totalPageCount(){
        int count = fundService.getDataCount();
        int totalPage = count / FundService.LIMIT + (count % FundService.LIMIT == 0 ? 0 : 1);
        return totalPage;
    }

    @GetMapping("/{id}")
    public Fund get(
        @PathVariable("id") int id
    ){
        return fundService.getDataById(id);
    }

    @PostMapping("/")
    public int add(
        @RequestBody Fund fund
    ) {
        return fundService.addData(fund);
    }

    @PutMapping("/")
    public int update(
        @RequestBody Fund fund
    ) {
        return fundService.updateData(fund);
    }

    @DeleteMapping("/{id}")
    public int delete(
        @PathVariable("id") int id
    ) {
        return fundService.deleteData(id); 
    }
    
}
