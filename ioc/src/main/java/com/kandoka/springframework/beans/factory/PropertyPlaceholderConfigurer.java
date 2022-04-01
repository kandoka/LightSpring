package com.kandoka.springframework.beans.factory;

import com.kandoka.springframework.beans.BeansException;
import com.kandoka.springframework.beans.PropertyValue;
import com.kandoka.springframework.beans.PropertyValues;
import com.kandoka.springframework.beans.factory.config.BeanDefinition;
import com.kandoka.springframework.beans.factory.config.BeanFactoryPostProcessor;
import com.kandoka.springframework.core.io.DefaultResourceLoader;
import com.kandoka.springframework.core.io.Resource;

import java.util.Properties;

/**
 * @Description 处理占位符配置类
 *              依赖于 BeanFactoryPostProcessor 在 Bean 生命周期的属性性，可以在 Bean 对象
 *              实例化之前，改变属性信息。所以这里通过实现 BeanFactoryPostProcessor 接
 *              口，完成对配置文件的加载以及摘取占位符中的在属性文件里的配置。这样就可以把提取到的
 *              配置信息放置到属性配置中了
 * @Author handong3
 * @Date 2022/4/1 14:54
 */
public class PropertyPlaceholderConfigurer implements BeanFactoryPostProcessor {

    public static final String DEFAULT_PLACEHOLDER_PREFIX = "${";
    public static final String DEFAULT_PLACEHOLDER_SUFFIX = "}";
    private String location;

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        //加载属性文件
        try {
            DefaultResourceLoader resourceLoader = new DefaultResourceLoader();
            Resource resource = resourceLoader.getResource(location);
            Properties properties = new Properties();
            properties.load(resource.getInputStream());

            String[] beanDefinitionNames = beanFactory.getBeanDefinitionNames();
            for(String beanName: beanDefinitionNames){
                BeanDefinition beanDefinition = beanFactory.getBeanDefinition(beanName);

                PropertyValues propertyValues = beanDefinition.getPropertyValues();
                for(PropertyValue propertyValue: propertyValues.getPropertyValues()){
                    Object value = propertyValue.getValue();
                    if(!(value instanceof String)){
                        continue;
                    }
                    String strValue = (String) value;
                    StringBuilder builder = new StringBuilder(strValue);
                    int startIdx = strValue.indexOf(DEFAULT_PLACEHOLDER_PREFIX);
                    int stopIdx = strValue.indexOf(DEFAULT_PLACEHOLDER_SUFFIX);
                    if(startIdx != -1 && stopIdx != -1 && startIdx < stopIdx){
                        String propKey = strValue.substring(startIdx + 2, stopIdx);
                        String propVal = properties.getProperty(propKey);
                        builder.replace(startIdx, stopIdx + 1, propVal);
                        propertyValues.addPropertyValue(new PropertyValue(propertyValue.getName(), builder.toString()));
                    }
                }
            }
        } catch (Exception e) {
            throw new BeansException("Could not load properties", e);
        }
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
