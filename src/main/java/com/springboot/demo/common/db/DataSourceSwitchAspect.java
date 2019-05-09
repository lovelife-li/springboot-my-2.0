package com.springboot.demo.common.db;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Component
@Aspect
@Order(-100) //这是为了保证AOP在事务注解之前生效,Order的值越小,优先级越高
@Slf4j
public class DataSourceSwitchAspect {

    /**
     * 定义切点
     */
    @Pointcut("@annotation(com.springboot.demo.common.db.DataSource)")
    public  void dataSourcePointCut(){

    }

    /**
     * 当使用该 @DataSource 注解时，执行前置方法，进行数据源切换
     * @param point
     */
    @Before("dataSourcePointCut()")
    public void beforeSwitchDS(JoinPoint point) {
        //获得当前访问的class
        Class<?> className = point.getTarget().getClass();
        //获得访问的方法名
        String methodName = point.getSignature().getName();
        //得到方法的参数的类型
        Class[] argClass = ((MethodSignature) point.getSignature()).getParameterTypes();
        String dataSource = DBTypeEnum.db01.getValue();
        try {
            // 得到访问的方法对象
            Method method = className.getMethod(methodName, argClass);
            // 判断是否存在@DS注解
            if (method.isAnnotationPresent(DataSource.class)) {
                DataSource annotation = method.getAnnotation(DataSource.class);
                // 取出注解中的数据源名
                dataSource = annotation.value();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 切换数据源
        DbContextHolder.setDbType(DBTypeEnum.getEnumByCode(dataSource));

    }

    /**
     * 后置处理 改为原来数据源
     * @param point
     */
    @After("dataSourcePointCut()")
    public void afterSwitchDS(JoinPoint point){
        DbContextHolder.setDbType(DBTypeEnum.db01);
    }


}
