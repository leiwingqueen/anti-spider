package com.elend.spider.event;

import java.util.Date;
import java.util.HashMap;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:config/spring/*.xml" })
public class MinuteEventTest {
    @Autowired
    private EventSubscribeConfig config;
    @Test
    public void test() {
        String ip="127.0.0.1";
        long accessNum=500;
        long urlNum=200;
        long userNum=10;
        MinuteEvent event=new MinuteEvent(config, ip, accessNum, urlNum, userNum,new HashMap<String,Long>(),new Date());
    }
}
