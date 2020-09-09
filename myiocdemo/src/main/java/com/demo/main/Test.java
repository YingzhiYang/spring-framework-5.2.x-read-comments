package com.demo.main;

import com.demo.app.AppConfig;
import com.demo.beanPostProcessor.MyBeanFactoryProcessor;
import com.demo.dao.ImportTestDao;
import com.demo.dao.IndexDao;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Test {
	public static void main(String[] args) {
		//把Spring所有的提前环境准备,初始化Spring的环境
		AnnotationConfigApplicationContext anno= new AnnotationConfigApplicationContext(AppConfig.class);

		//自己添加一个BeanFactoryProcessor
		// anno.addBeanFactoryPostProcessor(new MyBeanFactoryProcessor());
		/*anno.refresh();
		IndexDao dao=anno.getBean(IndexDao.class);
		dao.query();*/
		ImportTestDao importTestDao= (ImportTestDao) anno.getBean("importTestDao");
		importTestDao.query();

	}
}
