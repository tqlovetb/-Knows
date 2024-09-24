package tq.tyd.knows.kafka.demo;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import tq.tyd.knows.kafka.vo.Message;

import javax.annotation.Resource;

@Component
@Slf4j
public class Procuder {
    // 从Spring容器中获得能够操作kafka的对象
    // 这个对象是Spring-kafka框架提供的,我们无需编写
    // KafkaTemplate<[话题名称的类型],[传递消息的类型]>
    @Resource
    private KafkaTemplate<String, String> kafkaTemplate;

    int i = 1;
    @Scheduled(fixedRate = 10000)
    public void senMessage(){
        Message message = new Message()
                .setId(i++)
                .setContent("这是发送给Kafka的消息")
                .setTime(System.currentTimeMillis());
        Gson gson = new Gson();
        String json = gson.toJson(message);
        log.debug("即将发送消息:{}", json);
        kafkaTemplate.send("myTopic",json);
        log.debug("消息已发送！");

    }

}
