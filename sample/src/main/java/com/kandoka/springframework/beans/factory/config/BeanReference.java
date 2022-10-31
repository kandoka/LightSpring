package com.kandoka.springframework.beans.factory.config;

/**
 * @Description bean的引用类，解决bean内的属性包含另一个需要被实例化的bean的情况 和 循环依赖问题
 * @Author handong3
 * @Date 2022/2/16 15:23
 */
public class BeanReference {

    private final String beanName;

    public BeanReference(String beanName) {
        this.beanName = beanName;
    }

    public String getBeanName() {
        return beanName;
    }
}
