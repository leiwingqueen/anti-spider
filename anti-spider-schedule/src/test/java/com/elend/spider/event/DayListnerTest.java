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
public class DayListnerTest {

    @Autowired
    private DayListner listner;

    @Test
    public void test() {
        DayEvent event = new DayEvent();
        event.setAccessNum(1000);
        event.setIp("127.0.0.2");
        event.setUrlNum(50);
        Map<String, Long> userAgentMap = new HashMap<String, Long>();
        userAgentMap.put("sogou", 30L);
        event.setUserAgentMap(userAgentMap);
        event.setUserNum(2);

        Result<String> execute = listner.execute(event);
        System.out.println("===================================" + execute);
    }
}
