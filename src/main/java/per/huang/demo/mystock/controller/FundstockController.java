package per.huang.demo.mystock.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import per.huang.demo.mystock.entity.Fund;
import per.huang.demo.mystock.entity.Fundstock;
import per.huang.demo.mystock.service.FundService;

@Controller
@RequestMapping("fund/fundstock")
public class FundstockController {

    @Autowired
    private FundService<Fundstock> fundstockService;
    @Autowired
    private FundService<Fund> fundService;

    private int pageNumber = -1;
    private int offset = -1;
    private int totalPage = 0;

    @GetMapping("/")
    public String index(@ModelAttribute Fundstock fundstock){
        this.pageNumber = -1;
        return "redirect:./page/" + pageNumber;
    }

    @GetMapping("/page/{pageNumber}")
    public String showPage(
        @PathVariable("pageNumber") int pageNumber,
        @ModelAttribute Fundstock fundstock,
        Model model){
            this.pageNumber = pageNumber;
            this.offset = (pageNumber - 1) * FundService.LIMIT;
            int fundstocksCount = fundstockService.getDataCount();
            this.totalPage = fundstocksCount / FundService.LIMIT + (fundstocksCount % FundService.LIMIT == 0 ? 0 : 1);
            List<Fundstock> fundstocks = null;
            if(pageNumber <= 0){
                fundstocks = fundstockService.getAllData();
            }else{
                fundstocks = fundstockService.getDataWithLimit(offset, FundService.LIMIT);
            }
            List<Fund> funds = fundService.getAllData();

            model.addAttribute("_method", "POST");
            model.addAttribute("fundstocks", fundstocks);
            model.addAttribute("funds", funds);
            model.addAttribute("totalPage", totalPage);

        return "fundstock";
    }

    @GetMapping("/test/rawdata")
    @ResponseBody
    public List<Fund> rawdata(){
        return fundService.getAllData();
    }

    




    
    
}
