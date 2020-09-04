package com.demo.beanPostProcessor;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.PriorityOrdered;
import org.springframework.stereotype.Component;

/**
 * 干预bean的实例化过程例子,  PriorityOrdered优先顺序，值越小，优先权越高
 */@Component
public class TestBeanPostProcessor implements BeanPostProcessor, PriorityOrdered {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if(beanName.equals("indexDao")){
            System.out.println("postProcessBeforeInitialization + IndexDao");
        }
        //这里可以直接把对象重新复写成代理对象返回去，这样就干预了IndexDao的初始化，变成了IndexDaoProxy
        //Proxy.newProxyInstance()
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if(beanName.equals("indexDao")){
            System.out.println("postProcessAfterInitialization + IndexDao");
        }
        return bean;
    }

    @Override
    public int getOrder() {
        return 15;
    }
}
