package com.kandoka.springframework.context.annotation;

import java.lang.annotation.*;

/**
 * @Description TODO
 * @Author handong3
 * @Date 2022/4/1 15:10
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Scope {
    String value() default "singleton";
}
