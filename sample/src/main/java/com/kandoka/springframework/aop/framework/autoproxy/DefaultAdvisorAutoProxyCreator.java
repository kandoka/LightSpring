package com.kandoka.springframework.aop.framework.autoproxy;

import com.kandoka.springframework.aop.*;
import com.kandoka.springframework.aop.aspectj.AspectJExpressionPointcutAdvisor;
import com.kandoka.springframework.aop.framework.ProxyFactory;
import com.kandoka.springframework.beans.BeansException;
import com.kandoka.springframework.beans.PropertyValues;
import com.kandoka.springframework.beans.factory.BeanFactory;
import com.kandoka.springframework.beans.factory.BeanFactoryAware;
import com.kandoka.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import com.kandoka.springframework.beans.factory.support.DefaultListableBeanFactory;
import com.kandoka.springframework.util.LogUtil;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.aop.Advice;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * @Description 融入 Bean 生命周期的自动代理创建者
 *              这个 DefaultAdvisorAutoProxyCreator 类的主要核心实现在于
 *              postProcessBeforeInstantiation 方法中，从通过 beanFactory.getBeansOfType 获
 *              取 AspectJExpressionPointcutAdvisor 开始。
 *
 *              获取了 advisors 以后就可以遍历相应的 AspectJExpressionPointcutAdvisor 填充对
 *              应的属性信息，包括：目标对象、拦截方法、匹配器，之后返回代理对象即可。
 *
 *              那么现在调用方获取到的这个 Bean 对象就是一个已经被切面注入的对象了，当调
 *              用方法的时候，则会被按需拦截，处理用户需要的信息。
 * @Author handong3
 * @Date 2022/4/1 11:10
 */
@Slf4j
public class DefaultAdvisorAutoProxyCreator implements InstantiationAwareBeanPostProcessor, BeanFactoryAware {

    private DefaultListableBeanFactory beanFactory;

    private final Set<Object> earlyProxyReferences = Collections.synchronizedSet(new HashSet<Object>());

    @Override
    public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
        return true;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = (DefaultListableBeanFactory) beanFactory;
    }

    @Override
    public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
        return null;
    }

    @Override
    public PropertyValues postProcessPropertyValues(PropertyValues pvs, Object bean, String beanName) throws BeansException {
        return pvs;
    }

    private boolean isInfrastructureClass(Class<?> beanClass) {
        return Advice.class.isAssignableFrom(beanClass)
                || Pointcut.class.isAssignableFrom(beanClass)
                || Advisor.class.isAssignableFrom(beanClass);
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    protected Object wrapIfNecessary(Object bean, String beanName) {
        if (isInfrastructureClass(bean.getClass())) {
            return bean;
        }

        Collection<AspectJExpressionPointcutAdvisor> advisors = beanFactory.getBeansOfType(AspectJExpressionPointcutAdvisor.class).values();

        for (AspectJExpressionPointcutAdvisor advisor : advisors) {
            ClassFilter classFilter = advisor.getPointcut().getClassFilter();
            // 过滤匹配类
            if (!classFilter.matches(bean.getClass())) {
                continue;
            }

            AdvisedSupport advisedSupport = new AdvisedSupport();

            TargetSource targetSource = new TargetSource(bean);
            advisedSupport.setTargetSource(targetSource);
            advisedSupport.setMethodInterceptor((MethodInterceptor) advisor.getAdvice());
            advisedSupport.setMethodMatcher(advisor.getPointcut().getMethodMatcher());
            advisedSupport.setProxyTargetClass(true);

            // 返回代理对象
            Object rtn =  new ProxyFactory(advisedSupport).getProxy();
            log.info("[{}]DefaultAdvisorAutoProxyCreator#wrapIfNecessary 返回代理对象：{}", bean.getClass(), rtn.getClass());
            return rtn;
        }

        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (!earlyProxyReferences.contains(beanName)) {
            return wrapIfNecessary(bean, beanName);
        }
        return bean;
    }

    @Override
    public Object getEarlyBeanReference(Object bean, String beanName) {
        earlyProxyReferences.add(beanName);
        return wrapIfNecessary(bean, beanName);
    }
}
