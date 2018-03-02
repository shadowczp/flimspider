package com.example.spider;

import com.example.spider.pojo.DetailItem;
import com.example.spider.pojo.TitleItem;
import com.example.spider.service.MainService;
import com.example.spider.util.WebDriverPool;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;

public class Main {
    @Value("${retryTimes}")
    private int retryTimes;
    @Autowired
    private MainService mainService;
    @Autowired
    private WebDriverPool webDriverPool;
    @Autowired
    private ExecutorService executorService;
    @Resource(name = "urls")
    private LinkedBlockingQueue<String> urls;


    public String getUrl(){
        return urls.poll();
    }

    public void addUrl(String url) throws InterruptedException {
        urls.put(url);
    }

    //标题主页的爬取逻辑
    public boolean titlePage(WebDriver webDriver, String url) {
        webDriver.get(url);
        try {
            //获取标题图片来判断是否加载成功了
            WebElement titleImg = webDriver.findElement(By.xpath("//*[@id=\"header\"]/div/a/img"));
        } catch (NoSuchElementException e) {
            return false;
        }
        try {
            WebElement nextPage = webDriver.findElement(By.xpath("//*[@id=\"content\"]/div/nav/div/a[contains(@class,\"next\")]"));
//            addUrl(nextPage.getAttribute("href"));

        } catch (NoSuchElementException e) {
            e.printStackTrace();
        }
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        try {
            List<WebElement> items = webDriver.findElements(By.xpath("//*[@id=\"content\"]/div/div"));
            for (WebElement webElement : items) {
                try {
                    TitleItem item = new TitleItem();
                    item.setId(webElement.getAttribute("id"));
                    item.setUpdateTime(webElement.findElement(By.xpath("div/span")).getText());
                    item.setTitle(webElement.findElement(By.xpath("h2/a")).getText());
                    item.setDetailUrl(webElement.findElement(By.xpath("h2/a")).getAttribute("href"));
                    item.setImgUrl(webElement.findElement(By.xpath("div/a/img")).getAttribute("src"));
                    item.setNote(webElement.findElement(By.xpath("h2/span")).getText());
                    addUrl(item.getDetailUrl());
                    mainService.insertTitle(item);
                } catch (NoSuchElementException e) {
                    //捕捉内层找不到元素的异常
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } catch (NoSuchElementException e) {
            //捕捉外层找不到元素的异常
            e.printStackTrace();
        }

        return true;
    }

    //详情页的爬取逻辑
    public boolean detailPage(WebDriver webDriver, String url) {

        webDriver.get(url);
        try {
            //获取标题图片来判断是否加载成功了
            WebElement titleImg = webDriver.findElement(By.xpath("//*[@id=\"header\"]/div/a/img"));
        } catch (NoSuchElementException e) {
            return false;
        }
        String id = null;
        try {
            WebElement idElement = webDriver.findElement(By.xpath("//*[@id=\"content\"]/div"));
            id = idElement.getAttribute("id");
        } catch (NoSuchElementException e) {
            return false;
        }

        try {
            List<WebElement> items = webDriver.findElements(By.xpath("//*[starts-with(@class,\"el-s-tr\")]"));
            for (WebElement webElement : items) {
                try {
                    DetailItem item = new DetailItem();
                    item.setId(id);
                    item.setContentSize(webElement.findElement(By.xpath("td[2]")).getText());
                    item.setUrl(webElement.findElement(By.xpath("td[1]/a")).getAttribute("href"));
                    item.setUrlInfo(webElement.findElement(By.xpath("td[1]/a")).getText());
                    mainService.insertDetail(item);

                } catch (NoSuchElementException e) {
                    //捕捉内层找不到元素的异常
                    e.printStackTrace();
                }
            }
        } catch (NoSuchElementException e) {

        }


        return true;
    }

    //一个具体的URL的爬取逻辑(空url/标题url/详情页url/其他url)
    public boolean work(WebDriver webDriver, String url) {
        boolean isSuccess = false;
        int workCount = 0;
        //1.空url直接返回
        if (StringUtils.isEmpty(url)) {
            return true;
        }

        //2.详情页url
        if (url.contains("dy8c.com/entertainment")) {
            //爬取失败的时候重试一定次数，最后检查是否成功
            do {
                isSuccess = detailPage(webDriver, url);
                workCount++;
            } while (!isSuccess && workCount < retryTimes);

        } else if (url.contains("dy8c.com")) {
            do {
                isSuccess = titlePage(webDriver, url);
                workCount++;
            } while (!isSuccess && workCount < retryTimes);
        } else {
            return true;
        }
        if(isSuccess){
            return true;
        }else {
            System.out.println("页面  "+url+"  爬取失败");
            return false;
        }

    }


}
