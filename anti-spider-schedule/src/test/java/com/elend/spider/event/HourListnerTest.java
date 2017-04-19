package com.elend.spider.event;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.elend.p2p.Result;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:config/spring/*.xml" })
public class HourListnerTest {
    
    @Autowired
    private HourListner listner;

    @Test
    public void test() {
        HourEvent event = new HourEvent();
        event.setAccessNum(199);
        event.setIp("127.0.0.2");
        event.setUrlNum(50);
        Map<String, Long> userAgentMap = new HashMap<String, Long>();
        userAgentMap.put("Python-urllib", 5L);
        event.setUserAgentMap(userAgentMap );
        event.setUserNum(6);
        //event.setAccessNumVariance(new BigDecimal("0.01"));
        Result<String> execute = listner.execute(event);
        System.out.println("===================================" + execute);
    }

}
