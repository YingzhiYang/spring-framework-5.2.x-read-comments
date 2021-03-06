package com.demo.app;

import com.demo.beanPostProcessor.MyImportDBR;
import com.demo.beanPostProcessor.MyImportSelect;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan("com.demo")
//@Import(MyImportSelect.class)
@Import(MyImportDBR.class)
@EnableAspectJAutoProxy
public class AppConfig {
}
