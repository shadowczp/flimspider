package com.example.spider;

import com.example.spider.util.WebDriverPool;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.List;
import java.util.concurrent.ExecutorService;

@SpringBootApplication
public class SpiderApplication {
    @Autowired
    private WebDriverPool webDriverPool;
    @Autowired
    private ExecutorService executorService;
    @Autowired
    private Main spider;

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

        executorService.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    WebDriver webDriver = webDriverPool.getWebDriver();
                    boolean flag = false;
                    flag=spider.detailPage(webDriver,"http://www.dy8c.com/entertainment/164505/");
                    while (flag == false){
                        flag=spider.detailPage(webDriver,"http://www.dy8c.com/entertainment/164505/");
                    }
                    String url;
                    while ((url = spider.getUrl())!=null){
                        System.out.println(url);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        System.out.println("exit");

    }
}
