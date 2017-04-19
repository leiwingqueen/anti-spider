package com.elend.spider.data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.elend.log.vo.AccessLogVO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:config/spring/*.xml" })
public class DayDataHandlerTest {
    @Autowired
    @Qualifier("dayDataHandler")
    private DataHandler handler;
    @Test
    public void testProcessListOfAccessLogVO() {
        AccessLogVO log=new AccessLogVO();
        log.setIp("127.0.0.1");
        log.setUserId(100017L);
        log.setUri("/test.do");
        log.setUserAgent("python");
        log.setCreateTime(new Date());
        List<AccessLogVO> list=new ArrayList<AccessLogVO>();
        list.add(log);
        list.add(log);
        handler.process(list);
        handler.process(list);
    }
    
    @Test
    public void test1(){
        String url="/loan/loanHandler/1608121018560956";
        System.out.println(url.replaceAll("[0-9]", ""));
        url="/loan/loanHandler/1608121018560956/sadfadf/123123";
        System.out.println(url.replaceAll("[0-9]", ""));
    }
}
