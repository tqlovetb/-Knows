package tq.tyd.knows.serach.service.impl;

import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import tq.tyd.knows.commons.model.Question;
import tq.tyd.knows.commons.model.Tag;
import tq.tyd.knows.commons.model.User;
import tq.tyd.knows.serach.repository.QuestionRepository;
import tq.tyd.knows.serach.service.IQuestionService;
import tq.tyd.knows.serach.utils.Pages;
import tq.tyd.knows.serach.vo.QuestionVo;

import javax.annotation.Resource;
import java.util.*;

@Service
@Slf4j
public class QuestionServiceImpl implements IQuestionService {
    @Resource
    private RestTemplate restTemplate;
    @Resource
    private QuestionRepository questionRepository;
    @Override
    public void synData() {
        String url="http://faq-service/v2/questions/page/count?pageSize={1}";
        int pageSize = 8;
        System.out.println("断电");
        Integer total = restTemplate.getForObject(url, Integer.class,pageSize);
        System.out.println("total"+total);
        for(int i=1;i<=total;i++){
            url= "http://faq-service/v2/questions/page?pageNum={1}&pageSize={2}";
            QuestionVo[] questionVos = restTemplate.getForObject(url, QuestionVo[].class,i,pageSize);
            System.out.println(questionVos);
            questionRepository.saveAll(Arrays.asList(questionVos));
            log.debug("完成了第{}页的新增", i);
        }
    }

    @Override
    public PageInfo<QuestionVo> search(String key, String username, Integer pageNum, Integer pageSize) {
        String url = "http://sys-service/v1/auth/user?username={1}";
        User user = restTemplate.getForObject(url, User.class, username);
        Pageable pageable = PageRequest.of(pageNum-1,pageSize, Sort.Direction.DESC,"createtime");
        Page<QuestionVo> page = questionRepository.queryAllByParams(key, key, user.getId(), pageable);
        for(QuestionVo vo:page){
            vo.setTags(tagNameToTags(vo.getTagNames()));
        }
        return Pages.pageInfo(page);
    }

    @Override
    public void saveQuestion(QuestionVo questionVo) {
        questionRepository.save(questionVo);
    }

    private List<Tag> tagNameToTags(String tagNames){
        String[] names = tagNames.split(",");
        String url="http://faq-service/v2/tags";
        Tag[] tagArr = restTemplate.getForObject(url, Tag[].class);
        Map<String,Tag> tagMap = new HashMap<>();
        for(Tag t:tagArr){
            tagMap.put(t.getName(), t);
        }
        List<Tag> tags = new ArrayList<>();
        for(String name:names){
            Tag t = tagMap.get(name);
            tags.add(t);
        }
        return tags;
    }
}
