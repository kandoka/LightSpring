package com.kandoka.springframework.aop;

/**
 * @Description 被代理类的封装类
 * @Author handong3
 * @Date 2022/3/31 16:38
 */
public class TargetSource {
    private final Object target;
    public TargetSource(Object target) {
        this.target = target;
    }
    /**
     * Return the type of targets returned by this {@link TargetSource}.
     * <p>Can return <code>null</code>, although certain usages of a
     * <code>TargetSource</code> might just work with a predetermined
     * target class.
     * @return the type of targets returned by this {@link TargetSource}
     */
    public Class<?>[] getTargetClass(){
        return this.target.getClass().getInterfaces();
    }

    /**
     * Return a target instance. Invoked immediately before the
     * AOP framework calls the "target" of an AOP method invocation.
     * @return the target object, which contains the joinpoint
     * @throws Exception if the target object can't be resolved
     */
    public Object getTarget(){
        return this.target;
    }
}
