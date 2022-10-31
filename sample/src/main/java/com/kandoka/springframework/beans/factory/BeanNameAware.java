package com.kandoka.springframework.beans.factory;

/**
 * @Description Interface to be implemented by beans that want to be aware of their bean name in
 *              a bean factory
 * @Author handong3
 * @Date 2022/2/24 14:45
 */
public interface BeanNameAware extends Aware {
    void setBeanName(String name);
}
