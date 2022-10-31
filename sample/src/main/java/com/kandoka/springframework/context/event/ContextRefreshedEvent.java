package com.kandoka.springframework.context.event;

/**
 * @Description 上下文刷新事件
 * @Author handong3
 * @Date 2022/3/25 15:03
 */
public class ContextRefreshedEvent extends ApplicationContextEvent{
    /**
     * Constructs a prototypical Event.
     *
     * @param source The object on which the Event initially occurred.
     * @throws IllegalArgumentException if source is null.
     */
    public ContextRefreshedEvent(Object source) {
        super(source);
    }
}
