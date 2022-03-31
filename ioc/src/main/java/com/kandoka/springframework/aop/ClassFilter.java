package com.kandoka.springframework.aop;

/**
 * @Description 类匹配接口
 * @Author handong3
 * @Date 2022/3/26 17:56
 */
public interface ClassFilter {
    boolean matches(Class<?> clazz);
}
