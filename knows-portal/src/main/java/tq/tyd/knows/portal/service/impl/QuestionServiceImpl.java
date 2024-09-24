package tq.tyd.knows.portal.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import tq.tyd.knows.portal.exception.ServiceException;
import tq.tyd.knows.portal.mapper.QuestionTagMapper;
import tq.tyd.knows.portal.mapper.UserMapper;
import tq.tyd.knows.portal.mapper.UserQuestionMapper;
import tq.tyd.knows.portal.model.*;
import tq.tyd.knows.portal.mapper.QuestionMapper;
import tq.tyd.knows.portal.service.IQuestionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import tq.tyd.knows.portal.service.ITagService;
import tq.tyd.knows.portal.service.IUserService;
import tq.tyd.knows.portal.vo.QuestionVo;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
    UserMapper userMapper;
    @Autowired
    QuestionMapper questionMapper;
    @Autowired
    ITagService tagService;
    @Autowired
    QuestionTagMapper questionTagMapper;
    @Autowired
    IUserService userService;
    @Autowired
    UserQuestionMapper userQuestionMapper;

    @Override
    public PageInfo<Question> getQuestions(String username,Integer pageNum,Integer pageSize) {
        User user = userMapper.findUserByUsername(username);
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
        User user = userMapper.findUserByUsername(username);
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

        Map<String,User> teacherMap = userService.getTeacherMap();
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
            log.debug("新增了问题和标签的关系:{}",userQuestion);
        }


    }

    @Override
    public PageInfo<Question> getTeacherQuestions(String username, Integer pageNum, Integer pageSize) {
        User user = userMapper.findUserByUsername(username);

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

}
