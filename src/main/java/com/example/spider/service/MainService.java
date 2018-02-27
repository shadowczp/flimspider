package com.example.spider.service;

import com.example.spider.mapper.TitleMapper;
import com.example.spider.pojo.TitleItem;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class MainService {
    @Resource
    private TitleMapper mapper;

    public TitleItem queryById(String id){
        TitleItem item = mapper.queryById(id);
        return item;
    }
}
