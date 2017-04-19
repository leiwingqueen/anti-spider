package com.elend.log.helper;

import org.mybatis.caches.memcached.MemcachedCache;
import org.springframework.stereotype.Component;

import com.elend.log.constant.CacheKeyConstant;
/**
 * 
 * @author liuxianyan
 *
 */
@Component
public class MemcacheHelper {

	public void firstRequest(String userName) {
		clearCache(userName);
	}

	public void logOut(String userName) {
		clearCache(userName);
	}
	
	public void welcome(String userName) {
		clearCache(userName);
	}
	/**
	 * 用户退出与第一次访问的时候，清理缓存
	 * @param userName
	 */
	private void clearCache(String userName){
		MemcachedCache cache = new MemcachedCache(CacheKeyConstant.MEMCACHED_PRIVILEGE_ID);
		{
			String key = CacheKeyConstant.PRIVILEGE_RESOURCE_KEY_PREFIX + userName;
			cache.removeObject(key);
		}
		{
			String key = CacheKeyConstant.PRIVILEGE_MENU_KEY_PREFIX + userName;
			cache.removeObject(key);			
		}
		{
			String key = CacheKeyConstant.PRIVILEGE_USER_INFO_KEY_PREFIX + userName;
			cache.removeObject(key);			
		}		
	}
}
