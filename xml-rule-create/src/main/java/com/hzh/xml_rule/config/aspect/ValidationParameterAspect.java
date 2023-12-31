
package com.hzh.xml_rule.config.aspect;

import com.hzh.all.annotation.CustomParameterValidation;
import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * @author dahuang
 * @version : ValidationParameterAspect.java, v 0.1 2023-06-18 11:56 dahuang
 */
@Aspect
@Component
public class ValidationParameterAspect {

    @Around("@annotation(customValidation)")
    public Object validateParameter(ProceedingJoinPoint joinPoint, CustomParameterValidation customValidation) throws Throwable {
        Object[] args = joinPoint.getArgs();
        // 遍历方法参数，找到标记了 @CustomValidation 注解的参数进行校验
        for (Object arg : args) {
            if (arg != null && arg.getClass().isAnnotationPresent(CustomParameterValidation.class)) {
                validateSingle(arg);
            }else {
                throw new IllegalArgumentException("Parameter cannot be null");
            }
        }
        // 继续执行原方法
        return joinPoint.proceed();
    }

    // 参数校验逻辑封装为单独的方法
    private void validateSingle(Object parameter) {
        if (parameter instanceof String) {
            validateStringParameter((String) parameter);
        } else {
            // Add validation logic for other parameter types if needed
        }
    }

    private void validateStringParameter(String parameter) {
        if (StringUtils.isBlank(parameter)) {
            throw new IllegalArgumentException("String Argument cannot be null");
        }
    }
}