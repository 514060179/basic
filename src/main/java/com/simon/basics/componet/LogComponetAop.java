/**
 *
 */
package com.simon.basics.componet;

import com.simon.basics.util.JSONUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import java.lang.reflect.Method;
import java.util.Map;

/**
 *
 * 可以mongoDb中 统计（大数据）
 */
@Component
@Aspect
public class LogComponetAop {

    protected static Logger logger = LoggerFactory.getLogger(LogComponetAop.class);

    /**
     * param point 切点
     *
     * @return
     * @throws Throwable
     */
    @Around("execution(* com.simon.*.controller..*(..))  ")
    public Object controllerAopLog(ProceedingJoinPoint point) throws Throwable {
        // 计时开始
        StopWatch clock = new StopWatch();
        clock.start();

        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        // 获取请求地址
        String requestUrl = requestAttributes.getRequest().getRequestURI();

        // 请求所在类
        String className = point.getSignature().getDeclaringTypeName();

        Method method = ((MethodSignature) point.getSignature()).getMethod();

        Map<String, String[]> parameterMap = requestAttributes.getRequest().getParameterMap();
        if (null != parameterMap && !parameterMap.isEmpty()) {
            for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
                if ("userid".equalsIgnoreCase(entry.getKey())) {
                    break;
                }
            }
        }
        Object result;
        Object[] args = point.getArgs();
        try {
            result = point.proceed(args);
        } catch (Exception e) {
            clock.stop();
            throw e;
        }

        // 计时结束
        clock.stop();

        logger.info("请求路径requestUrl {},\n所在的类是：{} ，调用的方法是：{}",requestUrl,className,method);
        logger.info("请求参数：{}"+ JSONUtil.objectToJson(parameterMap));

        return result;
    }
}
