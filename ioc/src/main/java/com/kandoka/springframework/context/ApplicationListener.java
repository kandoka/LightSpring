package com.kandoka.springframework.context;

import java.util.EventListener;

/**
 * @Description TODO
 * @Author handong3
 * @Date 2022/3/25 15:08
 */
public interface ApplicationListener<E extends ApplicationEvent> extends EventListener {
    /**
     * Handle an application event.
     * @param event the event to respond to
     */
    void onApplicationEvent(E event);
}
