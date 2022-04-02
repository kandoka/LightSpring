package com.kandoka.springframework.beans.factory.support;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.kandoka.springframework.beans.BeansException;
import com.kandoka.springframework.beans.PropertyValue;
import com.kandoka.springframework.beans.PropertyValues;
import com.kandoka.springframework.beans.factory.*;
import com.kandoka.springframework.beans.factory.config.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

/**
 * @Description bean工厂抽象类，主要实现了：
 *              1、根据bean定义实例化bean的createBean方法
 *              2、给bean填充属性方法
 *              3、调用初始化前置后置方法
 * @Author handong3
 * @Date 2022/2/15 16:27
 */
public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory implements AutowireCapableBeanFactory {

    private InstantiationStrategy instantiationStrategy = new SimpleInstantiationStrategy();

    /**
     * 根据beanDefinition创建一个bean，并且添加到bean缓存中
     * @param beanName
     * @param beanDefinition
     * @return
     * @throws BeansException
     */
    @Override
    protected Object createBean(String beanName, BeanDefinition beanDefinition, Object[] args) throws BeansException {
        System.out.println("[" + beanName + "] AbstractAutowireCapableBeanFactory.createBean() 开始");
        // 判断是否返回代理 Bean 对象
        Object bean = resolveBeforeInstantiation(beanName, beanDefinition);
        if (null != bean) {
            return bean;
        }

        bean = doCreateBean(beanName, beanDefinition, args);
        System.out.println("[" + beanName + "] AbstractAutowireCapableBeanFactory.createBean() 完成");
        return bean;
    }

    protected Object doCreateBean(String beanName, BeanDefinition beanDefinition, Object[] args){
        System.out.println("[" + beanName + "] AbstractAutowireCapableBeanFactory.doCreateBean() 开始");
        Object bean = null;
        try {
            //根据beanDefinition创建一个bean
            bean = createBeanInstance(beanDefinition, beanName, args);

            //处理循环依赖，将实例化后的Bean对象提前放入缓存中暴露出来
            if (beanDefinition.isSingleton()) {
                Object finalBean = bean;
                addSingletonFactory(beanName, () -> getEarlyBeanReference(beanName, beanDefinition, finalBean));
            }

            //实例化后判断是否继续注入属性
            boolean continueWithPropertyPopulation = applyBeanPostProcessorsAfterInstantiation(beanName, bean);
            if(!continueWithPropertyPopulation){
                return bean;
            }

            //在设置Bean属性之前，允许BeanPostProcessor修改属性值
            applyBeanPostProcessorsBeforeApplyingPropertyValues(beanName, bean, beanDefinition);

            //给bean填充属性
            applyPropertyValues(beanName, bean, beanDefinition);

            //执行Bean的初始化方法和BeanPostProcessor 的前置和后置处理方法
            bean = initializeBean(beanName, bean, beanDefinition);
        } catch (Exception e) {
            throw new BeansException("Instantiation of bean failed", e);
        }

        registerDisposableBeanIfNecessary(beanName, bean, beanDefinition);

        //如果bean是单例，则添加创建好的bean到bean缓存中
        Object exposedObject = bean;
        if (beanDefinition.isSingleton()) {
            // 获取代理对象
            exposedObject = getSingleton(beanName);
            registerSingleton(beanName, exposedObject);
        }
        System.out.println("[" + beanName + "] AbstractAutowireCapableBeanFactory.doCreateBean() 完成");
        return exposedObject;
    }

    /**
     * Bean 实例化后对于返回 false 的对象，不在执行后续设置 Bean 对象属性的操作
     *
     * @param beanName
     * @param bean
     * @return
     */
    private boolean applyBeanPostProcessorsAfterInstantiation(String beanName, Object bean) {
        boolean continueWithPropertyPopulation = true;
        for (BeanPostProcessor beanPostProcessor : getBeanPostProcessors()) {
            if (beanPostProcessor instanceof InstantiationAwareBeanPostProcessor) {
                InstantiationAwareBeanPostProcessor instantiationAwareBeanPostProcessor = (InstantiationAwareBeanPostProcessor) beanPostProcessor;
                //判断是否已经被InstantiationAwareBeanPostProcessor#postProcessAfterInstantiation处理
                if (!instantiationAwareBeanPostProcessor.postProcessAfterInstantiation(bean, beanName)) {
                    continueWithPropertyPopulation = false;
                    break;
                }
            }
        }
        return continueWithPropertyPopulation;
    }

    protected Object getEarlyBeanReference(String beanName, BeanDefinition beanDefinition, Object bean) {
        Object exposedObject = bean;
        for (BeanPostProcessor beanPostProcessor : getBeanPostProcessors()) {
            if (beanPostProcessor instanceof InstantiationAwareBeanPostProcessor) {
                exposedObject = ((InstantiationAwareBeanPostProcessor) beanPostProcessor).getEarlyBeanReference(exposedObject, beanName);
                if (null == exposedObject) {
                    return exposedObject;
                }
            }
        }

        return exposedObject;
    }

    /**
     * 就是在设置 Bean 属性之前，允许 BeanPostProcessor 修改属性值
     *
     * 首先就是获取已经注入的 BeanPostProcessor 集合并从中筛选出继承接口
     * InstantiationAwareBeanPostProcessor的实现类
     *
     * 最后就是调用相应的 postProcessPropertyValues 方法以及循环设置属性值信息，
     * @param beanName
     * @param bean
     * @param beanDefinition
     */
    protected void applyBeanPostProcessorsBeforeApplyingPropertyValues(String beanName, Object bean, BeanDefinition beanDefinition) {
        for(BeanPostProcessor beanPostProcessor: getBeanPostProcessors()){
            if(beanPostProcessor instanceof InstantiationAwareBeanPostProcessor){
                PropertyValues pvs = ((InstantiationAwareBeanPostProcessor) beanPostProcessor)
                        .postProcessPropertyValues(beanDefinition.getPropertyValues(), bean, beanName);
                if(null != pvs){
                    for(PropertyValue propertyValue: pvs.getPropertyValues()){
                        beanDefinition.getPropertyValues().addPropertyValue(propertyValue);
                    }
                }
            }
        }
    }

    protected Object resolveBeforeInstantiation(String beanName, BeanDefinition beanDefinition){
        Object bean = applyBeanPostProcessorsBeforeInstantiation(beanDefinition.getBeanClass(), beanName);
        if (null != bean) {
            bean = applyBeanPostProcessorsAfterInitialization(bean, beanName);
        }
        return bean;
    }

    protected Object applyBeanPostProcessorsBeforeInstantiation(Class beanClass, String beanName){
        for (BeanPostProcessor beanPostProcessor : getBeanPostProcessors()) {
            if (beanPostProcessor instanceof InstantiationAwareBeanPostProcessor) {
                Object result = ((InstantiationAwareBeanPostProcessor) beanPostProcessor)
                        .postProcessBeforeInstantiation(beanClass, beanName);
                if (null != result) {
                    return result;
                }
            }
        }
        return null;
    }

    /**
     * 注册bean的销毁适配器
     * @param beanName
     * @param bean
     * @param beanDefinition
     */
    protected void registerDisposableBeanIfNecessary(String beanName, Object bean,
                                                     BeanDefinition beanDefinition) {
        System.out.println("[" + beanName + "] AbstractAutowir" +
                "eCapableBeanFactory.registerDisposableBeanIfNecessary() 开始");
        if(!beanDefinition.isSingleton()){
            return;
        }
        if (bean instanceof DisposableBean || StrUtil.isNotEmpty(beanDefinition.getDestroyMethodName())) {
            registerDisposableBean(beanName, new DisposableBeanAdapter(bean, beanName, beanDefinition));
        }
        System.out.println("[" + beanName + "] AbstractAutowir" +
                "eCapableBeanFactory.registerDisposableBeanIfNecessary() 结束");
    }

    /**
     * bean属性填充 TODO 处理循环依赖
     * @param beanName
     * @param bean
     * @param beanDefinition
     */
    protected void applyPropertyValues(String beanName, Object bean, BeanDefinition beanDefinition){
        System.out.println("[" + beanName + "] AbstractAutowireCapableBeanFactory.applyPropertyValues() 开始");
        try {
            //循环bean的所有属性，进行属性填充操作
            PropertyValues propertyValues = beanDefinition.getPropertyValues();
            for(PropertyValue propertyValue: propertyValues.getPropertyValues()){

                String name = propertyValue.getName();
                Object value = propertyValue.getValue();

                System.out.println("填充：" + name);

                // 如果A 依赖 B，获取 B 的实例化，可以处理递归依赖 A->B->C...
                if(value instanceof BeanReference){
                    BeanReference beanReference = (BeanReference) value;
                    value = getBean(beanReference.getBeanName());
                }

                //进行属性填充，借用hutool工具类的属性填充方法，也可以自己通过反射机制实现
                BeanUtil.setFieldValue(bean, name, value);
            }
        } catch (Exception e) {
            throw new BeansException("Error setting property values：" + beanName);
        }
        System.out.println("[" + beanName + "] AbstractAutowireCapableBeanFactory.applyPropertyValues() 完成");
    }

    /**
     * 根据对象实例化策略，创建实例
     * @param beanDefinition
     * @param beanName
     * @param args
     * @return
     */
    protected Object createBeanInstance(BeanDefinition beanDefinition, String beanName, Object[] args){
        System.out.println("[" + beanName + "] AbstractAutowireCapableBeanFactory.createBeanInstance() 开始");
        Constructor constructor2Use = null;
        Class<?> beanClass = beanDefinition.getBeanClass();
        Constructor<?>[] declaredConstructors = beanClass.getDeclaredConstructors();
        //找到符合的构造方法，构造参数个数一致
        for(Constructor constructor: declaredConstructors){
            if(null != args && constructor.getParameterTypes().length == args.length){
                constructor2Use = constructor;
                break;
            }
        }
        System.out.println("[" + beanName + "] AbstractAutowireCapableBeanFactory.createBeanInstance() 完成");
        return  getInstantiationStrategy().instantiate(beanDefinition, beanName, constructor2Use, args);
    }

    /**
     * 获取实例化策略
     * @return
     */
    public InstantiationStrategy getInstantiationStrategy() {
        return instantiationStrategy;
    }

    /**
     * 设置实例化策略
     * @param instantiationStrategy
     */
    public void setInstantiationStrategy(InstantiationStrategy instantiationStrategy) {
        this.instantiationStrategy = instantiationStrategy;
    }

    /**
     * 初始化bean
     * @param beanName
     * @param bean
     * @param beanDefinition
     * @return
     */
    private Object initializeBean(String beanName, Object bean, BeanDefinition beanDefinition) throws Exception {
        System.out.println("[" + beanName + "] AbstractAutowireCapableBeanFactory.initializeBean() 开始");
        //处理实现感知的bean，将对应的资源放入bean实例中
        if(bean instanceof Aware){
            //让bean获取BeanFactory实例
            if(bean instanceof BeanFactoryAware){
                ((BeanFactoryAware) bean).setBeanFactory(this);
            }
            //让bean获取BeanClassLoader实例
            if(bean instanceof BeanClassLoaderAware){
                ((BeanClassLoaderAware) bean).setBeanClassLoader(getBeanClassLoader());
            }
            //让bean获取Bean名字
            if(bean instanceof BeanNameAware){
                ((BeanNameAware) bean).setBeanName(beanName);
            }
        }

        // 1. 执行 BeanPostProcessor Before 处理
        Object wrappedBean = applyBeanPostProcessorsBeforeInitialization(bean, beanName);

        // 执行 Bean 对象的初始化方法
        try {
            invokeInitMethods(beanName, wrappedBean, beanDefinition);
        } catch (Exception e) {
         throw new BeansException("Invocation of init method of bean[\" + beanNam\n" +
                 "e + \"] failed", e);
        }

        // 2. 执行 BeanPostProcessor After 处理
        wrappedBean = applyBeanPostProcessorsAfterInitialization(bean, beanName);
        System.out.println("[" + beanName + "] AbstractAutowireCapableBeanFactory.initializeBean() 完成");
        return wrappedBean;
    }

    /**
     * 执行bean的初始化方法 TODO
     * @param beanName
     * @param bean
     * @param beanDefinition
     */
    private void invokeInitMethods(String beanName, Object bean, BeanDefinition beanDefinition) throws Exception {
        System.out.println("[" + beanName + "] AbstractAutowireCapableBeanFactory.invokeInitMethods() 执行");
        //1.调用实现了接口InitializingBean的afterPropertySet方法
        if(bean instanceof InitializingBean) {
            ((InitializingBean) bean).afterPropertiesSet();
        }

        //2.配置信息init-method
        String initMethodName = beanDefinition.getInitMethodName();
        if(StrUtil.isNotEmpty(initMethodName)){
            Method initMethod = beanDefinition.getBeanClass().getMethod(initMethodName);
            if(null == initMethod){
                throw new BeansException("Could not find an init method named'" +
                        initMethodName + "' on bean with name'" + beanName + "'");
            }
            initMethod.invoke(bean);
        }
    }

    /**
     * bean初始化前，调用所有前置处理方法
     * @param existingBean
     * @param beanName
     * @return
     * @throws BeansException
     */
    @Override
    public Object applyBeanPostProcessorsBeforeInitialization(Object existingBean,
                                                              String beanName) throws BeansException {
        System.out.println("[" + beanName + "] AbstractAutowireCapableBeanFactory.applyBeanPostProcessorsBeforeInitialization() 开始");
        Object result = existingBean;
        for (BeanPostProcessor processor : getBeanPostProcessors()) {
            Object current = processor.postProcessBeforeInitialization(result, beanName);
            if (null == current) {
                return result;
            }
            result = current;
        }
        System.out.println("[" + beanName + "] AbstractAutowireCapableBeanFactory.applyBeanPostProcessorsBeforeInitialization() 完成");
        return result;
    }

    /**
     * bean初始化后，调用所有后置处理方法
     * @param existingBean
     * @param beanName
     * @return
     * @throws BeansException
     */
    @Override
    public Object applyBeanPostProcessorsAfterInitialization(Object existingBean, String beanName) throws BeansException {
        System.out.println("[" + beanName + "] AbstractAutowireCapableBeanFactory.applyBeanPostProcessorsAfterInitialization() 开始");
        Object result = existingBean;
        for (BeanPostProcessor processor : getBeanPostProcessors()) {
            Object current = processor.postProcessAfterInitialization(result, beanName);
            if (null == current) {
                return result;
            }
            result = current;
        }
        System.out.println("[" + beanName + "] AbstractAutowireCapableBeanFactory.applyBeanPostProcessorsAfterInitialization() 完成");
        return result;
    }
}
