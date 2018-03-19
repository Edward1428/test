package carSystem.com.annotation;

import carSystem.com.bean.Role;

import java.lang.annotation.*;

/**
 * Created by zhanzhenchao on 16/4/28.
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LoginRequired {
    Role role() default Role.ALL;
}
