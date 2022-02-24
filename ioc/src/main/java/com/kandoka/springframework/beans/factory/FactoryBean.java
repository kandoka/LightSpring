package com.kandoka.springframework.beans.factory;

/**
 * @Description TODO
 * @Author handong3
 * @Date 2022/2/24 15:54
 */
public interface FactoryBean<T> {
    T getObject() throws Exception;
    Class<?> getObjectType();
    boolean isSingleton();
}
