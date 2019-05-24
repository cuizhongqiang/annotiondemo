package com.dn.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)//定义它在类的属性上生效
@Retention(RetentionPolicy.RUNTIME)//定义它在JVM运行时生效
public @interface NeedSetValue {

    //我开始定义我们需要的四个属性

    //它要调用的bean
    Class<?> beanClass();

    //它需要传入的参数
    String param();

    //它需要调用的方法
    String method();

    //它需要的结果值
    String targetFiled();

}
