package tq.tyd.knows.faq.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tq.tyd.knows.commons.exception.ServiceException;
import tq.tyd.knows.commons.model.Answer;
import tq.tyd.knows.commons.model.Question;
import tq.tyd.knows.commons.model.User;
import tq.tyd.knows.faq.mapper.AnswerMapper;
import tq.tyd.knows.faq.mapper.QuestionMapper;
import tq.tyd.knows.faq.service.IAnswerService;
import tq.tyd.knows.faq.vo.AnswerVo;

import javax.annotation.Resource;
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
    @Resource
    RibbonClient ribbonClient;
    @Autowired
    AnswerMapper answerMapper;
    @Autowired
    QuestionMapper questionMapper;
    @Override
    public Answer saveAnswer(AnswerVo answerVo, String username) {
        User user = ribbonClient.getUser(username);
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
        User user=ribbonClient.getUser(username);
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
