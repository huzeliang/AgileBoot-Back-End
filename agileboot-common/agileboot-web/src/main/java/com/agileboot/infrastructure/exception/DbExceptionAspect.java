package com.agileboot.infrastructure.exception;

import cn.hutool.core.map.MapUtil;
import com.agileboot.common.exception.ApiException;
import com.agileboot.common.exception.error.ErrorCode.Internal;
import java.sql.SQLException;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.exceptions.PersistenceException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;


/**
 * @author hzl
 */
@Aspect
@Component
@Slf4j
public class DbExceptionAspect {

    @Pointcut("execution(* com.agileboot..mapper..*(..))")
    public void mapperLayer() {
    }

    @Pointcut("execution(* com.agileboot..manager..*(..))")
    public void managerLayer() {
    }

    @Pointcut("execution(* com.agileboot..service..*(..))")
    public void serviceLayer() {
    }

    @Pointcut("execution(* com.agileboot..db..*(..))")
    public void dbLayer() {
    }


    @Pointcut("mapperLayer() || managerLayer() || dbLayer()")
    public void sqlException() {
    }

    /**
     * 包装成ApiException 再交给globalExceptionHandler处理
     *
     * @param joinPoint joinPoint
     * @return object
     */
    @Around("sqlException()")
    public Object aroundDbException(ProceedingJoinPoint joinPoint) throws Throwable {
        Object proceed;
        try {
            // 将应用层的数据库错误 捕获并进行转换  主要捕获 sql形式的异常
            proceed = joinPoint.proceed();
        } catch (ApiException apiException) {
            throw apiException;
        } catch (Exception sqlException) {
            ApiException wrapException = new ApiException(sqlException, Internal.DB_INTERNAL_ERROR);
            wrapException.setPayload(MapUtil.of("detail", sqlException.getMessage()));
            throw wrapException;
        }
        return proceed;
    }

    @Pointcut("serviceLayer() || bean(*ApplicationService)")
    public void serviceDbException() {
    }

    @Around("serviceDbException()")
    public Object aroundApplicationDbException(ProceedingJoinPoint joinPoint) throws Throwable {
        Object proceed;
        try {
            // 将应用层的数据库错误 捕获并进行转换  主要捕获 jpa形式的  insert save 等模型抛出的错误
            proceed = joinPoint.proceed();
        } catch (ApiException ae) {
            throw ae;
        } catch (SQLException | PersistenceException sqlException) {
            ApiException wrapException = new ApiException(sqlException, Internal.DB_INTERNAL_ERROR);
            wrapException.setPayload(MapUtil.of("detail", sqlException.getMessage()));
            throw wrapException;
        }

        return proceed;
    }


}
