package com.example.spider.pojo;

public class TitleItem {
    //post-164505
    private String id;
    //http://www.dy8c.com/entertainment/164505/
    private String detailUrl;
    //更新时间：2018-02-22
    private String updateTime;
    //寻梦环游记 (2017)
    private String title;
    //[BD720P/1080P]
    private String note;
    //http://wx2.sinaimg.cn/large/7768b183gy1flufpywuecj20m80vsdwi.jpg
    private String imgUrl;

    public String getDetailUrl() {
        return detailUrl;
    }

    public void setDetailUrl(String detailUrl) {
        this.detailUrl = detailUrl;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
