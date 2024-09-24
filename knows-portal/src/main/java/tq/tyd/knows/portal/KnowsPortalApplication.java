package tq.tyd.knows.portal;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
// 下面注解的含义是令Mybatis框架扫描指定包中的所有接口和类
// 相当于为指定包中所有类和接口都添加了@Mapper注解

@MapperScan("tq.tyd.knows.portal.mapper")
public class KnowsPortalApplication {

    public static void main(String[] args) {
        SpringApplication.run(KnowsPortalApplication.class, args);
    }

}
