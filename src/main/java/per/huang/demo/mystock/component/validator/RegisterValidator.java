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
public class RegisterValidator implements Validator {

    @Autowired
    UserdataService userdataService;

    @Override
    public boolean supports(Class<?> clazz) {
        return Userdata.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        Userdata userdata = (Userdata) target;
        String name = userdata.getName();
        String password = userdata.getPassword();
        String email = userdata.getEmail();
        String checkpassword = userdata.getCheckpassword();

        ValidationUtils.rejectIfEmpty(errors, "name", "userdata.name.empty");
        ValidationUtils.rejectIfEmpty(errors, "email", "userdata.email.empty");
        ValidationUtils.rejectIfEmpty(errors, "password", "userdata.password.empty");
        ValidationUtils.rejectIfEmpty(errors, "checkpassword", "userdata.checkpassword.empty");
        if (name.trim().isEmpty() || email.trim().isEmpty() || password.trim().isEmpty()
                || checkpassword.trim().isEmpty()) {
            return;
        }
        if (!passwordCheck(password)) {
            errors.rejectValue("password", "userdata.password.invalid");
        }
        if (password.trim().length() < 8 || password.trim().length() > 50) {
            errors.rejectValue("password", "userdata.password.length", new Object[] { 8, 50 }, "輸入密碼長度必須介於8~50之間");
        }
        if (!password.equals(checkpassword)) {
            errors.rejectValue("checkpassword", "userdata.checkpassword.wrong");
        }
        if (!usernameCheck(name)) {
            errors.rejectValue("name", "userdata.name.invalid");
        }
        if (name.trim().length() < 2 || name.trim().length() > 20) {
            errors.rejectValue("name", "userdata.name.length", new Object[] { 2, 20 }, "輸入使用者名稱長度必須介於2~20之間");
        }
        if (!email.contains("@")) {
            errors.rejectValue("email", "userdata.email.invalid");
        }
        Userdata checkName = null;
        Userdata checkEmail = null;
        try {
            checkName = userdataService.getDataByNameOrEmail(name);
            checkEmail = userdataService.getDataByNameOrEmail(email);
        } catch (DataNotFoundException e) {
        }
        if (checkName != null) {
            errors.rejectValue("name", "userdata.name.alreadyexists");
        }
        if (checkEmail != null) {
            errors.rejectValue("email", "userdata.email.alreadyexists");
        }

    }

    private boolean usernameCheck(String input) {
        return input.matches("[\\p{Alpha}]+");
    }

    private boolean passwordCheck(String input) {
        if (input.matches(".*[a-z].*") && input.matches(".*[A-Z].*") && input.matches(".*[0-9\\p{Punct}].*")) {
            return true;
        } else {
            return false;
        }
    }

}
