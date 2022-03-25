package com.kandoka.springframework.context;

/**
 * @Description 事件的发布者接口，所有事件都需要从这个接口发布出去
 * @Author handong3
 * @Date 2022/3/25 17:07
 */
public interface ApplicationEventPublisher {
    /**
     * Notify all listeners registered with this application of an application
     * event. Events may be framework events (such as RequestHandledEvent)
     * or application-specific events.
     * @param event the event to publish
     */
    void publishEvent(ApplicationEvent event);
}
