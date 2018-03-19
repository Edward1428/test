package carSystem.com.interceptor;

import carSystem.com.annotation.ReadOnly;
import carSystem.com.dbmanager.DBContext;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ReadOnlyInterceptor implements Ordered {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Around("@annotation(readOnlyConnection)")
    public Object proceed(ProceedingJoinPoint proceedingJoinPoint, ReadOnly readOnlyConnection) throws Throwable {
        try {
            DBContext.setDbType(DBContext.DbType.SLAVE);
            return proceedingJoinPoint.proceed();
        } finally {
            DBContext.clearDbType();
        }
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
