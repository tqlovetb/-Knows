package tq.tyd.knows.faq.service.impl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import tq.tyd.knows.commons.exception.ServiceException;
import tq.tyd.knows.commons.model.*;
import tq.tyd.knows.faq.kafka.QuestionProducer;
import tq.tyd.knows.faq.mapper.QuestionMapper;
import tq.tyd.knows.faq.mapper.QuestionTagMapper;
import tq.tyd.knows.faq.mapper.UserQuestionMapper;
import tq.tyd.knows.faq.service.IQuestionService;
import tq.tyd.knows.faq.service.ITagService;
import tq.tyd.knows.faq.vo.QuestionVo;

import javax.annotation.Resource;
import javax.jws.soap.SOAPBinding;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 糖球
 * @since 2024-06-26
 */
@Service
@Slf4j
public class QuestionServiceImpl extends ServiceImpl<QuestionMapper, Question> implements IQuestionService {

    @Autowired
    QuestionMapper questionMapper;
    @Autowired
    ITagService tagService;
    @Autowired
    QuestionTagMapper questionTagMapper;

    @Autowired
    UserQuestionMapper userQuestionMapper;
    @Resource
    private RestTemplate restTemplate;

    @Resource
    private QuestionProducer questionProducer;

    private User getUser(String username){
        String url = "http://sys-service/v1/auth/user?username={1}";
        User user = restTemplate.getForObject(url, User.class, username);
        return user;
    }

    @Override
    public PageInfo<Question> getQuestions(String username,Integer pageNum,Integer pageSize) {
        User user = getUser(username);
        QueryWrapper<Question> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",user.getId());
        queryWrapper.eq("delete_status", 0);
        queryWrapper.orderByDesc("createtime");
        PageHelper.startPage(pageNum,pageSize);
        List<Question> questions = questionMapper.selectList(queryWrapper);
        for (Question question:questions){
            List<Tag> tags = tagNamesToTags(question.getTagNames());
            question.setTags(tags);
        }
        return new PageInfo<>(questions);
    }

    private List<Tag> tagNamesToTags(String tagNames){
        String[] names = tagNames.split(",");
        Map<String,Tag> tagMap = tagService.getTagMap();
        List<Tag> tags = new ArrayList<>();
        for(String name:names){
            Tag t = tagMap.get(name);
            tags.add(t);
        }
        return tags;
    }

    @Override
    // 添加事务注解
// 实现效果:当前方法中所有sql操作要么都执行,要么都不执行
// 只要方法运行过程中发生异常,那么已经执行的sql语句都会"回滚"
    @Transactional
    public void saveQuestion(QuestionVo questionVo, String username) {
        User user = getUser(username);
        StringBuilder builder = new StringBuilder();
        for(String t:questionVo.getTagNames()){
            builder.append(t).append(",");
        }
        String tagNames = builder.deleteCharAt(builder.length()-1).toString();
        Question question = new Question()
                .setTitle(questionVo.getTitle())
                .setContent(questionVo.getContent())
                .setUserNickName(user.getNickname())
                .setUserId(user.getId())
                .setCreatetime(LocalDateTime.now())
                .setStatus(0)
                .setPageViews(0)
                .setPublicStatus(0)
                .setDeleteStatus(0)
                .setTagNames(tagNames);
        int num = questionMapper.insert(question);
        if(num!=1){
            throw new ServiceException("数据库异常！");
        }
        Map<String,Tag> tagMap = tagService.getTagMap();
        for(String tagName:questionVo.getTagNames()){
            Tag t = tagMap.get(tagName);
            QuestionTag questionTag = new QuestionTag()
                    .setQuestionId(question.getId())
                    .setTagId(t.getId());
            num = questionTagMapper.insert(questionTag);
            if(num!=1){
                throw new ServiceException("数据库异常！");
            }
            log.debug("新增了问题和标签的关系:{}",questionTag);
        }

        String url="http://sys-service/v1/users/master";
        User[] teachers=restTemplate.getForObject(url,User[].class);
        Map<String,User> teacherMap = new HashMap<>();
        for(User u : teachers){
            teacherMap.put(u.getNickname(), u);
        }
        for(String nickname:questionVo.getTeacherNicknames()){
            User u = teacherMap.get(nickname);
            UserQuestion userQuestion = new UserQuestion()
                    .setQuestionId(question.getId())
                    .setUserId(u.getId())
                    .setCreatetime(LocalDateTime.now());
            num = userQuestionMapper.insert(userQuestion);
            if(num!=1){
                throw new ServiceException("数据库忙！");
            }
            log.debug("新增了问题和讲师的关系:{}",userQuestion);
        }
        //将刚刚新增完成的问题,发送到kafka
        //以便search模块接收并新增到Es
        questionProducer.senQuestion(question);

    }

    @Override
    public PageInfo<Question> getTeacherQuestions(String username, Integer pageNum, Integer pageSize) {
        User user = getUser(username);

        PageHelper.startPage(pageNum,pageSize);
        List<Question> questions = questionMapper.findTeacherQuestions(user.getId());
        for (Question question:questions){
            List<Tag> tags = tagNamesToTags(question.getTagNames());
            question.setTags(tags);
        }
        return new PageInfo<>(questions);
    }

    @Override
    public Question getQuestionById(Integer id) {
        Question question = questionMapper.selectById(id);
        List<Tag> tags = tagNamesToTags(question.getTagNames());
        question.setTags(tags);
        return question;
    }

    @Override
    public Integer countQuestionsByUserId(Integer userId) {
        return questionMapper.countQuestionsByUserId(userId);
    }

    @Override
    public PageInfo<Question> getQuestions(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Question> list = questionMapper.selectList(null);
        for(Question q : list){
            System.out.println(q);
        }
        return new PageInfo<>(list);
    }

}
