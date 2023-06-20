
package com.hzh.xml_rule.config.aspect;

import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @author dahuang
 * @version : ValidationMethodAspect.java, v 0.1 2023-06-18 11:45 dahuang
 */
@Aspect
@Component
public class ValidationMethodAspect {
    @Before("@annotation(com.hzh.all.annotation.CustomMethodValidation)")
    public void validateMethodArgs(JoinPoint joinPoint) {
        // 获取方法参数
        Object[] args = joinPoint.getArgs();
        // 遍历方法参数，进行校验
        for (Object arg : args) {
            checkArg(arg);
        }
    }


    public void checkArg(Object arg){
        if (arg == null) {
            throw new IllegalArgumentException("Argument cannot be null");
        }
        if (arg instanceof String){
            if (StringUtils.isBlank((String)arg)){
                throw new IllegalArgumentException("String Argument cannot be empty");
            }
        }
    }
}