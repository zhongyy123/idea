package com.yr.redis.spring;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;


public class ClusterTest {
 
	@Autowired
	private ApplicationContext app;
	private RedisTemplate<String,Object> redisTemplate;
	private ValueOperations value;


	/**
	 * before
	 */
	@Before
	public void before() {

		 app= new ClassPathXmlApplicationContext("classpath:spring-cluster.xml");
		 redisTemplate=(RedisTemplate<String,Object>) app.getBean("redisTemplate");
		 value=redisTemplate.opsForValue();

	}

	/**
	 * 添加
	 */
	@Test
	public void testCreate() {

		redisTemplate.opsForValue().set("id","111");
		System.out.println("添加成功");
	}


	/**
	 * 查询
	 */
	@Test
	public void testRead() {

		System.out.println("Redis中键为id对应的值为：" + value.get("id"));
	}

	/**
	 * 查询所有key
	 */
	@Test
	public void  testkeys(){

		System.out.println("所有的key是： "+redisTemplate.keys("*"));
	}

	/**
	 * 更新
	 */
	@Test
	public void testUpdate() {

		System.out.println("原始值：" + value.get("id"));
		value.set("id", "diaomao");
		System.out.println("更新值：" + value.get("id"));
	}

	/**
	 * 删除
	 */
	@Test
	public void testDelete() {
		System.out.println("Redis中的值：" + value.get("id"));
        redisTemplate.delete("id");
		System.out.println("删除后：" + value.get("id"));
	}
}