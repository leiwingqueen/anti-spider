package com.elend.log.constant;

import com.elend.log.cfg.CommConfig;

/**
 * 常量定义
 * @author liuxianyan
 *
 */
public class CommonConstant {
	
	/**分页每页显示记录数*/
	public static final int PAGE_SIZE=CommConfig.getInt("page_size", 15);
	
	/**对于分页，每页最多允许1000条数据*/
	public static final int PAGE_MAX_ROWS = CommConfig.getInt("page_max_size", 1000);
	
	/** 是否使用本地 cache(EHCache) */
	public static final boolean LOCAL_CACHE = CommConfig.getBool("local_cache");
	
//	/**系统id**/
	public static int app_id = CommConfig.getInt("app_id", 16);
//	/**系统标识**/
	public static String app_key = CommConfig.get("app_key");
		
	public static final String app_name = "reconciliation";
}
