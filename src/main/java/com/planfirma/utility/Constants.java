package com.planfirma.utility;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.MissingResourceException;
import java.util.Properties;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;


import redis.clients.jedis.Jedis;

public class Constants implements ApplicationContextAware {
	
	private static ApplicationContext ctx;

	public static ApplicationContext getApplicationContext() {
		return ctx;
	}
	public void setApplicationContext(ApplicationContext ctx) throws BeansException {
		this.ctx = ctx;
	}
	
	private static Constants constantUtils;

	public static Constants getInstance() {
		if (constantUtils == null)
			constantUtils = new Constants();
		return constantUtils;
	}
	
	public static void reloadContext(){	
		Jedis jedis;
		jedis=JedisFactory.getInstance().getJedisPool().getResource();
		try {
			jedis.connect();
			Properties bean = (Properties) ctx.getBean("commonsConfigurationFactoryBean");
			if (null != jedis.hgetAll("config"))
				jedis.del("config");
			HashMap<String, String> map=getAllKeys(bean);
			jedis.hmset("config", map);				
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			if(null!=jedis)
				JedisFactory.getInstance().getJedisPool().returnResource(jedis);
		}
	}

	
	public String getStringFromContext(String key){
		Properties bean = (Properties) ctx.getBean("commonsConfigurationFactoryBean");
		
		return bean.getProperty(key);
	}

	public static  String getString(String key) {
		Jedis jedis;
		jedis=JedisFactory.getInstance().getJedisPool().getResource();
		try {			
			jedis.connect();
			if (null == jedis.hgetAll("config") || jedis.hgetAll("config").isEmpty())
			{
				ApplicationContext ctx = Constants.getApplicationContext();
				Properties bean = (Properties) ctx.getBean("commonsConfigurationFactoryBean");
				HashMap<String, String> map=getAllKeys(bean);
				//jedis.
				jedis.hmset("config",map);				
				return bean.getProperty(key);
			}
			else{
				return jedis.hgetAll("config").get(key.trim());
			}


		} catch (MissingResourceException e) {
			return '!' + key + '!';
		}
		finally{
			if(null!=jedis)
				JedisFactory.getInstance().getJedisPool().returnResource(jedis);
		}
	}	
	public static HashMap<String, String> getAllKeys(Properties prop){
		HashMap<String, String> map = new HashMap<String, String>();
		for(Entry<Object, Object> x : prop.entrySet()) {
			System.out.println(x.getKey() + " " + x.getValue());
			map.put(x.getKey().toString().trim(), x.getValue().toString().trim());
		}
		return map;
	}

}
