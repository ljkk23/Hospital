package edu.swu.cs.aspect;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import edu.swu.cs.Exception.SystemException;
import edu.swu.cs.annotation.systemLog;
import edu.swu.cs.domain.ResponseResult;
import edu.swu.cs.domain.securityEntity.UserDetailsImpl;
import edu.swu.cs.enums.AppHttpCodeEnum;
import lombok.extern.slf4j.Slf4j;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

@Component
@Aspect
@Slf4j
public class logAspect {
    @Pointcut("@annotation(edu.swu.cs.annotation.systemLog)")
    public void pt(){

    }

    @Around("pt()")
    public Object printLog(ProceedingJoinPoint joinPoint) throws Throwable {
        Object ret=null;
        try {
            handleBefore(joinPoint);
            ret = joinPoint.proceed();
            handleAfter(ret);
        } finally {
            log.info("--------end-------"+System.lineSeparator());
        }
        return ret;

    }

    private void handleAfter(Object ret) {
        // 打印出参
        log.info("Response       : {}", JSON.toJSONString(ret));
    }

    private void handleBefore(ProceedingJoinPoint joinPoint) {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        //getSystemLog
        MethodSignature signature = (MethodSignature)joinPoint.getSignature();
        systemLog systemLog = signature.getMethod().getAnnotation(systemLog.class);

        log.info("=======Start=======");
        // 打印请求 URL
        log.info("URL            : {}",request.getRequestURI());
        // 打印描述信息
        log.info("BusinessName   : {}", systemLog.businessName());
        // 打印 Http method
        log.info("HTTP Method    : {}", request.getMethod());
        // 打印调用 controller 的全路径以及执行方法
        log.info("Class Method   : {}.{}", joinPoint.getSignature().getDeclaringTypeName(),((MethodSignature)joinPoint.getSignature()).getName());
        // 打印请求的 IP
        log.info("IP             : {}",request.getRequestURL());
        String jsonString = JSON.toJSONString(joinPoint.getArgs());
        // 打印请求入参
        log.info("Request Args   : {}", jsonString);

        JSONArray objects = JSONObject.parseArray(jsonString);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl)authentication.getPrincipal();
        //如果传过来的参数有userName
        if(!Objects.isNull(objects)){
            JSONObject jsonObject = objects.getJSONObject(0);
            if(!Objects.isNull(jsonObject.get("userName"))){
                if(!Objects.equals(userDetails.getUsername(),jsonObject.get("userName"))){
                    throw new SystemException(AppHttpCodeEnum.WRONG_OPERATOR);
                }
            }
        }

    }
}
