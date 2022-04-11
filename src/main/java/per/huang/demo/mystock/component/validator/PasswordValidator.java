package per.huang.demo.mystock.component.validator;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

import per.huang.demo.mystock.entity.Userdata;
import per.huang.demo.mystock.exception.DataNotFoundException;

@Component
public class PasswordValidator extends RegisterValidator {

    @Autowired
    HttpSession session;

    @Override
    public void validate(Object target, Errors errors) {

        Userdata userdata = (Userdata) target;
        String name = userdata.getName();
        String password = userdata.getPassword();
        String checkpassword = userdata.getCheckpassword();
        Boolean passwordreset_success = (Boolean) session.getAttribute("passwordreset_validated");
        if (passwordreset_success == null || !passwordreset_success) {
            ValidationUtils.rejectIfEmpty(errors, "name", "userdata.name.empty");
            if (name.trim().isEmpty()) {
                return;
            }
            try {
                //驗證使用者名稱或者信箱是否存在
                userdataService.getDataByNameOrEmail(name);
            } catch (DataNotFoundException e) {
                if (name.contains("@")) {
                    errors.rejectValue("name", "userdata.email.notexists");
                } else {
                    errors.rejectValue("name", "userdata.name.notexists");
                }
            }
            return;
        } else {
            //驗證密碼
            ValidationUtils.rejectIfEmpty(errors, "password", "userdata.password.empty");
            ValidationUtils.rejectIfEmpty(errors, "checkpassword", "userdata.checkpassword.empty");
            if (password.trim().isEmpty() || checkpassword.trim().isEmpty()) {
                return;
            }
            passwordValidate(password, checkpassword, errors);
        }

    }

}
