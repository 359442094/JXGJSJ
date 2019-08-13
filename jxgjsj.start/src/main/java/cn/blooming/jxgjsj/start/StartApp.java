package cn.blooming.jxgjsj.start;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@ComponentScan(basePackages = {
        "cn.blooming.jxgjsj.model",
        "cn.blooming.jxgjsj.service",
        "cn.blooming.jxgjsj.controller",
        "cn.blooming.jxgjsj.api",
        "cn.blooming.jxgjsj.start"
})
@MapperScan(basePackages = {
        "cn.blooming.jxgjsj.model.mapper"
})
@EnableConfigServer
@EnableSwagger2
@EnableWebMvc
@EnableCaching//开启缓存
public class StartApp {
    public static void main(String[] args) {
        SpringApplication.run(StartApp.class,args);
    }
}
