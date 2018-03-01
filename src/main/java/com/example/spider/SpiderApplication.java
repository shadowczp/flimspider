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
        String url = null;
        while((url=urls.poll()) != null){

        }

        WebDriver webDriver = webDriverPool.getWebDriver();
        work(webDriver, url);

        System.out.println("exit");

    }

    public void work(WebDriver webDriver, String url) {
        boolean isSuccess = false;
        int workCount = 0;
        if (StringUtils.isEmpty(url)) {
            return;
        }
        if (url.contains("dy8c.com/entertainment")) {
            //爬取失败的时候重试一定次数，最后检查是否成功
            do {
                isSuccess = spider.detailPage(webDriver, url);
                workCount++;
            } while (!isSuccess && workCount < retryTimes);
            if(isSuccess){
                return;
            }else {
                System.out.println("页面  "+url+"  爬取失败");
                return;
            }
        } else if (url.contains("dy8c.com")) {
            do {
                isSuccess = spider.titlePage(webDriver, url);
                workCount++;
            } while (!isSuccess && workCount < retryTimes);
            if(isSuccess){
                return;
            }else {
                System.out.println("页面  "+url+"  爬取失败");
                return;
            }
        } else {
            return;
        }
        executorService.execute(() -> {
            try {
                WebDriver webDriver = webDriverPool.getWebDriver();
                boolean flag = false;
                flag = spider.detailPage(webDriver, "http://www.dy8c.com/entertainment/164505/");
                while (flag == false) {
                    flag = spider.detailPage(webDriver, "http://www.dy8c.com/entertainment/164505/");
                }
                String urlm;
                while ((urlm = spider.getUrl()) != null) {
                    System.out.println(urlm);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        });
    }
}
