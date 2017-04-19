package com.elend.spider.common.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.elend.spider.common.mapper.AntiSpiderBlackListMapper;
import com.elend.spider.common.mapper.AntiSpiderLogMapper;
import com.elend.spider.common.mapper.AntiSpiderWhiteListMapper;
import com.elend.spider.common.service.SpiderService;

@Transactional(readOnly = true)
@Service
public class SpiderServiceImpl implements SpiderService {

    @Autowired
    private AntiSpiderBlackListMapper antiSpiderBlackListMapper;
    
    @Autowired
    private AntiSpiderWhiteListMapper antiSpiderWhiteListMapper;
    
    @Autowired
    private AntiSpiderLogMapper antiSpiderLogMapper;
}
