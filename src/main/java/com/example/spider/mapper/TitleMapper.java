package com.example.spider.mapper;

import com.example.spider.pojo.DetailItem;
import com.example.spider.pojo.TitleItem;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TitleMapper {
    TitleItem queryById(String id);
    void insertTitle(TitleItem item);
    void insertDetail(DetailItem item);
}
