package per.huang.demo.mystock.component.validator;

import org.springframework.stereotype.Component;

@Component
public class RegisterValidator {

    private boolean usernameCheck(String input){
        boolean flag = input.matches("[\\p{Alpha}]+");
        if(input.length()<2 || input.length()>20){
            flag = false;
        }
        return flag;
    }

    private boolean password(String input){
        if(input.matches(".*[a-z].*")&&input.matches(".*[A-Z].*")&&input.matches(".*[0-9\\p{Punct}].*")
                &&input.length()>=8&&input.length()<=50){
            return true;
        }else{
            return false;
        }
    }
    
}
