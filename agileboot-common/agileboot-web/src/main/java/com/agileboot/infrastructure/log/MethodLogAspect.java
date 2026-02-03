package com.agileboot.infrastructure.log;

import cn.hutool.json.JSONUtil;
import com.agileboot.common.utils.jackson.JacksonUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
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
public class MethodLogAspect {

    // @Pointcut("execution(* com.agileboot..mapper..*(..))")
    // public void mapperLayer() {
    // }

    @Pointcut("execution(* com.agileboot..manager..*(..))")
    public void managerLayer() {
    }

    @Pointcut("execution(* com.agileboot..service..*(..))")
    public void serviceLayer() {
    }

    @Pointcut("execution(* com.agileboot..controller..*(..))")
    public void controllerLayer() {
    }

    @Pointcut("execution(public * com.agileboot..db.*Service.*(..))")
    public void dbServiceLayer() {
    }


    /////////////////////////////////////////// dbServiceLog ///////////////////////////////////////////
    @Pointcut("dbServiceLayer()")
    public void dbServiceLog() {
    }

    @Around("dbServiceLog()")
    public Object aroundDbService(ProceedingJoinPoint joinPoint) throws Throwable {
        Object proceed = joinPoint.proceed();
        log.info("DB SERVICE : {} ; REQUEST：{} ; RESPONSE : {}", joinPoint.getSignature().toShortString(),
            safeToJson(joinPoint.getArgs()), safeToJson(proceed));
        return proceed;
    }

    @AfterThrowing(value = "dbServiceLog()", throwing = "e")
    public void afterDbServiceThrow(JoinPoint joinPoint, Exception e) {
        log.error("DB SERVICE : {} ; REQUEST：{} ; EXCEPTION : {}", joinPoint.getSignature().toShortString(),
            safeToJson(joinPoint.getArgs()), e.getMessage());
    }

    /////////////////////////////////////////// managerLog ///////////////////////////////////////////
    @Pointcut("managerLayer()")
    public void managerLog() {
    }

    @Around("managerLog()")
    public Object aroundManager(ProceedingJoinPoint joinPoint) throws Throwable {
        Object proceed = joinPoint.proceed();
        log.info("MANAGER : {} ; REQUEST：{} ; RESPONSE : {}", joinPoint.getSignature().toShortString(),
            safeToJson(joinPoint.getArgs()), safeToJson(proceed));
        return proceed;
    }

    @AfterThrowing(value = "managerLog()", throwing = "e")
    public void afterManagerThrow(JoinPoint joinPoint, Exception e) {
        log.error("MANAGER : {} ; REQUEST：{} ; EXCEPTION : {}", joinPoint.getSignature().toShortString(),
            safeToJson(joinPoint.getArgs()), e.getMessage());
    }

    /////////////////////////////////////////// serviceLog ///////////////////////////////////////////
    @Pointcut("serviceLayer()")
    public void serviceLog() {
    }

    @Around("serviceLog()")
    public Object aroundService(ProceedingJoinPoint joinPoint) throws Throwable {
        Object proceed = joinPoint.proceed();
        log.info("SERVICE : {} ; REQUEST：{} ; RESPONSE : {}", joinPoint.getSignature().toShortString(),
            safeToJson(joinPoint.getArgs()), safeToJson(proceed));
        return proceed;
    }

    @AfterThrowing(value = "serviceLog()", throwing = "e")
    public void afterServiceThrow(JoinPoint joinPoint, Exception e) {
        log.error("SERVICE : {} ; REQUEST：{} ; EXCEPTION : {}", joinPoint.getSignature().toShortString(),
            safeToJson(joinPoint.getArgs()), e.getMessage());
    }


    /////////////////////////////////////////// applicationServiceLog ///////////////////////////////////////////
    @Pointcut("bean(*ApplicationService)")
    public void applicationServiceLog() {
    }

    @Around("applicationServiceLog()")
    public Object aroundApplicationService(ProceedingJoinPoint joinPoint) throws Throwable {
        Object proceed = joinPoint.proceed();
        log.info("APPLICATION SERVICE : {} ; REQUEST：{} ; RESPONSE : {}", joinPoint.getSignature().toShortString(),
            safeToJson(joinPoint.getArgs()), safeToJson(proceed));
        return proceed;
    }

    @AfterThrowing(value = "applicationServiceLog()", throwing = "e")
    public void afterApplicationServiceThrow(JoinPoint joinPoint, Exception e) {
        log.error("APPLICATION SERVICE : {} ; REQUEST：{} ; EXCEPTION : {}", joinPoint.getSignature().toShortString(),
            safeToJson(joinPoint.getArgs()), e.getMessage());
    }


    /////////////////////////////////////////// controllerLog ///////////////////////////////////////////
    @Pointcut("controllerLayer()")
    public void controllerLog() {
    }

    @Around("controllerLog()")
    public Object aroundController(ProceedingJoinPoint joinPoint) throws Throwable {
        Object proceed = joinPoint.proceed();
        log.info("CONTROLLER : {} ; REQUEST：{} ; RESPONSE : {}", joinPoint.getSignature().toShortString(),
            safeToJson(joinPoint.getArgs()), safeToJson(proceed));
        return proceed;
    }

    @AfterThrowing(value = "controllerLog()", throwing = "e")
    public void afterControllerThrow(JoinPoint joinPoint, Exception e) {
        log.error("CONTROLLER : {} ; REQUEST：{} ; EXCEPTION : {}", joinPoint.getSignature().toShortString(),
            safeToJson(joinPoint.getArgs()), e.getMessage());
    }


    /**
     * 安全的打印出Json字符串 因为Jackson的Json格式化要求比较高，可能会报错 如果报错的话 使用Hutool的JSON工具 如果还是报错，直接使用对象的toString方法即可 目的只是为了打印参数和返回值
     * 逻辑上不用太严格
     */
    private String safeToJson(Object o) {
        if (o == null) {
            return "null";
        }
        String json = null;
        try {
            json = JacksonUtil.to(o);
        } catch (Exception e) {
            json = JSONUtil.toJsonStr(o);
        } finally {
            if (json == null) {
                json = o.toString();
            }
        }
        return json;
    }


}
