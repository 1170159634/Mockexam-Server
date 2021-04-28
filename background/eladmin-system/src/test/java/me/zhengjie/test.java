package me.zhengjie;


import me.zhengjie.modules.mockexam.service.CrawlingService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class test {

    @Autowired
    private CrawlingService crawlingService;

    @Test
    public void contextLoads() {
        crawlingService.pic2Local();
    }




}

