package com.kandoka.springframework.context.support;

import com.kandoka.springframework.beans.BeansException;
import com.kandoka.springframework.beans.factory.ConfigurableListableBeanFactory;
import com.kandoka.springframework.beans.factory.config.BeanFactoryPostProcessor;
import com.kandoka.springframework.beans.factory.config.BeanPostProcessor;
import com.kandoka.springframework.context.ApplicationEvent;
import com.kandoka.springframework.context.ApplicationListener;
import com.kandoka.springframework.context.ConfigurableApplicationContext;
import com.kandoka.springframework.context.event.ApplicationEventMulticaster;
import com.kandoka.springframework.context.event.ContextClosedEvent;
import com.kandoka.springframework.context.event.ContextRefreshedEvent;
import com.kandoka.springframework.context.event.SimpleApplicationEventMulticaster;
import com.kandoka.springframework.core.io.DefaultResourceLoader;

import java.util.Collection;
import java.util.Map;

/**
 * @Description 抽象应用上下文
 * @Author handong3
 * @Date 2022/2/17 15:31
 */
public abstract class AbstractApplicationContext extends DefaultResourceLoader implements ConfigurableApplicationContext {

    //事件发布
    public static final String APPLICATION_EVENT_MULTICASTER_BEAN_NAME = "applicationEventMulticaster";
    private ApplicationEventMulticaster applicationEventMulticaster;

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
        //7. 初始化事件发布者
        initApplicationEventMulticaster();
        //8. 注册事件监听器
        registerListeners();
        //9. 发布容器刷新完成事件
        finishRefresh();
        System.out.println("AbstractApplicationContext.refresh() 完成");
    }

    private void finishRefresh(){
        publishEvent(new ContextRefreshedEvent(this));
    }

    @Override
    public void publishEvent(ApplicationEvent event){
        applicationEventMulticaster.multicastEvent(event);
    }

    /**
     * 注册容器中所有的监听器
     */
    private void registerListeners(){
        Collection<ApplicationListener> applicationListeners = getBeansOfType(ApplicationListener.class).values();
        for(ApplicationListener listener: applicationListeners){
            applicationEventMulticaster.addApplicationListener(listener);
        }
    }

    /**
     * 初始化广播器
     */
    private void initApplicationEventMulticaster(){
        ConfigurableListableBeanFactory beanFactory = getBeanFactory();
        applicationEventMulticaster = new SimpleApplicationEventMulticaster(beanFactory);
        beanFactory.registerSingleton(APPLICATION_EVENT_MULTICASTER_BEAN_NAME, applicationEventMulticaster);
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
    public <T> T getBean(Class<T> requiredType) throws BeansException {
        return getBeanFactory().getBean(requiredType);
    }

    @Override
    public void registerShutdownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread(this::close));
    }

    @Override
    public void close() {
        System.out.println("AbstractApplicationContext.close() 开始");
        publishEvent(new ContextClosedEvent(this));
        getBeanFactory().destroySingletons();
        System.out.println("AbstractApplicationContext.close() 完成");
    }
}
