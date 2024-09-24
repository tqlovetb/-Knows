package tq.tyd.knows.portal.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import tq.tyd.knows.portal.exception.ServiceException;
import tq.tyd.knows.portal.mapper.QuestionMapper;
import tq.tyd.knows.portal.mapper.UserMapper;
import tq.tyd.knows.portal.model.Answer;
import tq.tyd.knows.portal.mapper.AnswerMapper;
import tq.tyd.knows.portal.model.Question;
import tq.tyd.knows.portal.model.User;
import tq.tyd.knows.portal.service.IAnswerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import tq.tyd.knows.portal.service.IUserService;
import tq.tyd.knows.portal.vo.AnswerVo;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 糖球
 * @since 2024-06-26
 */
@Service
public class AnswerServiceImpl extends ServiceImpl<AnswerMapper, Answer> implements IAnswerService {
    @Autowired
    UserMapper userMapper;
    @Autowired
    AnswerMapper answerMapper;
    @Autowired
    QuestionMapper questionMapper;
    @Override
    public Answer saveAnswer(AnswerVo answerVo, String username) {
        User user = userMapper.findUserByUsername(username);
        Answer answer = new Answer()
                .setContent(answerVo.getContent())
                .setLikeCount(0)
                .setAcceptStatus(0)
                .setQuestId(answerVo.getQuestionId())
                .setUserId(user.getId())
                .setUserNickName(user.getNickname())
                .setCreatetime(LocalDateTime.now());
        int num = answerMapper.insert(answer);
        if(num!=1){
            throw new ServiceException("数据库异常");
        }
        return answer;
    }

    @Override
    public List<Answer> getAnswerByQuestionId(Integer questionId) {
        /*QueryWrapper<Answer> qw = new QueryWrapper<>();
        qw.eq("quest_id", questionId);
        List<Answer> answers = answerMapper.selectList(qw);*/
        List<Answer> answers = answerMapper.findAnswersByQuestionId(questionId);
        return answers;
    }
    @Override
    public boolean accept(Integer answerId,String username){
        User user=userMapper.findUserByUsername(username);
        Answer answer = answerMapper.selectById(answerId);
        Question question = questionMapper.selectById(answer.getQuestId());
        if(user.getId().equals(question.getUserId())){
            int num = answerMapper.updateAcceptStatus(1,answerId);
            if(num!=1){
                throw new ServiceException("数据库忙！");
            }
            num=questionMapper.updateStatus(Question.SOLVED, question.getId());
            if(num!=1){
                throw new ServiceException("数据库忙！");
            }
        }
        return false;
    }

}
