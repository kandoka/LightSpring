package com.kandoka.springframework.context.event;

import com.kandoka.springframework.beans.factory.BeanFactory;
import com.kandoka.springframework.context.ApplicationEvent;
import com.kandoka.springframework.context.ApplicationListener;

/**
 * @Description TODO
 * @Author handong3
 * @Date 2022/3/25 16:50
 */
public class SimpleApplicationEventMulticaster extends AbstractApplicationEventMulticaster {

    public SimpleApplicationEventMulticaster(BeanFactory beanFactory) {
        setBeanFactory(beanFactory);
    }

    @Override
    public void multicastEvent(ApplicationEvent event) {
        for (final ApplicationListener listener : getApplicationListeners(event)) {
            listener.onApplicationEvent(event);
        }
    }
}
