/*
 * Copyright 2002-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.beans.factory.config;

import org.springframework.beans.BeansException;
import org.springframework.lang.Nullable;

/**
 * Factory hook that allows for custom modification of new bean instances &mdash;
 * for example, checking for marker interfaces or wrapping beans with proxies.
 *
 * <p>Typically, post-processors that populate beans via marker interfaces
 * or the like will implement {@link #postProcessBeforeInitialization},
 * while post-processors that wrap beans with proxies will normally
 * implement {@link #postProcessAfterInitialization}.
 *
 * <h3>Registration</h3>
 * <p>An {@code ApplicationContext} can autodetect {@code BeanPostProcessor} beans
 * in its bean definitions and apply those post-processors to any beans subsequently
 * created. A plain {@code BeanFactory} allows for programmatic registration of
 * post-processors, applying them to all beans created through the bean factory.
 *
 * <h3>Ordering</h3>
 * <p>{@code BeanPostProcessor} beans that are autodetected in an
 * {@code ApplicationContext} will be ordered according to
 * {@link org.springframework.core.PriorityOrdered} and
 * {@link org.springframework.core.Ordered} semantics. In contrast,
 * {@code BeanPostProcessor} beans that are registered programmatically with a
 * {@code BeanFactory} will be applied in the order of registration; any ordering
 * semantics expressed through implementing the
 * {@code PriorityOrdered} or {@code Ordered} interface will be ignored for
 * programmatically registered post-processors. Furthermore, the
 * {@link org.springframework.core.annotation.Order @Order} annotation is not
 * taken into account for {@code BeanPostProcessor} beans.
 *
 * @author Juergen Hoeller
 * @author Sam Brannen
 * @since 10.10.2003
 * @see InstantiationAwareBeanPostProcessor
 * @see DestructionAwareBeanPostProcessor
 * @see ConfigurableBeanFactory#addBeanPostProcessor
 * @see BeanFactoryPostProcessor
 *
 * BeanPostProcessor是Spring框架提供的一个扩展类点，叫做bean后置器(其中一个一共5个，下面列举的类都属于这一个类别的扩展点)
 * 通过实现BeanPostProcessor接口，程序员就可以干预bean实例化的过程，从而减轻beanFactory的负担
 * 这个接口可以设置多个，形成一个列表，然后依次执行。！！注意！！凡是实现的全部都会执行一遍
 * （但是Spring默认的BeanPostProcessor怎么办？Spring自己会set）
 * 比如AOP就是在Bean实例化期间，将切面逻辑植入Bean治理中的
 * 也正是通过BeanPostProcessor，Spring把AOP，动态代理，IOC容器建立起了联系
 * 由Spring提供的默认的PostProcessor有很多，比如
 * 1. ApplicationContextAwareProcessor (简称ACAP)
 *		ACAP后置处理器的作用是：当应用程序定义的Bean实现ApplicationContextAwareProcessor接口时注入ApplicationContext对象
 * 2. InitDestroyAnnotationBeanPostProcessor
 * 		用来处理自定义的初始化方法和销毁方法。
 * 		我们知道Spring有3中自定义初始化和销毁的方法：
 * 		a.通过@Bean指定init-method和destroy-method属性
 * 		b.通过实现InitializingBean接口和实现DisposiableBean接口
 * 		c.通过加上@PostConstract和@PreDestroy注解
 * 		之所以这三种方法都可以实现回调，就是因为	InitDestroyAnnotationBeanPostProcessor做了处理
 * 	3. InstantiationAwareBeanPostProcessor
 * 	4. CommonAnnotationBeanPostProcessor
 * 	5. AutowiredAnnotationBeanPostProcessor
 * 	6. RequireAnnotationBeanPostProcessor
 * 	7. BeanValidationPostProcessor
 * 	8. AbstractAutoProxyCreator
 * 	等等
 *
 *
 * 	第二个扩展点BeanFactoryPostProcessor，bean工厂后置器
 */
public interface BeanPostProcessor {

	/**
	 * Apply this {@code BeanPostProcessor} to the given new bean instance <i>before</i> any bean
	 * initialization callbacks (like InitializingBean's {@code afterPropertiesSet}
	 * or a custom init-method). The bean will already be populated with property values.
	 * The returned bean instance may be a wrapper around the original.
	 * <p>The default implementation returns the given {@code bean} as-is.
	 * @param bean the new bean instance 这个参数bean就是原始对象
	 * @param beanName the name of the bean
	 * @return the bean instance to use, either the original or a wrapped one;
	 * if {@code null}, no subsequent BeanPostProcessors will be invoked
	 * @throws org.springframework.beans.BeansException in case of errors
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet
	 *
	 * BeforeInitialization顾名思义：在bean的初始化之前执行
	 * 这个参数bean就是原始对象
	 *
	 */
	@Nullable
	default Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		return bean;
	}

	/**
	 * Apply this {@code BeanPostProcessor} to the given new bean instance <i>after</i> any bean
	 * initialization callbacks (like InitializingBean's {@code afterPropertiesSet}
	 * or a custom init-method). The bean will already be populated with property values.
	 * The returned bean instance may be a wrapper around the original.
	 * <p>In case of a FactoryBean, this callback will be invoked for both the FactoryBean
	 * instance and the objects created by the FactoryBean (as of Spring 2.0). The
	 * post-processor can decide whether to apply to either the FactoryBean or created
	 * objects or both through corresponding {@code bean instanceof FactoryBean} checks.
	 * <p>This callback will also be invoked after a short-circuiting triggered by a
	 * {@link InstantiationAwareBeanPostProcessor#postProcessBeforeInstantiation} method,
	 * in contrast to all other {@code BeanPostProcessor} callbacks.
	 * <p>The default implementation returns the given {@code bean} as-is.
	 * @param bean the new bean instance       这个参数bean就是原始对象
	 * @param beanName the name of the bean
	 * @return the bean instance to use, either the original or a wrapped one;
	 * if {@code null}, no subsequent BeanPostProcessors will be invoked
	 * @throws org.springframework.beans.BeansException in case of errors
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet
	 * @see org.springframework.beans.factory.FactoryBean
	 *
	 * AfterInitialization顾名思义：在bean的初始化之后执行。
	 * 这个参数bean就是原始对象
	 */
	@Nullable
	default Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		return bean;
	}

}
