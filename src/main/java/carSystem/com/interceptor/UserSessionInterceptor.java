package carSystem.com.interceptor;

import carSystem.com.annotation.LoginRequired;
import carSystem.com.bean.Role;
import carSystem.com.bean.User;
import carSystem.com.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 用户登录拦截器
 *
 * Created by zhanzhenchao on 16/4/28.
 */
public class UserSessionInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod method = (HandlerMethod) handler;
            LoginRequired annotation = getClassOrMethodAnnotationByClassFirst(method);
            if (null != annotation) {
                response.setCharacterEncoding("utf-8");
                response.setContentType("application/json");
                boolean flag = true;
                String sid = getCookieValue(request, "sid");
                if (StringUtils.isNoneBlank(sid)) {
                    User user = userService.findSid(sid);
                    if (null == user) {
                        response.getWriter().write("{\"status\": 0,\"msg\":\"请登录\"}");
                        response.setStatus(403);
                        flag = false;
                    } else {
                        if (annotation.role().getRole() == Role.ALL.getRole()) {
                            request.setAttribute("user", user);
                            flag = true;
                        } else if (annotation.role().getRole() == user.getRole()) {
                            request.setAttribute("user", user);
                            flag = true;
                        } else {
                            response.getWriter().write("{\"status\": 0,\"msg\":\"权限不足\"}");
                            response.setStatus(403);
                            flag = false;
                        }
                    }
                    return flag;
                } else {
                    response.getWriter().write("{\"status\": 0,\"msg\":\"请登录\"}");
                    return false;
                }
            }
        }
        return super.preHandle(request, response, handler);
    }

    private LoginRequired getClassOrMethodAnnotationByClassFirst(HandlerMethod method) {
        LoginRequired annotation = method.getBeanType().getAnnotation(LoginRequired.class);
        if (null == annotation) {
            annotation = method.getMethodAnnotation(LoginRequired.class);
        }
        return annotation;
    }

    private String getCookieValue(HttpServletRequest request, String key) {
        Cookie[] cookies = request.getCookies();
        if (null != cookies) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(key)) {
                    return cookie.getValue();
                }
            }
        }
        return "";
    }
}
