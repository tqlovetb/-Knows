package tq.tyd.knows.kafka.demo;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import tq.tyd.knows.kafka.vo.Message;

@Component
@Slf4j
public class Consumer {
    @KafkaListener(topics = "myTopic")
    public void receive(ConsumerRecord<String, String> record){
        String json = record.value();
        Gson gson = new Gson();
        Message message = gson.fromJson(json, Message.class);
        log.debug("接收到的消息:{}",message);
    }
}
