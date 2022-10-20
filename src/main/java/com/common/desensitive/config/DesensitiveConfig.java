package com.common.desensitive.config;

import com.common.desensitive.annotations.DesensitiveField;
import com.common.desensitive.aspects.DesensitiveAspect;
import org.springframework.context.annotation.*;

@Configuration
@ComponentScan(basePackages = {"com.common.desensitive.**"})
public class DesensitiveConfig {

}
