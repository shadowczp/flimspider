package com.example.spider.service;

import com.example.spider.mapper.TitleMapper;
import com.example.spider.pojo.DetailItem;
import com.example.spider.pojo.TitleItem;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
@Transactional
public class MainService {
    @Resource
    private TitleMapper mapper;

    public TitleItem queryById(String id){
        TitleItem item = mapper.queryById(id);
        return item;
    }
    public void insertTitle(TitleItem item){
        mapper.insertTitle(item);
    }
    public void insertDetail(DetailItem item){
        mapper.insertDetail(item);
    }
}
