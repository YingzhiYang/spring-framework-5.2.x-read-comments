package com.demo.app;

import com.demo.beanPostProcessor.MyImportSelect;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan("com.demo")
//@Import(MyImportSelect.class)
public class AppConfig {
}
