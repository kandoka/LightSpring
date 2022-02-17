package com.kandoka.springframework.context.support;

import com.kandoka.springframework.beans.factory.support.DefaultListableBeanFactory;
import com.kandoka.springframework.beans.factory.xml.XmlBeanDefinitionReader;

/**
 * @Description TODO
 * @Author handong3
 * @Date 2022/2/17 15:32
 */
public abstract class AbstractXmlApplicationContext extends AbstractRefreshableApplicationContext {

    /**
     * 加载xml文件配置信息
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
