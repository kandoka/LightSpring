package com.kandoka.springframework.context.support;

import com.kandoka.springframework.beans.BeansException;
import com.kandoka.springframework.beans.factory.ConfigurableListableBeanFactory;
import com.kandoka.springframework.beans.factory.config.BeanFactoryPostProcessor;
import com.kandoka.springframework.beans.factory.config.BeanPostProcessor;
import com.kandoka.springframework.context.ConfigurableApplicationContext;
import com.kandoka.springframework.core.io.DefaultResourceLoader;

import java.util.Map;

/**
 * @Description 抽象应用上下文
 * @Author handong3
 * @Date 2022/2/17 15:31
 */
public abstract class AbstractApplicationContext extends DefaultResourceLoader implements ConfigurableApplicationContext {

    /**
     * 刷新应用上下文，处理创建获取bean工厂、加载beanDefinition、执行自定义的bean实例化前后处理方法
     * @throws BeansException
     */
    @Override
    public void refresh() throws BeansException {
        System.out.println("AbstractApplicationContext.refresh() 开始");
        // 1. 创建 BeanFactory，并加载 BeanDefinition
        refreshBeanFactory();
        // 2. 获取 BeanFactory
        ConfigurableListableBeanFactory beanFactory = getBeanFactory();
        //3.添加ApplicationContextAwareProcessor，让实现ApplicationContextAware接口的类实例
        //能感知到所属的ApplicationContext
        beanFactory.addBeanPostProcessor(new ApplicationContextAwareProcessor(this));
        // 4. 在 Bean 实例化之前，执
        //行 BeanFactoryPostProcessor (Invoke factory processors registered as beans in the co
        //ntext.)
        invokeBeanFactoryPostProcessors(beanFactory);
        // 5. BeanPostProcessor 需要提前于其他 Bean 对象实例化之前执行注册操作
        registerBeanPostProcessors(beanFactory);
        // 6. 提前实例化单例 Bean 对象
        beanFactory.preInstantiateSingletons();
        System.out.println("AbstractApplicationContext.refresh() 完成");
    }

    protected abstract void refreshBeanFactory() throws BeansException;

    protected abstract ConfigurableListableBeanFactory getBeanFactory();

    /**
     * 在所有的 BeanDefinition 加载完成后，实例化 Bean 对象之前，执行修
     * 改 BeanDefinition 属性的机制
     * @param beanFactory
     * @throws BeansException
     */
    private void invokeBeanFactoryPostProcessors(ConfigurableListableBeanFactory beanFactory) {
        System.out.println("AbstractApplicationContext.invokeBeanFactoryPostProcessors() 开始");
        //获取bean工厂的所有后置处理器
        Map<String, BeanFactoryPostProcessor> beanFactoryPostProcessorMap =
                beanFactory.getBeansOfType(BeanFactoryPostProcessor.class);
        for (BeanFactoryPostProcessor beanFactoryPostProcessor : beanFactoryPostProcessorMap.values()) {
            beanFactoryPostProcessor.postProcessBeanFactory(beanFactory);
        }
        System.out.println("AbstractApplicationContext.invokeBeanFactoryPostProcessors() 完成");
    }

    /**
     * 注册bean处理器，实例化这些处理器为bean
     * @param beanFactory
     */
    private void registerBeanPostProcessors(ConfigurableListableBeanFactory beanFactory) {
        System.out.println("AbstractApplicationContext.registerBeanPostProcessors() 开始");
        Map<String, BeanPostProcessor> beanPostProcessorMap = beanFactory.getBeansOfType(BeanPostProcessor.class);
        for (BeanPostProcessor beanPostProcessor : beanPostProcessorMap.values()) {
            beanFactory.addBeanPostProcessor(beanPostProcessor);
        }
        System.out.println("AbstractApplicationContext.registerBeanPostProcessors() 完成");
    }

    @Override
    public <T> Map<String, T> getBeansOfType(Class<T> type) throws BeansException {
        return getBeanFactory().getBeansOfType(type);
    }

    @Override
    public String[] getBeanDefinitionNames() {
        return getBeanFactory().getBeanDefinitionNames();
    }

    @Override
    public Object getBean(String name) throws BeansException {
        return getBeanFactory().getBean(name);
    }

    @Override
    public Object getBean(String name, Object... args) throws BeansException {
        return getBeanFactory().getBean(name, args);
    }

    @Override
    public <T> T getBean(String name, Class<T> requiredType) throws BeansException {
        return getBeanFactory().getBean(name, requiredType);
    }

    @Override
    public void registerShutdownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread(this::close));
    }

    @Override
    public void close() {
        System.out.println("AbstractApplicationContext.close() 开始");
        getBeanFactory().destroySingletons();
        System.out.println("AbstractApplicationContext.close() 完成");
    }
}
