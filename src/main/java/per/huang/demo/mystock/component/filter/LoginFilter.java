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
public class LoginFilter implements Filter{

    public LoginFilter(){
        System.out.println("filter...");
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
            throws IOException, ServletException {
            HttpServletRequest request = (HttpServletRequest)req;
            HttpServletResponse response = (HttpServletResponse)resp;
            Object session = request.getSession().getAttribute("user_name");
            //System.out.println("session..."+session);
            if(session==null){
                request.getRequestDispatcher("/login/").forward(request, response);
                return;
            }
        chain.doFilter(request, response);
    }

    @Bean
    public FilterRegistrationBean<LoginFilter> loginFilterRegistration(){
        FilterRegistrationBean<LoginFilter> registrationBean =
            new FilterRegistrationBean<>();
        registrationBean.setFilter(new LoginFilter());
        registrationBean.addUrlPatterns("/stock/*");
        registrationBean.setOrder(1);
        return registrationBean;
    }
    
}
