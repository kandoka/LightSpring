package com.kandoka.springframework.beans.factory.support;

import com.kandoka.springframework.beans.BeansException;
import com.kandoka.springframework.beans.factory.FactoryBean;
import com.kandoka.springframework.beans.factory.config.BeanDefinition;
import com.kandoka.springframework.beans.factory.config.BeanPostProcessor;
import com.kandoka.springframework.beans.factory.config.ConfigurableBeanFactory;
import com.kandoka.springframework.util.ClassUtils;
import com.kandoka.springframework.util.StringValueResolver;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description bean工厂，负责bean的定义、注册、获取
 * @Author handong3
 * @Date 2022/2/15 16:23
 */
public abstract class AbstractBeanFactory extends FactoryBeanRegistrySupport implements ConfigurableBeanFactory {

    /** ClassLoader to resolve bean class names with, if necessary */
    private ClassLoader beanClassLoader = ClassUtils.getDefaultClassLoader();

    /** BeanPostProcessors to apply in createBean */
    private final List<BeanPostProcessor> beanPostProcessors = new ArrayList<BeanPostProcessor>();

    /**
     * String resolvers to apply e.g. to annotation attribute values
     */
    private final List<StringValueResolver> embeddedValueResolvers = new ArrayList<>();

    /**
     * 获取Bean
     * 首先尝试从bean缓存中获取bean <br>
     * 如果缓存中找不到，说明bean未被创建，则先创建bean，<br>
     *      创建bean时会根据对应的beanDefinition创建这个bean<br>
     *          尝试从beanDefinition缓存中获取beanDefinition<br>
     *      创建好bean后，将bean放入bean缓存中，以便以后获取<br>
     * @param name
     * @return
     * @throws BeansException
     */
    @Override
    public Object getBean(String name) throws BeansException {
        return doGetBean(name, null);
    }

    /**
     * 获取Bean
     * 首先尝试从bean缓存中获取bean <br>
     * 如果缓存中找不到，说明bean未被创建，则先创建bean，<br>
     *      创建bean时会根据对应的beanDefinition创建这个bean<br>
     *          尝试从beanDefinition缓存中获取beanDefinition<br>
     *      创建好bean后，将bean放入bean缓存中，以便以后获取<br>
     * @param name
     * @param args bean的构造参数
     * @return
     * @throws BeansException
     */
    @Override
    public Object getBean(String name, Object... args) throws BeansException {
        return doGetBean(name, args);
    }

    @Override
    public <T> T getBean(String name, Class<T> requiredType) throws BeansException {
        return (T) getBean(name);
    }

    /**
     * 获取Bean
     * 首先尝试从bean缓存中获取bean <br>
     * 如果缓存中找不到，说明bean未被创建，则先创建bean，<br>
     *      创建bean时会根据对应的beanDefinition创建这个bean<br>
     *          尝试从beanDefinition缓存中获取beanDefinition<br>
     *      创建好bean后，将bean放入bean缓存中，以便以后获取<br>
     * @param name
     * @param args
     * @param <T>
     * @return
     */
    protected <T> T doGetBean(final String name, final Object[] args) {
        System.out.println("["+name+"] AbstractBeanFactory.doGetBean() 开始");
        Object sharedInstance = getSingleton(name);
        if (sharedInstance != null) {
            // 如果是 FactoryBean，则需要调用 FactoryBean#getObject
            return (T) getObjectForBeanInstance(sharedInstance, name);
        }

        BeanDefinition beanDefinition = getBeanDefinition(name);
        Object bean = createBean(name, beanDefinition, args);
        System.out.println("["+name+"] AbstractBeanFactory.doGetBean() 完成");
        return (T) getObjectForBeanInstance(bean, name);
    }

    private Object getObjectForBeanInstance(Object beanInstance, String beanName) {
        if (!(beanInstance instanceof FactoryBean)) {
            return beanInstance;
        }

        Object object = getCachedObjectForFactoryBean(beanName);

        if (object == null) {
            FactoryBean<?> factoryBean = (FactoryBean<?>) beanInstance;
            object = getObjectFromFactoryBean(factoryBean, beanName);
        }

        return object;
    }


    protected abstract BeanDefinition getBeanDefinition(String beanName) throws BeansException;

    protected abstract Object createBean(String beanName, BeanDefinition beanDefinition, Object[] args) throws BeansException;

    @Override
    public void addBeanPostProcessor(BeanPostProcessor beanPostProcessor){
        this.beanPostProcessors.remove(beanPostProcessor);
        this.beanPostProcessors.add(beanPostProcessor);
    }

    /**
     * Return the list of BeanPostProcessors that will get applied
     * to beans created with this factory.
     */
    public List<BeanPostProcessor> getBeanPostProcessors() {
        return this.beanPostProcessors;
    }

    public ClassLoader getBeanClassLoader() {
        return this.beanClassLoader;
    }

    @Override
    public void addEmbeddedValueResolver(StringValueResolver valueResolver) {
        this.embeddedValueResolvers.add(valueResolver);
    }

    @Override
    public String resolveEmbeddedValue(String value) {
        String result = value;
        for (StringValueResolver resolver : this.embeddedValueResolvers) {
            result = resolver.resolveStringValue(result);
        }
        return result;
    }
}
