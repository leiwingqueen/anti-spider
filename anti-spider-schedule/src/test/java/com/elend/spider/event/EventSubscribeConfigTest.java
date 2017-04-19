package com.elend.spider.event;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 事件观察者配置测试
 * @author liyongquan 2016年8月8日
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:config/spring/*.xml" })
public class EventSubscribeConfigTest {
    @Autowired
    private EventSubscribeConfig config;
    /**
     * 事件观察者配置
     */
    @Test
    public void testGetConfig() {
        for(String className:config.getConfig().keySet()){
            System.out.println("className:"+className);
            for(Listner listner:config.getConfig().get(className)){
                System.out.println(listner);
            }
            System.out.println("==============");
        }
    }

}
