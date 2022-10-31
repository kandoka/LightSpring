package com.kandoka.springframework.beans.factory.annotation;

import java.lang.annotation.*;

/**
 * @Description TODO
 * @Author handong3
 * @Date 2022/4/1 16:49
 */
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Value {
    /**
     * The actual value expression: e.g. "#{systemProperties.myProp}".
     */
    String value();
}
