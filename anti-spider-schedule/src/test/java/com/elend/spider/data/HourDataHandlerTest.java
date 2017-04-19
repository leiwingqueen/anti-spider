package com.elend.spider.data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
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
public class HourDataHandlerTest {
    @Autowired
    @Qualifier("hourDataHandler")
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
    public void testCalAccessNumVarience(){
    	/**
    	 * logId:93932153
    	 * grep 9f6d2e2f30eb4cd98acb655c8b3b7b7f /data/elend/log/anti/anti-spider-schedule.log(8-15号的日志) 
    	 * select count(*),minute(create_time) from access_log where create_time between '2016-08-15 04:00:01' and '2016-08-15 04:59:59' and ip='218.17.157.50' group by minute(create_time);
    	 */
    	Long[] list=new Long[]{18L,14L,15L,24L,31L,19L,8L,31L,32L,19L,11L,13L,34L,30L,21L,11L,28L,18L,10L,9L,12L,32L,8L,1L,11L,3L,12L,9L,7L,6L,15L,1L,6L,1L,5L,1L,2L,4L,3L,4L,27L,10L,14L,35L,15L,11L,36L,32L,49L};
    	List<Long> accessNumList=new ArrayList<Long>(60);
    	for(long l:list){
    	    accessNumList.add(l);
    	}
    	System.out.println("len:"+accessNumList.size());
        //accessNumList.add(5L);
        BigDecimal varience=HourDataHandler.calAccessNumVarience(accessNumList);
        System.out.println("varience:"+varience);
    }
}
