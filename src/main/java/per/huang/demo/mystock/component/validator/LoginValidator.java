package per.huang.demo.mystock.component.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import per.huang.demo.mystock.entity.Userdata;
import per.huang.demo.mystock.exception.DataNotFoundException;
import per.huang.demo.mystock.service.UserdataService;

@Component
public class LoginValidator implements Validator{

    @Autowired
    UserdataService userdataService;

    @Override
    public boolean supports(Class<?> clazz) {
        return Userdata.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        
        Userdata userdata = (Userdata)target;
        String name = userdata.getName();
        String password = userdata.getPassword();
        ValidationUtils.rejectIfEmpty(errors, "name", "userdata.name.empty");
        ValidationUtils.rejectIfEmpty(errors, "password", "userdata.password.empty");
        if(name.trim().isEmpty() || name.trim().isEmpty()){
            return;
        }
        try{
            Userdata targetdata = userdataService.getDataByNameOrEmail(name);
            if(!password.equals(targetdata.getPassword())){
                errors.rejectValue("password", "userdata.password.wrong");
            }
       
        }catch(DataNotFoundException e){
            String item = e.getItem();
            if(item.equals("user_name")){
                errors.rejectValue("name", "userdata.name.notexists");
            }else if(item.equals("user_email")){
                errors.rejectValue("name", "userdata.email.notexists");
            }

        }
        
    }

    
    
}
