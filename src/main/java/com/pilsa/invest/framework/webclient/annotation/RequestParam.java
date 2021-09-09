package com.pilsa.invest.framework.webclient.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface RequestParam {
    String name();
    boolean exceptValue() default false;
    boolean isIncludeZero() default true;
}
