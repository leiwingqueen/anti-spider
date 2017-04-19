package com.elend.spider.api.util;

import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.elend.p2p.zk.conf.PropertiesHelper;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:config/spring/*.xml", "classpath*:applicationContext-mvc.xml", "classpath*:p2p-servlet.xml" })
public class PropHelperTest {
    
    @Autowired
    private PropHelper helper;
    
    @Autowired
    @Qualifier("antiSpiderZkPropertiesHelper")
    private PropertiesHelper zkPropertiesHelper;

    @Test
    public void test() {
        String[] accessIps = helper.getAccessIps();
        System.out.println(Arrays.toString(accessIps));
    }
    
    @Test
    public void init() {
        int setString = zkPropertiesHelper.setString("access_ips", "127.0.0.1,172.16.30.230,172.16.30.231,172.16.50.44");
        System.out.println(setString);
    }

}
