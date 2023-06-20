
package com.hzh.all.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author dahuang
 * @version : CustomParameterValidation.java, v 0.1 2023-06-18 11:43 dahuang
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface CustomParameterValidation {
}