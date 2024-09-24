package tq.tyd.knows.faq.service;

import com.baomidou.mybatisplus.extension.service.IService;
import tq.tyd.knows.commons.model.Answer;
import tq.tyd.knows.faq.vo.AnswerVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 糖球
 * @since 2024-06-26
 */
public interface IAnswerService extends IService<Answer> {
    Answer saveAnswer(AnswerVo answerVo, String username);
    List<Answer> getAnswerByQuestionId(Integer questionId);
    boolean accept(Integer answerId,String username);

}
