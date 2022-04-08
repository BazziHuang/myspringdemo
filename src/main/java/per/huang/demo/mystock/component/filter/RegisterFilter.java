package per.huang.demo.mystock.component.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class RegisterFilter implements Filter{

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
            throws IOException, ServletException {
            HttpServletRequest request = (HttpServletRequest)req;
            HttpServletResponse response = (HttpServletResponse)resp;
            Boolean session = (Boolean)request.getSession().getAttribute("register_success");
            if(session==null){
                request.getRequestDispatcher("/wrong").forward(request, response);
                return;
            }
        chain.doFilter(request, response);
    }

    @Bean
    public FilterRegistrationBean<RegisterFilter> registerFilterRegistration(){
        FilterRegistrationBean<RegisterFilter> registrationBean =
            new FilterRegistrationBean<>();
        registrationBean.setFilter(new RegisterFilter());
        registrationBean.addUrlPatterns("/login/register/validate/*");
        registrationBean.setOrder(1);
        return registrationBean;
    }
    
}
