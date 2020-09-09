package com.demo.beanPostProcessor;

import com.demo.dao.ImportTestDao;
import org.springframework.beans.factory.FactoryBean;

import java.lang.reflect.Proxy;

public class MyfactoryBean implements FactoryBean {
	@Override
	public Object getObject() throws Exception {
		Class[] clazzes=new Class[]{ImportTestDao.class};
		Object proxy= Proxy.newProxyInstance(this.getClass().getClassLoader(),clazzes,new MyInvocation());
		return proxy;
	}

	@Override
	public Class<?> getObjectType() {
		return ImportTestDao.class;
	}

	@Override
	public boolean isSingleton() {
		return false;
	}
}
