package com.elend.spider.init;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.elend.p2p.spring.SpringContextUtil;
import com.elend.p2p.util.redis.RedisDao;
import com.elend.spider.AccessLogScanner;

/**
 * 初始化lastLogId，避免扫描太老的数据
 * @author liyongquan 2016年8月12日
 *
 */
public class LastLogIdInit {
    public static void main(String[] args) {
        if(args==null||args.length<=0){
            System.out.println("请输入初始化last_log_id");
            return;
        }
        String[] xmlCfg = new String[] { "classpath*:config/spring/*.xml" };
        ApplicationContext context = new ClassPathXmlApplicationContext(xmlCfg);
        SpringContextUtil.setContext(context);
        RedisDao redisDao=(RedisDao)SpringContextUtil.getBean("redisManager");
        redisDao.set(AccessLogScanner.LAST_LOG_ID_KEY, args[0]);
        System.out.println("更新last_log_id为:"+args[0]);
    }
}
