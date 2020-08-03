package com.yr.redis.spring;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class UserDaoTest {

	private ApplicationContext app;
	private UserDao userDao;

	/**
	 * before
	 */
	@Before
	public void before() {

		app = new ClassPathXmlApplicationContext("spring-redis.xml");
		userDao = (UserDao) app.getBean("userDao");
	}

	/**
	 * 添加
	 */
	@Test
	public void save() {
		// -------------- Create ---------------
		User user = new User("u2", "湖南");
		userDao.save(user);
		System.out.println("添加成功");
	}

	/**
	 * 更新
	 */
	@Test
	public void update() {
		// --------------Update ------------
		User user = new User("u2","深圳");
		userDao.save(user);
		System.out.println("更新成功");

	}

	/**
	 * 查询
	 */
	@Test
	public void read() {
		// ---------------Read ---------------
		String uid = "u2";
		System.out.println(userDao.read(uid));
	}

	/**
	 * 删除
	 */
	@Test
	public void del() {
		// --------------Delete ------------
		String uid = "u2";
		userDao.delete(uid);
		System.out.println("删除成功");
	}
}