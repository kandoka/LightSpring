package com.kandoka.springframework.beans.factory;

import com.kandoka.springframework.beans.BeansException;

/**
 * @Description Defines a factory which can return an Object instance
 * @Author handong3
 * @Date 2022/4/1 18:21
 */
public interface ObjectFactory<T> {
    T getObject() throws BeansException;
}
