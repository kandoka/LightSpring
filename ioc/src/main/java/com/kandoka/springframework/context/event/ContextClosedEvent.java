package com.kandoka.springframework.context.event;

/**
 * @Description 上下文关闭事件
 * @Author handong3
 * @Date 2022/3/25 15:02
 */
public class ContextClosedEvent extends ApplicationContextEvent{
    /**
     * Constructs a prototypical Event.
     *
     * @param source The object on which the Event initially occurred.
     * @throws IllegalArgumentException if source is null.
     */
    public ContextClosedEvent(Object source) {
        super(source);
    }
}
