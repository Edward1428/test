package carSystem.com.annotation;

import org.springframework.core.MethodParameter;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * Created by zhanzhenchao on 16/4/27.
 */
public class RequestAttributeResolver implements HandlerMethodArgumentResolver {

    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterAnnotation(RequestAttribute.class) != null;
    }

    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        RequestAttribute attr = parameter.getParameterAnnotation(RequestAttribute.class);
        if (null != attr && null != parameter) {
            String name = StringUtils.isEmpty(attr.value()) ? parameter.getParameterName() : attr.value();
            if (null != name) {
                return webRequest.getAttribute(name, WebRequest.SCOPE_REQUEST);
            }
        }
        return null;
    }

}
