package carSystem.com.config;

import carSystem.com.annotation.RequestAttributeResolver;
import carSystem.com.interceptor.RealRemoteAddressInterceptor;
import carSystem.com.interceptor.SessionInterceptor;
import carSystem.com.interceptor.UserSessionInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.nio.charset.Charset;
import java.util.List;

@Configuration
public class WebMvcConfigurationAdapter extends WebMvcConfigurerAdapter {

    @Bean
    public HttpMessageConverter<String> responseBodyConverter() {
        StringHttpMessageConverter converter = new StringHttpMessageConverter(Charset.forName("UTF-8"));
        return converter;
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        super.configureMessageConverters(converters);
        converters.add(responseBodyConverter());
    }

//    /**
//     * 以前要访问一个页面需要先创建个Controller控制类，再写方法跳转到页面
//     * 在这里配置后就不需要那么麻烦了，直接访问http://localhost:8080/toLogin就跳转到login.jsp页面了
//     * @param registry
//     */
//    @Override
//    public void addViewControllers(ViewControllerRegistry registry) {
//        registry.addViewController("/toLogin").setViewName("login");
//        super.addViewControllers(registry);
//    }

    //需要被注解的拦截器
    @Bean
    public UserSessionInterceptor userSessionInterceptor() {
        return new UserSessionInterceptor();
    }

    /**
     * 拦截器
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // addPathPatterns 用于添加拦截规则
        // excludePathPatterns 用户排除拦截
        registry.addInterceptor(new RealRemoteAddressInterceptor()).addPathPatterns("/**");
        registry.addInterceptor(new SessionInterceptor()).addPathPatterns("/**");
        registry.addInterceptor(userSessionInterceptor()).addPathPatterns("/**");
//                .excludePathPatterns("/api/login", "/api/login/check", "/changeLine");
        super.addInterceptors(registry);
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        //自定义添加注解
        argumentResolvers.add(new RequestAttributeResolver());
    }
}
