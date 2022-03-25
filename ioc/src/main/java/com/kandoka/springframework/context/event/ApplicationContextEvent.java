package com.kandoka.springframework.context.event;

import com.kandoka.springframework.context.ApplicationContext;
import com.kandoka.springframework.context.ApplicationEvent;

/**
 * @Description 应用上下文事件
 * @Author handong3
 * @Date 2022/3/25 15:00
 */
public class ApplicationContextEvent extends ApplicationEvent {
    /**
     * Constructs a prototypical Event.
     *
     * @param source The object on which the Event initially occurred.
     * @throws IllegalArgumentException if source is null.
     */
    public ApplicationContextEvent(Object source) {
        super(source);
    }

    /**
     * 获取事件发布时所属的上下文
     * @return
     */
    public final ApplicationContext getApplicationContext() {
        return (ApplicationContext) getSource();
    }
}
