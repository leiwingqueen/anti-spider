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
public class MinuteDataHandlerTest {
    @Autowired
    @Qualifier("minuteDataHandler")
    private DataHandler handler;
    @Test
    public void testProcess() {
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
}
