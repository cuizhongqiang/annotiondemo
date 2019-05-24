package com.dn.util;

import com.dn.annotation.NeedSetValue;
import com.github.pagehelper.StringUtil;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Component
public class BeanUtil implements ApplicationContextAware {
    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if(this.applicationContext==null)
            this.applicationContext = applicationContext;
    }

    public void setFieldValueForCollection(Collection col) throws Exception {
        //1 :获取到结果集的Class对象
        Class<?> clazz = col.iterator().next().getClass();

        //2:获取到我们class对象中的属性，为了优化性能，我们定义一个本地缓存
        Field[] fields = clazz.getDeclaredFields();


        Map<String,Object> cache  = new HashMap<>();
        //3遍历我们属性集合中被这个注解指定到了的字段，再去获取这个注解

        for(Field needField :fields){
            NeedSetValue sv  = needField.getAnnotation(NeedSetValue.class);
            if(sv==null)
                continue;
            //一旦我们找到了这个field,就让它的属性可见
            needField.setAccessible(true);

            //接下来我们就通过我们的这个注解对象，来获取，我们需要的工具
            /***
             * 哪些属性需要设置？
             * 该调用哪个bean的哪个方法？
             * 该传入哪个属性的值作为参数？
             * 该获取得到的对象上的哪个属性的值
             * */
            //1:我们去获得我们需要的bean
            Object bean  = this.applicationContext.getBean(sv.beanClass());

            //2:我们要调用的方法
            Method method = sv.beanClass().getMethod(sv.method(),clazz.getDeclaredField(sv.param()).getType());

            //3:获取入参字段
            Field paramField = clazz.getDeclaredField(sv.param());
            paramField.setAccessible(true);

            //4:值的来源
            Field targetFiled  = null;
            Boolean needInnerField = !StringUtil.isEmpty(sv.targetFiled());

            //把缓存的key做出来
            String keyPrefix = sv.beanClass()+"-"+sv.method()+"-"+sv.targetFiled()+"-";

           //4遍历我们的结果集，开始操作，把我们结果集的指定的值写入到我们的属性中
            for(Object obj :col){
                //获取结果集对象属性值
                Object paramValue = paramField.get(obj);
                if(paramValue==null)
                    continue;

                Object value = null;
                //先从缓存中拿
                String key = keyPrefix+paramValue;
                 if(cache.containsKey(key)){
                     value = cache.get(key);
                 } else{
                     value = method.invoke(bean,paramValue);

                     if (needInnerField){
                         if(value!=null){
                             if (targetFiled==null){
                                 targetFiled = value.getClass().getDeclaredField(sv.targetFiled());
                                 targetFiled.setAccessible(true);
                             }
                             value = targetFiled.get(value);
                         }
                     }
                     cache.put(key,value);
                 }
                 needField.set(obj,value);

            }



        }

    }


}
