package com.yr.redis.spring;


public interface UserDao {
	/**
	 * @param user
	 */
	void save(User user);
	/**
	 * @param uid
	 * @return
	 */
	User read(String uid);
	/**
	 * @param uid
	 */
	void delete(String uid);

}