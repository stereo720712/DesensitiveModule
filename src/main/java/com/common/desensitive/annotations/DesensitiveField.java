package com.common.desensitive.annotations;


import com.common.desensitive.enums.SensitiveType;

import java.lang.annotation.*;

/**
 * @author sean
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD,ElementType.PARAMETER})
@Inherited
public @interface DesensitiveField {
    SensitiveType value() default SensitiveType.DEFAULT;
}
