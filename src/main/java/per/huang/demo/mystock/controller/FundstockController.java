package per.huang.demo.mystock.controller;

import java.util.List;

import javax.validation.Valid;
import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import per.huang.demo.mystock.component.FundstockValidator;
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
    @Autowired
    private FundstockValidator fundstockValidator;

    private int pageNumber = -1;
    private int offset = -1;
    private int totalPage = 0;
    private boolean putFlag = false;
    private boolean errorFlage = false;

    @GetMapping("/")
    public String index(@ModelAttribute Fundstock fundstock) {
        pageNumber = -1;
        return "redirect:./page/" + pageNumber;
    }

    @GetMapping("/page/{pageNumber}")
    public String showPage(
            @PathVariable("pageNumber") int pageNumber,
            @Valid @ModelAttribute Fundstock fundstock,
            BindingResult result,
            Model model) {
        if(errorFlage){
            fundstockValidator.validate(fundstock, result);
            errorFlage = false;
        }
        this.pageNumber = pageNumber;
        offset = (pageNumber - 1) * FundService.LIMIT;
        int fundstocksCount = fundstockService.getDataCount();
        totalPage = fundstocksCount / FundService.LIMIT + (fundstocksCount % FundService.LIMIT == 0 ? 0 : 1);

        List<Fundstock> fundstocks = fundstockService.getDataWithLimit(offset, FundService.LIMIT);
        List<Fund> funds = fundService.getAllData();
        if(putFlag){
            model.addAttribute("_method", "PUT");
        }else{
            model.addAttribute("_method", "POST");
        }
        model.addAttribute("fundstocks", fundstocks);
        model.addAttribute("funds", funds);
        model.addAttribute("totalPage", totalPage);

        return "fundstock";
    }

    @GetMapping("/{id}")
    public String get(
        @PathVariable("id") int id,
        RedirectAttributes attributes
    ) {
        Fundstock fundstock = fundstockService.getDataById(id);
        attributes.addFlashAttribute("fundstock", fundstock);
        putFlag = true;
        return "redirect:./page/" + pageNumber;
    }

    @PostMapping("/")
    public String add(
        @Valid @ModelAttribute Fundstock fundstock,
        BindingResult result,
        RedirectAttributes attributes
    ) {
        
        fundstockValidator.validate(fundstock, result);
        if(result.hasErrors()){
            attributes.addFlashAttribute("fundstock", fundstock);
            errorFlage = true;
            return "redirect:./page/" + pageNumber;
        }
        fundstockService.addData(fundstock);
        return "redirect:./";
    }

    @PutMapping("/{id}")
    public String update(
        @PathVariable("id") int id,
        @Valid Fundstock fundstock,
        BindingResult result,
        RedirectAttributes attributes
    ) {
        fundstockValidator.validate(fundstock, result);
        if(result.hasErrors()){
            attributes.addFlashAttribute("fundstock", fundstock);
            putFlag = true;
            return "redirect: ./page/" + pageNumber;
        }
        fundstockService.updateData(fundstock);
        putFlag = false;
        return "redirect:./";
    }

    @DeleteMapping("/{id}")
    public String delete(
        @PathVariable("id") int id
    ) {
        System.out.println("go delete");
        fundstockService.deleteData(id);
        putFlag = false;
        return "redirect:./page/" + pageNumber; 
    }

    // rawdata test
    @GetMapping("/test/rawdata")
    @ResponseBody
    public List<Fund> rawdata() {
        return fundService.getAllData();
    }

}
