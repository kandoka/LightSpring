package com.kandoka.springframework.aop.aspectj;

import com.kandoka.springframework.aop.ClassFilter;
import com.kandoka.springframework.aop.MethodMatcher;
import com.kandoka.springframework.aop.Pointcut;
import com.kandoka.springframework.util.LogUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.weaver.tools.PointcutExpression;
import org.aspectj.weaver.tools.PointcutParser;
import org.aspectj.weaver.tools.PointcutPrimitive;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

/**
 * @Description 切点表达式类
 * @Author handong3
 * @Date 2022/3/31 16:11
 */
@Slf4j
public class AspectJExpressionPointcut implements Pointcut, ClassFilter, MethodMatcher {

    private static final Set<PointcutPrimitive> SUPPORTED_PRIMITIVES = new HashSet<PointcutPrimitive>();

    static {
        SUPPORTED_PRIMITIVES.add(PointcutPrimitive.EXECUTION);
    }

    private final PointcutExpression pointcutExpression;

    public AspectJExpressionPointcut(String expression) {
        PointcutParser pointcutParser = PointcutParser.getPointcutParserSupportingSpecifiedPrimitivesAndUsingSpecifiedClassLoaderForResolution(SUPPORTED_PRIMITIVES, this.getClass().getClassLoader());
        pointcutExpression = pointcutParser.parsePointcutExpression(expression);
    }

    /**
     * 对类进行匹配
     * @param clazz
     * @return
     */
    @Override
    public boolean matches(Class<?> clazz) {
        log.info("[{}]AspectJExpressionPointcut#matches() 执行匹配类", clazz);
        return pointcutExpression.couldMatchJoinPointsInType(clazz);
    }

    /**
     * 对类中改的方法进行匹配
     * @param method
     * @param targetClass
     * @return
     */
    @Override
    public boolean matches(Method method, Class<?> targetClass) {
        log.info("[{}]AspectJExpressionPointcut#matches() 执行匹配方法：{}", targetClass, method.getName());
        return pointcutExpression.matchesMethodExecution(method).alwaysMatches();
    }

    @Override
    public ClassFilter getClassFilter() {
        return this;
    }

    @Override
    public MethodMatcher getMethodMatcher() {
        return this;
    }
}
