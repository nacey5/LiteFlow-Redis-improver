
package com.hzh.all.annotation;

import com.hzh.all.GrayScheduling;
import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Component;
import java.lang.annotation.*;

/**
 * @author dahuang
 * @version : LiteComponent.java, v 0.1 2023-06-23 14:14 dahuang
 */
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Component
public @interface LiteComponent {

    @AliasFor(annotation = Component.class, attribute = "value")
    String value() default "";

    GrayScheduling tag() default GrayScheduling.DEFAULT;
}