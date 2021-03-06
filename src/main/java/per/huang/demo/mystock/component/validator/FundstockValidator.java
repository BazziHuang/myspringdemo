package per.huang.demo.mystock.component.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import per.huang.demo.mystock.entity.Fundstock;
import yahoofinance.YahooFinance;

@Component
public class FundstockValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return Fundstock.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        Fundstock fundstock = (Fundstock) target;
        ValidationUtils.rejectIfEmpty(errors, "fund_id", "fundstock.fund_id.empty");
        ValidationUtils.rejectIfEmpty(errors, "symbol", "fundstock.symbol.empty");
        ValidationUtils.rejectIfEmpty(errors, "share", "fundstock.share.empty");

        yahoofinance.Stock yStock = null;
        try {
            String symbol = fundstock.getSymbol();
            if(!symbol.contains(".TW")){
                symbol+=".TW";
            }
            yStock = YahooFinance.get(symbol);
            Integer share = fundstock.getShare();
            if (fundstock.getSymbol() != null && yStock == null) {
                errors.rejectValue("symbol", "fundstock.symbol.notfound");
            }
            if(share < 1000){
                errors.rejectValue("share", "fundstock.share.notenough", new Object[]{1000},"買進股數必須大於或等於1000股");
            }
            if (share != null && share % 1000 != 0) {
                errors.rejectValue("share", "fundstock.share.range");
            }
        } catch (Exception e) {
            System.out.println("Exception occurs at FundstockValidator: ");
            e.printStackTrace();
        }
    }

}
