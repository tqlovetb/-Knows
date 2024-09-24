package tq.tyd.knows.kafka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
// 启动当前项目对kafka的支持
@EnableKafka
// 启动SpringBoot框架内部的定时任务功能
// 和kafka没有必然依赖关系,只是需要测试时使用它
@EnableScheduling
public class KnowsKafkaApplication {

    public static void main(String[] args) {
        SpringApplication.run(KnowsKafkaApplication.class, args);
    }

}
