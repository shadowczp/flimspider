package com.example.spider.config;

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
    @Value("${jsEnable}")
    private boolean jsEnable;

    @Bean
    public WebDriverPool webDriverPool() {
        return new WebDriverPool(poolSize, jsEnable);
    }

    @Bean
    public ExecutorService executorService() {
        ExecutorService cachedThreadPool = Executors.newFixedThreadPool(poolSize);
        return cachedThreadPool;
    }

    @Bean(name = "urls")
    public LinkedBlockingQueue<String> linkedBlockingQueue() {
        return new LinkedBlockingQueue<>();
    }
}
