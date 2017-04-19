package com.elend.spider.common.facade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.elend.spider.common.service.SpiderService;

@Component
public class SpiderFacde {
    @Autowired
    private SpiderService spiderService;
}
