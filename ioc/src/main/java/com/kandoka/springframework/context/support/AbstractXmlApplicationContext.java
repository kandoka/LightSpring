package com.kandoka.springframework.context.support;

import com.kandoka.springframework.beans.factory.support.DefaultListableBeanFactory;
import com.kandoka.springframework.beans.factory.xml.XmlBeanDefinitionReader;

/**
 * @Description 应用上下文的抽象类。实现了加载xml资源文件
 * @Author handong3
 * @Date 2022/2/17 15:32
 */
public abstract class AbstractXmlApplicationContext extends AbstractRefreshableApplicationContext {

    /**
     * 加载xml文件配置信息，存入缓存中
     * @param beanFactory
     */
    @Override
    protected void loadBeanDefinitions(DefaultListableBeanFactory beanFactory) {
        XmlBeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader(
                beanFactory, this);
        String[] configLocations = getConfigLocations();
        if (null != configLocations){
            beanDefinitionReader.loadBeanDefinitions(configLocations);
        }
    }
    protected abstract String[] getConfigLocations();
}
