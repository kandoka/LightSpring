package com.kandoka.springframework.context;

import com.kandoka.springframework.beans.factory.HierarchicalBeanFactory;
import com.kandoka.springframework.beans.factory.ListableBeanFactory;
import com.kandoka.springframework.core.io.ResourceLoader;

/**
 * @Description context 是本次实现应用上下文功能新增的服务包
 *              ApplicationContext，继承于 ListableBeanFactory，也就继承了关于 BeanFactory
 *              方法，比如一些 getBean 的方法。另外 ApplicationContext 本身是 Central 接
 *              口，但目前还不需要添加一些获取 ID 和父类上下文，所以暂时没有接口方法的定
 *              义。
 * @Author handong3
 * @Date 2022/2/17 15:27
 */
public interface ApplicationContext extends ListableBeanFactory, HierarchicalBeanFactory, ResourceLoader, ApplicationEventPublisher {
}
