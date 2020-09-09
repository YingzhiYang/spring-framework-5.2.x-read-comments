package com.demo.beanPostProcessor;

import com.demo.dao.ImportTestDao;
import com.demo.main.Test;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

import java.lang.reflect.Proxy;


public class MyImportDBR implements ImportBeanDefinitionRegistrar {
	@Override
	public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
		//得到BD，扫描接口，这里写死了，但是其实可以做一个包扫描
		BeanDefinitionBuilder builder=BeanDefinitionBuilder.genericBeanDefinition(ImportTestDao.class);
		GenericBeanDefinition beanDefinition= (GenericBeanDefinition) builder.getBeanDefinition();
		beanDefinition.setBeanClass(MyfactoryBean.class);
		registry.registerBeanDefinition("importTestDao",beanDefinition);


		/*ImportTestDao importTestDao= (ImportTestDao) Proxy.newProxyInstance(Test.class.getClassLoader(),new Class[]{ImportTestDao.class},new MyInvocation());
		BeanDefinitionBuilder builder=BeanDefinitionBuilder.genericBeanDefinition(importTestDao.getClass());
		GenericBeanDefinition beanDefinition= (GenericBeanDefinition) builder.getBeanDefinition();

		registry.registerBeanDefinition("importTestDao",beanDefinition);*/
	}
}
