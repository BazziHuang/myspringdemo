package per.huang.demo.mystock.controller.validator;

import java.io.IOException;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import per.huang.demo.mystock.entity.Fundstock;
import yahoofinance.YahooFinance;

public class FundstockValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return FundstockValidator.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Fundstock fundstock = (Fundstock) target;
        yahoofinance.Stock yStock = null;
        try {
            yStock = YahooFinance.get(fundstock.getSymbol());
            Integer share = fundstock.getShare();
            if (fundstock.getSymbol() != null && yStock == null) {
                errors.rejectValue("symbol", "{fundstock.symbol.notfound}");
            }
            if (share != null && share % 1000 != 0) {
                errors.rejectValue("share", "{fundstock.share.range}");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
