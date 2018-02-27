package com.example.spider;

import com.example.spider.pojo.TitleItem;
import com.example.spider.service.MainService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpiderApplicationTests {

    @Autowired
    private MainService mainService;
	@Test
	public void contextLoads() {
	}
	@Test
    public void driver(){
        WebDriver driver = new HtmlUnitDriver(true);
        driver.get("http://www.dy8c.com/");
        String html = driver.getPageSource();
        System.out.println(html);
    }

    @Test
    public void mybatis(){
	    TitleItem item =mainService.queryById("post-100");
	    System.out.println(item.getId()+item.getDetailUrl()+item.getImgUrl()+item.getTitle()+item.getNote());
    }
}
