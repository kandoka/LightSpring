package com.kandoka.springframework.beans.factory.annotation;

import java.lang.annotation.*;

/**
 * @Description TODO
 * @Author handong3
 * @Date 2022/4/1 16:49
 */
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface Qualifier {
    String value() default "";
}
