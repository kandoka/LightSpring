package com.kandoka.springframework.stereotype;

import java.lang.annotation.*;

import static java.lang.annotation.RetentionPolicy.*;

/**
 * @Description TODO
 * @Author handong3
 * @Date 2022/4/1 15:12
 */
@Target(ElementType.TYPE)
@Retention(RUNTIME)
@Documented
public @interface Component {
    String value() default "";
}
