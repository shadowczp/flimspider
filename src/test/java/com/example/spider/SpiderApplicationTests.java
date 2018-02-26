package com.example.spider;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpiderApplicationTests {

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

}
