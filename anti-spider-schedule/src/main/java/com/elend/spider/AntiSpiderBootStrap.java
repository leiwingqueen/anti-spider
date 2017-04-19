package com.elend.spider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.elend.p2p.spring.SpringContextUtil;

/**
 * 反爬虫启动类
 * @author liyongquan 2016年8月11日
 *
 */
public class AntiSpiderBootStrap {
    private final static Logger logger = LoggerFactory.getLogger(AntiSpiderBootStrap.class);
    /**
     * 每次扫描的日志大小
     * 当前的访问量大概为5000/s,因此这里暂定为5000
     */
    public static final int LOG_SIZE=5000;
    public static void main(String[] args) {
        String[] xmlCfg = new String[] { "classpath*:config/spring/*.xml" };
        ApplicationContext context = new ClassPathXmlApplicationContext(xmlCfg);
        SpringContextUtil.setContext(context);
        AccessLogScanner accessLogScanner=(AccessLogScanner)SpringContextUtil.getBean("accessLogScanner");
        /**
         * 杀进程要求使用kill pid，不能使用kill -9，不然会导致统计数据出错
         */
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                logger.info("进程收到退出信号...now exit");
                ThreadSetting.setServerStop(true);
                logger.info("等待5s...等待上一个循环的数据处理完");
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    logger.error(e.getMessage());
                }
            }
        });
        while (!ThreadSetting.STOP_SERVER) {
            int size=0;
            try {
                size = accessLogScanner.scan(LOG_SIZE);
            } catch (Exception e1) {
                logger.error("accessLogScanner处理异常...",e1);
            }
            if(size<=0){//当前的数据已经处理完,处理速度大于日志生成速度,sleep 1s
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    logger.error(e.getMessage());
                }
            }
            //处理完一批数据后，sleep 200ms
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                logger.error(e.getMessage());
            }
        }
        logger.info("AntiSpiderBootStrap安全退出...");
    }
}
