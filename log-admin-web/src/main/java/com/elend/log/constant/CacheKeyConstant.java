package com.elend.log.constant;
/**
 * 定义缓存key
 * @author liuxianyan
 *
 */
public class CacheKeyConstant {
	
	/**业务memcached id的所有前缀*/
	public static final String _MEMCACHED_KEYPREFIX="_log-admin-web_";
	
	
	/*************权限资源 memcached key*******************/
	
	/**权限域id的*/
	public static final String MEMCACHED_PRIVILEGE_ID = _MEMCACHED_KEYPREFIX+"PRIVILEGE";
	
	/**菜单key*/
	public static final String PRIVILEGE_MENU_KEY_PREFIX = "menu_";
	
	/**资源key*/
	public static final String PRIVILEGE_RESOURCE_KEY_PREFIX = "resource_";
	
	/**用户信息*/
	public static final String PRIVILEGE_USER_INFO_KEY_PREFIX = "user_info_";
	
	/*****************权限资源 memcached key end************/
	
	
	
}
