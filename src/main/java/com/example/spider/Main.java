package com.example.spider;

import com.example.spider.pojo.TitleItem;
import com.example.spider.util.WebDriverPool;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.concurrent.ExecutorService;

public class Main {
    @Autowired
    private WebDriverPool webDriverPool;
    @Autowired
    private ExecutorService executorService;

    public void titlePage(WebDriver webDriver,String url) {
        webDriver.get(url);
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
                    System.out.println(item.getId());
                    System.out.println(item.getUpdateTime());
                    System.out.println(item.getTitle());
                    System.out.println(item.getDetailUrl());
                    System.out.println(item.getImgUrl());
                    System.out.println(item.getNote());
                } catch (Exception e) {
                    //捕捉内层找不到元素的异常
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            //捕捉外层找不到元素的异常
            e.printStackTrace();
        }
    }

    public void detailPage() {

    }
}
