package com.kandoka.springframework.context.event;

import com.kandoka.springframework.context.ApplicationEvent;
import com.kandoka.springframework.context.ApplicationListener;

/**
 * @Description 事件广播器
 * @Author handong3
 * @Date 2022/3/25 15:06
 */
public interface ApplicationEventMulticaster {
    /**
     * 添加事件的监听器
     * @param listener
     */
    void addApplicationListener(ApplicationListener<?> listener);

    /**
     * 移除事件的监听器
     * @param listener
     */
    void removeApplicationListener(ApplicationListener<?> listener);

    /**
     * 广播事件，来让事件监听器收到
     * @param event
     */
    void multicastEvent(ApplicationEvent event);
}
