package com.example.spider;

import com.example.spider.pojo.DetailItem;
import com.example.spider.pojo.TitleItem;
import com.example.spider.service.MainService;
import org.junit.Before;
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
    private TitleItem titleItem;
    private DetailItem detailItem;
    @Before
    public void init(){
        titleItem = new TitleItem();
        titleItem.setId("测试id1");
        titleItem.setImgUrl("图片url");
        titleItem.setDetailUrl("详细url");
        titleItem.setNote("备注");
        titleItem.setTitle("标题");
        titleItem.setUpdateTime("更新时间");

        detailItem = new DetailItem();
        detailItem.setContentSize("10G");
        detailItem.setId("测试id1");
        detailItem.setUrl("下载url");
        detailItem.setUrlInfo("迅雷高清");
    }
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

    @Test
    public void insertTitle(){
	    mainService.insertTitle(titleItem);
    }

    @Test
    public void insertDetail(){
        mainService.insertDetail(detailItem);
    }
}
