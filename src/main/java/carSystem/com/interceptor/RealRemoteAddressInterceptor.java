package carSystem.com.interceptor;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 访问IP拦截器
 * Created by Rico on 16/4/27.
 */
public class RealRemoteAddressInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception{
        String address = request.getHeader("X-Forwarded-For");
        if (address == null) {
            address = request.getHeader("X-Real-IP");
            if (address == null) {
                address = request.getRemoteAddr();
            }
        }
        if (address.contains(",")) {
            address = address.split(",")[0].trim();
        }
        request.setAttribute("realRemoteAddress", address);
        return super.preHandle(request, response, handler);
    }

}
