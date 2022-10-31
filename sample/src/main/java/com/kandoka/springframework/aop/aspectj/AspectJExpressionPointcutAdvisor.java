package com.kandoka.springframework.aop.aspectj;

import com.kandoka.springframework.aop.Pointcut;
import com.kandoka.springframework.aop.PointcutAdvisor;
import org.aopalliance.aop.Advice;

/**
 * @Description  PointcutAdvisor接口的实现类，把切面pointcut、拦截方法advice和具体的
 *              拦截方法表达式包装在一起。这样可以在xml的配置中定义一个pointcutAdvisor
 *              切面拦截器了
 * @Author handong3
 * @Date 2022/4/1 10:55
 */
public class AspectJExpressionPointcutAdvisor implements PointcutAdvisor {

    //切面
    private AspectJExpressionPointcut pointcut;
    //具体的拦截方法
    private Advice advice;
    //表达式
    private String expression;

    public void setExpression(String expression){
        this.expression = expression;
    }

    @Override
    public Pointcut getPointcut() {
        if (null == pointcut) {
            pointcut = new AspectJExpressionPointcut(expression);
        }
        return pointcut;
    }

    @Override
    public Advice getAdvice() {
        return advice;
    }

    public void setAdvice(Advice advice) {
        this.advice = advice;
    }
}
