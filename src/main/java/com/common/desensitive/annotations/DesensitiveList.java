package com.common.desensitive.annotations;


import java.lang.annotation.*;

/**
 * @author sean
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DesensitiveList {
    String value() default "";
}
