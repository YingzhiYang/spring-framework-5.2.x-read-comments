package com.demo.beanPostProcessor;

import com.demo.dao.IndexDao;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;


public class MyImportSelect implements ImportSelector {
    /**
     * 这个方法的返回值，是一个字符串数组，只要在配置类被引用了
     * 这里返回的字符串数组中的类名就会被Spring容器new出来，然后再把这些对象放到工厂当中去
     * @param importingClassMetadata
     * @return
     */
    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        return new String[]{IndexDao.class.getName()};
    }
}
