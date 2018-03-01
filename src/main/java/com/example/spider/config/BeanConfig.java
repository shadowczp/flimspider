package com.example.spider.config;

import com.example.spider.Main;
import com.example.spider.util.WebDriverPool;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

@Configuration
public class BeanConfig {
    @Value("${poolSize}")
    private Integer poolSize;
    @Bean
    public WebDriverPool webDriverPool(){
        return new WebDriverPool(poolSize);
    }
    @Bean
    public ExecutorService executorService(){
        ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
        return cachedThreadPool;
    }
    @Bean(name = "urls")
    public LinkedBlockingQueue<String> linkedBlockingQueue(){
        return new LinkedBlockingQueue<>();
    }
    @Bean
    public Main main(){
        return new Main();
    }

}
