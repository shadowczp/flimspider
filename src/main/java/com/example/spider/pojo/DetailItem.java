package com.example.spider.pojo;

public class DetailItem {
    //post-164505
    private String id;
    //寻梦环游记720P中英双字
    private String urlInfo;
    //下载链接
    private String url;
    //内容大小（【1.9G】）
    private String contentSize;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrlInfo() {
        return urlInfo;
    }

    public void setUrlInfo(String urlInfo) {
        this.urlInfo = urlInfo;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getContentSize() {
        return contentSize;
    }

    public void setContentSize(String contentSize) {
        this.contentSize = contentSize;
    }
}
