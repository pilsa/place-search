package com.pilsa.place.framework.aspect;

import com.pilsa.place.common.constant.ApiConstant;
import com.pilsa.place.common.web.vo.request.CommonRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Aspect
@Component
public class ControllerAspect {

    @Pointcut("execution(* com.pilsa.place..*Controller.*(..))")
    public void controllerAdvice(){}

    @Before("controllerAdvice() && args(commonRequest)")
    public void requestLogging(JoinPoint joinPoint, CommonRequest commonRequest){
        MDC.put(ApiConstant.TRANSACTION_ID, commonRequest.getTransactionId());
    }


    @Before("controllerAdvice() && args(commonRequest)")
    public void maintenance(JoinPoint joinPoint, CommonRequest commonRequest){
        /*======================================================================================
         * HttpServletRequest 객체 생성.
        ======================================================================================*/
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

        /*======================================================================================
         * Header 정보를 모두 가져온다. (공통으로 Header 값 검증이 필요의 경우 작성한다.)
        ======================================================================================*/
        Map<String,String> headerMap = getHeaderMap(request);

        /*======================================================================================
         * Controller 수행 전 공통로직을 처리한다.
        ======================================================================================*/

        /*======================================================================================
         * business 에서 사용할 수 있도록 commonRequest 에 Set 한다.
        ======================================================================================*/
        commonRequest.setTransactionId(MDC.get(ApiConstant.TRANSACTION_ID));
    }

    private Map getHeaderMap(HttpServletRequest request) {
        Map<String,String> headerMap = new HashMap();
        Enumeration<String> headerNames = request.getHeaderNames();
        while(headerNames.hasMoreElements()) {
            String key = headerNames.nextElement();
            headerMap.put(key.toLowerCase(),request.getHeader(key));
        }
        return headerMap;
    }


    @AfterReturning("controllerAdvice() && args(commonRequest)")
    public void afterReturning (JoinPoint joinPoint, CommonRequest commonRequest){
    }

    @After("controllerAdvice() && args(commonRequest)")
    public void after (JoinPoint joinPoint, CommonRequest commonRequest){
    }

}