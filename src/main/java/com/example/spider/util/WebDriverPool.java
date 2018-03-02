package com.example.spider.util;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class WebDriverPool {
    private BlockingQueue<WebDriver> queue;

    public WebDriverPool(int poolSize) {
        queue = new LinkedBlockingQueue<>(poolSize);
        for (int i = 0; i < poolSize; i++) {
//            WebDriver webDriver = new HtmlUnitDriver(BrowserVersion.FIREFOX_45,false);
            WebDriver webDriver = new HtmlUnitDriver();
            queue.add(webDriver);
        }
    }

    public WebDriver getWebDriver() throws InterruptedException {
        WebDriver webDriver = queue.take();
        return webDriver;
    }
    public void releaseWebDriver(WebDriver webDriver) throws InterruptedException {
        queue.put(webDriver);
    }
}
