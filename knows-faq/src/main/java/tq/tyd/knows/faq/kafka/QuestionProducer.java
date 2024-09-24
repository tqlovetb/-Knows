package tq.tyd.knows.faq.kafka;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import tq.tyd.knows.commons.model.Question;
import tq.tyd.knows.commons.vo.Topic;

import javax.annotation.Resource;

@Component
@Slf4j
public class QuestionProducer {
    // 从Spring容器中获得能够操作kafka的对象
    // 这个对象是Spring-kafka框架提供的,我们无需编写
    // KafkaTemplate<[话题名称的类型],[传递消息的类型]>
    @Resource
    private KafkaTemplate<String, String> kafkaTemplate;

    public void senQuestion(Question question){

        Gson gson = new Gson();
        String json = gson.toJson(question);
        log.debug("即将发送消息:{}", json);
        kafkaTemplate.send(Topic.QUESTION,json);

    }
}
