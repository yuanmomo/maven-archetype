/** 
 * Project Name : Tools
 * Package Name : net.yuanmomo.tools.util.bean
 * Created on   : 2014-4-4上午1:11:32
 * File Name    : BeanUtil.java
 *
 * Author       : Hongbin Yuan
 * Blog         : yuanmomo.net
 * Company      : 成都逗溜网科技有限公司  
 */

package net.yuanmomo.util;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * ClassName : BeanUtil Function : TODO ADD FUNCTION. Reason : TODO ADD REASON.
 * Date : 2014-4-4 上午1:11:32
 * 
 * @author : Hongbin Yuan
 * @version
 * @since JDK 1.7
 * @see
 */
public class BeanUtil implements ApplicationContextAware {

	// Spring应用上下文环境
	private static ApplicationContext applicationContext;

	/**
	 * 实现ApplicationContextAware接口的回调方法，设置上下文环境
	 * 
	 * @param applicationContext
	 */
	public void setApplicationContext(ApplicationContext applicationContext) {
		BeanUtil.applicationContext = applicationContext;
	}

	/**
	 * @return ApplicationContext
	 */
	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	/**
	 * 获取对象 这里重写了bean方法，起主要作用
	 * 
	 * @param name
	 * @return Object 一个以所给名字注册的bean的实例
	 * @throws BeansException
	 */
	public static Object getBean(String name) throws BeansException {
		return applicationContext.getBean(name);
	}

	/**
	 * 获取对象 这里重写了bean方法，起主要作用
	 * 
	 * @param clazz
	 * @return Object 一个以所给名字注册的bean的实例
	 * @throws BeansException
	 */
	public static <T> T getBean(Class<T> clazz) throws BeansException {
		return applicationContext.getBean(clazz);
	}
	
	public static <T> void register(Class<T> clazz, Object bean) {
		DefaultListableBeanFactory fty = (DefaultListableBeanFactory) applicationContext
				.getAutowireCapableBeanFactory();
		fty.registerResolvableDependency(clazz, bean);
	}
}
