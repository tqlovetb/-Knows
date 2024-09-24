package tq.tyd.knows.serach.controller;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.protocol.types.Field;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import tq.tyd.knows.commons.vo.Topic;
import tq.tyd.knows.serach.service.IQuestionService;
import tq.tyd.knows.serach.vo.QuestionVo;

import javax.annotation.Resource;

@Component
@Slf4j
public class QuestionConsumer {
    @Resource
    private IQuestionService questionService;
    @KafkaListener(topics = Topic.QUESTION)
    public void questionReceive(ConsumerRecord<String,String> record){
        String json = record.value();
        Gson gson = new Gson();
        QuestionVo questionVo = gson.fromJson(json, QuestionVo.class);
        questionService.saveQuestion(questionVo);
        log.debug("成功的新增了问题到ES:{}", questionVo);
    }
}
