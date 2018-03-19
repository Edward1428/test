package carSystem.com.annotation;

import java.lang.annotation.*;

/**
 * Created by zhanzhenchao on 16/4/27.
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequestAttribute {
    String value() default "";
}
