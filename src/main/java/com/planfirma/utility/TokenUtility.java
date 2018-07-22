package com.planfirma.utility;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.planfirma.dto.TokenVO;
import com.planfirma.dto.UserDto;

import redis.clients.jedis.Jedis;



@Component
@Lazy(true)
public class TokenUtility {

	
	public Map<String, String> clientMap = new HashMap<String, String>();
	
	
	
	private static TokenUtility utility;	
	public static TokenUtility getInstance() {
		if (utility == null)
			utility = new TokenUtility();

		return utility;
	}
	
	
	public static String generateUsertoken(UserDto userdto)
	{
		String token=userdto.getUsername()+":"+userdto.getId()+":"+new Date().getTime();
		return new String(org.apache.commons.codec.binary.Base64.encodeBase64(token.getBytes()));
	}
	public static String [] fetchUsertokenParts(String usertoken)
	{
		byte[] bytes = org.apache.commons.codec.binary.Base64.decodeBase64(usertoken.getBytes());
		String token = new String(bytes);
		String[] parts = token.split(":");
		return parts;
	}
	
	public void addJedisToken(Jedis jedis, TokenVO subToken) {
		Map<String, String> userProperties = new HashMap<String, String>();
		userProperties.put("userToken", subToken.getUserToken());
		userProperties.put("activeTime", subToken.getActiveTime());
		if (null != subToken.getCustomParams()) {
			Map<String, String> map1 = subToken.getCustomParams();
			for (Map.Entry<String, String> entry : map1.entrySet()) {
				userProperties.put(entry.getKey(), entry.getValue());
			}
		}		
		jedis.hmset(fetchUserKeyForRedis(subToken.getUserToken()), userProperties);
	}
	
	public TokenVO getRedisToken(String usertoken) {
		TokenVO tokenVO = null;
		Jedis jedis = null;
		try {
			jedis = JedisFactory.getInstance().getJedisPool().getResource();
			tokenVO = getRedisToken(usertoken, jedis);
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			if (null != jedis)
				JedisFactory.getInstance().getJedisPool().returnResource(jedis);
		}
		return tokenVO;
	}

	private TokenVO getRedisToken(String usertoken, Jedis jedis) {
		Map<String, String> customParams = new HashMap<String, String>();
		Map<String, String> properties = jedis.hgetAll(fetchUserKeyForRedis(usertoken));
		TokenVO subToken = null;
		if (null != properties.get("userToken")
				&& null != properties.get("activeTime")) {
			subToken = new TokenVO();

			subToken.setUserToken(properties.get("userToken"));
			subToken.setActiveTime(properties.get("activeTime"));

			for (Map.Entry<String, String> entry : properties.entrySet()) {

				if (!entry.getKey().equals("userToken") && !entry.getKey().equals("activeTime"))
					customParams.put(entry.getKey(), entry.getValue());

			}

			if (!customParams.isEmpty())
				subToken.setCustomParams(customParams);
		}
		return subToken;
	}

	private void removeJedisToken(String redisKey, Jedis jedis) {
		try {
			jedis.del(redisKey);		
		}catch(Exception e) {
			System.out.println("error removing user : " + redisKey);
		}
	}

	
	public void makeRedisToken(String userToken,Long userID,Map<String,String>... map)
	{
		
		System.out.println("abcd");
		makeRedisToken(userToken);		
	}
	
	
	
	public void makeRedisToken(String userToken){
		Jedis jedis = null;
		try {
			jedis = JedisFactory.getInstance().getJedisPool().getResource();
			
			if (null == getRedisToken(userToken, jedis)) {
				TokenVO subToken = Utility.createToken(userToken);
				addJedisToken(jedis, subToken);
			} else {
				removeJedisToken(fetchUserKeyForRedis(userToken), jedis);
				TokenVO subToken = Utility.createToken(userToken);
				addJedisToken(jedis, subToken);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != jedis)
				JedisFactory.getInstance().getJedisPool().returnResource(jedis);
		}
	}

	public void updateRedisToken(String userToken) {
		Jedis jedis = null;
		try {
			jedis = JedisFactory.getInstance().getJedisPool().getResource();
			if (null != getRedisToken(userToken, jedis)){
				TokenVO subToken = getRedisToken(userToken, jedis);
				subToken.setActiveTime(new Timestamp(new Date().getTime()).toString());
				addJedisToken(jedis, subToken);				
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != jedis)
				JedisFactory.getInstance().getJedisPool().returnResource(jedis);
		}
	}
	
	public void deleteRedisToken(String userToken) {

		Jedis jedis = null;
		try {
			jedis = JedisFactory.getInstance().getJedisPool().getResource();
			if (null != getRedisToken(userToken, jedis))
				removeJedisToken(fetchUserKeyForRedis(userToken), jedis);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != jedis)
				JedisFactory.getInstance().getJedisPool().returnResource(jedis);
		}
	}
	
	public void removeUserFromRedis(Long userID) {

		Jedis jedis = null;
		try {
			jedis = JedisFactory.getInstance().getJedisPool().getResource();
			String redisKey = userID.longValue()+"";
			removeJedisToken(redisKey, jedis);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != jedis)
				JedisFactory.getInstance().getJedisPool().returnResource(jedis);
		}
	}
	

	public boolean validateRedisUsertoken(String usertoken) {
		Jedis jedis = null;		
	
		try {
			jedis = JedisFactory.getInstance().getJedisPool().getResource();
			boolean newToken = true;
			String[] parts = TokenUtility.fetchUsertokenParts(usertoken);
	
			double	sessionTimeOut=1;
			TokenVO tokenVO = getRedisToken(usertoken, jedis);
			System.out.println("usertoken::::::::"+usertoken);
			if(null!=tokenVO)
				System.out.println("Redis usertoken::::::::"+tokenVO.getUserToken());
			else
				System.out.println("Redis usertoken::::::::usertoken not in redis");
			if(parts.length>=2){
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date d = new Date();
				Date d1 = null;
				if (null == tokenVO) {
					newToken = false;
					return newToken;
				}
				if (!tokenVO.getUserToken().trim().equals(usertoken.trim())) {
					newToken = false;
					return newToken;
				}
				if (null != tokenVO) {
					try {
						d1 = format.parse(tokenVO.getActiveTime());					
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}
				if (null != d1) {
					long diff = d.getTime() - d1.getTime();
					double hrs=(60 * 60 * 1000);
					double diffHours = (diff / hrs);
					if (diffHours > sessionTimeOut) {
						newToken = false;
						deleteRedisToken(usertoken);
						System.out.println("Redis usertoken::::::::usertoken expired" + diffHours+":"+sessionTimeOut);
					}
				}
			}
			else{
				return false;
			}
			return newToken;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			if (null != jedis)
				JedisFactory.getInstance().getJedisPool().returnResource(jedis);
		}
	}

	private String fetchUserKeyForRedis(String token){
		String tokenParts[] =TokenUtility.fetchUsertokenParts(token);
		String userKey = tokenParts[1];
		return userKey;
	}
	
	private long fetchClientIdFromToken(String token){
		String tokenParts[] =TokenUtility.fetchUsertokenParts(token);
		String clientId=tokenParts[5];
		return Long.valueOf(clientId);
	}
	


	
}
