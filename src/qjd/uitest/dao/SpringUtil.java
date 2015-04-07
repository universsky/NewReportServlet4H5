/**
 * 
 */
package qjd.uitest.dao;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author chenguangjian
 *
 */
public final class SpringUtil {

	private static ApplicationContext ctx = new ClassPathXmlApplicationContext(
			"file:src/qjd/uitest/dao/spring-qjd-uitest.xml");

	// private static ApplicationContext ctxDev = new
	// ClassPathXmlApplicationContext(
	// "/spring/spring-qjd-dev.xml");

	public static Object getBean(String beanName) {
		return ctx.getBean(beanName);
	}

	// public static Object getBeanDev(String beanName) {
	// return ctxDev.getBean(beanName);
	// }

	public static void main(String[] args) {
		System.out.println(getBean("jdbcTemplate"));
		System.out.println(getBean("jdbcTemplate2"));

	}
}
