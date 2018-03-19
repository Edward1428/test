package carSystem.com.interceptor;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * 用户Cookie sid拦截器
 * Created by zhanzhenchao on 16/4/28.
 */
public class SessionInterceptor extends HandlerInterceptorAdapter {

    @Value("${cookie_timeout}")
    private String cookie_timeOut;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws Exception {
        Cookie[] cookies = request.getCookies();
        if (null != cookies) {
            for (Cookie cookie : cookies) {
                if ("sid".equals(cookie.getName())) {
                    String sid = cookie.getValue();
                    if (StringUtils.isNotEmpty(sid)) {
                        return super.preHandle(request, response, handler);
                    }
                }
            }
        }
        String sid = UUID.randomUUID().toString();
        Cookie newCookie = new Cookie("sid", sid);
        newCookie.setPath("/");
        int cookie_timeout;
        if (StringUtils.isBlank(cookie_timeOut)) {
            cookie_timeout = 31556926;
        } else {
            cookie_timeout = Integer.parseInt(cookie_timeOut);
        }
        newCookie.setMaxAge(cookie_timeout);
        response.addCookie(newCookie);
        return super.preHandle(request, response, handler);
    }
}
