package com.example.spider;

import com.example.spider.service.MainService;
import com.example.spider.util.WebDriverPool;
import org.mybatis.spring.annotation.MapperScan;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;

@MapperScan("com.example.spider.mapper")
@SpringBootApplication
public class SpiderApplication {
    @Value("${retryTimes}")
    private int retryTimes;
    @Autowired
    private WebDriverPool webDriverPool;
    @Autowired
    private ExecutorService executorService;
    @Autowired
    private Main spider;
    @Autowired
    private MainService mainService;
    @Resource(name = "urls")
    private LinkedBlockingQueue<String> urls;

    public static void main(String[] args) throws InterruptedException {
        ApplicationContext context = SpringApplication.run(SpiderApplication.class, args);
        context.getBean(SpiderApplication.class).start(args);
    }

    /**
     * 主逻辑在这里
     * 这里主要是控制流程，具体的逻辑在Main类中
     * 以后要爬别的内容，主要就是改这里的流程和Main类的逻辑
     *
     * @param args
     * @throws InterruptedException
     */
    public void start(String[] args) throws InterruptedException {
        //给一个起始url
        urls.put("http://www.dy8c.com");

        while (true) {
            executorService.execute(() -> {
                String url = urls.poll();
                WebDriver webDriver = null;
                try {
                    webDriver = webDriverPool.getWebDriver();
                    System.out.println("当前处理URL: "+url);
                    spider.work(webDriver, url);
                    System.out.println("处理URL: "+url+"    完成");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    if (webDriver != null) {
                        try {
                            webDriverPool.releaseWebDriver(webDriver);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
            //休眠0.5s
            Thread.sleep(500);
        }
    }

    public void work(WebDriver webDriver, String url) {

    }
}
