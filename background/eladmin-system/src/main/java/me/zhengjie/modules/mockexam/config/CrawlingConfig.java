package me.zhengjie.modules.mockexam.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author :         Mingxuan_x
 * @version :        1.0
 * @Description: 小葱计算爬取数据工具类
 * @Telephone :      15135964789
 * @createDate :     2021/4/10 17:08
 * @updateUser :     Mingxuan_x
 * @updateDate :     2021/4/10 17:08
 * @updateRemark :   修改内容
 **/
@Configuration
public class CrawlingConfig {


    @Data
    public static class CrawlingConfigProperties {
        //url
        private String url;
        //接口唯一标识
        private String key;
        //选题类型(全部)
        private String models;
        //查询选题类型
        private String model;
        //科目类型
        private String subject;
        //获取类型
        private String testType;


    }

    @Bean
    @ConfigurationProperties(prefix = "crawling")
    public CrawlingConfigProperties CrawlingConfig() {
        return new CrawlingConfigProperties();
    }
}
