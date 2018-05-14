package net.helpscout.routingds;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Aspect
@Component
public class ReadOnlyConnectionInterceptor implements Ordered {

    private int order;

    @Value("20")
    public void setOrder(int order) {
        this.order = order;
    }

    @Override
    public int getOrder() {
        return order;
    }

    @Around("@annotation(transactional)")
    public Object proceed(ProceedingJoinPoint pjp, Transactional transactional) throws Throwable {
        try {
            if (transactional.readOnly()) {
                System.out.println("Setting slave connection");
                DbContextHolder.setDbType(AppConfig.DataSourceName.SLAVE);
            }
            return pjp.proceed();
        } finally {
            // restore state
            DbContextHolder.clearDbType();
        }
    }
}
