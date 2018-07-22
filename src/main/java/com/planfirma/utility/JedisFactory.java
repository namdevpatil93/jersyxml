package com.planfirma.utility;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class JedisFactory {

	private static JedisPool jedisPool;
	private static JedisFactory instance;
	private JedisFactory() {
		JedisPoolConfig poolConfig = new JedisPoolConfig();  
		poolConfig.setMaxTotal(Integer.parseInt(Constants.getInstance().getStringFromContext("jedisMaxActive"))); //128
		jedisPool = new JedisPool(poolConfig,Constants.getInstance().getStringFromContext("jedisPath"));
	}

	public JedisPool getJedisPool() {
		return jedisPool;
	}

	public static JedisFactory getInstance() {
		if (instance == null) {
			instance = new JedisFactory();
		}
		return instance;
	}
}
