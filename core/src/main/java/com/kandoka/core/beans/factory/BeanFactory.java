package com.kandoka.core.beans.factory;

/**
 * @Description Bean容器接口
 * @Author handong3
 * @Date 2022/10/31 18:01
 */
public interface BeanFactory {
    Object getBean(String name) throws Exception;
}
