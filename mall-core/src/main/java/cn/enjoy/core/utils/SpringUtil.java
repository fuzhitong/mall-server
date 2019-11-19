package cn.enjoy.core.utils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.function.Predicate;

@Component
public class SpringUtil implements ApplicationContextAware {

	private static ApplicationContext applicationContext;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		SpringUtil.applicationContext = applicationContext;
	}

	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	/**
	 * 根据Bean名称获取实例
	 * @param name  Bean注册名称
	 * @return bean实例
	 * @throws BeansException
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getBean(String name) throws BeansException {
		return (T) applicationContext.getBean(name);
	}

	public static <T> T getBean(Class<T> requiredType) throws BeansException {
		return applicationContext.getBean(requiredType);
	}

	/**
	 * 判断当前环境是否是生产环境
	 * @return
	 */
	public static boolean isProd(){
		String[] profiles = applicationContext.getEnvironment().getActiveProfiles();
		return (Arrays.stream(profiles).filter(new Predicate<String>() {
			@Override
			public boolean test(String s) {
				return "prod".equals(s);
			}
		}).count() > 0);
	}

}