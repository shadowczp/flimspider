package com.example.spider;

import com.example.spider.service.SpiderService;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.concurrent.ExecutorService;

@MapperScan("com.example.spider.mapper")
@SpringBootApplication
public class SpiderApplication {
    @Autowired
    private ExecutorService executorService;
    @Autowired
    private SpiderService spiderService;

    public static void main(String[] args) throws InterruptedException {
        ApplicationContext context = SpringApplication.run(SpiderApplication.class, args);
        context.getBean(SpiderApplication.class).start(args);
    }

    /**
     * 主逻辑在这里
     * 这里主要是控制流程，具体的逻辑在SpiderService类中
     * 以后要爬别的内容，主要就是改这里的流程和SpiderService类的逻辑
     *
     * @param args
     * @throws InterruptedException
     */
    public void start(String[] args) throws InterruptedException {
        //给一个起始url
        spiderService.addUrl("http://www.dy8c.com");
        for (int i = 2; i < 1918; i++) {
            spiderService.addUrl("http://www.dy8c.com/page/" + i + "/");
        }

        while (true) {
            executorService.execute(() -> {
                spiderService.run();
            });
            //休眠
            Thread.sleep(30);
        }
    }

}
