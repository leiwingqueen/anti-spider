package com.elend.log.schedule;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.elend.p2p.resource.BaseResourceDB;
import com.elend.log.constant.CommonConstant;

/**
 * 读取这个war包的url并保存到数据库
 * 
 * @author linshumao
 */
public class ResourceDB extends BaseResourceDB {

    @Override
    public int getAppid() {
        return CommonConstant.app_id;
    }

    public static void main(String[] args) throws SQLException {

        System.setProperty("service.domain", "euc.gzdai.com");
        System.setProperty("service.domain.loadOnce", "true");

        ApplicationContext context = new ClassPathXmlApplicationContext(
                                                                        "classpath*:config/spring/*.xml","classpath*:applicationContext-mvc.xml");
        com.elend.p2p.spring.SpringContextUtil.setContext(context);
        DataSource  dataSource = (DataSource)context.getBean("eucDataSource");
        new ResourceDB().run(dataSource.getConnection());
        
        System.exit(0);
    }
}