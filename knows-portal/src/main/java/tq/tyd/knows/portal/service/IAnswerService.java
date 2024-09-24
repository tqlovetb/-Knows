package tq.tyd.knows.portal.service;

import tq.tyd.knows.portal.model.Answer;
import com.baomidou.mybatisplus.extension.service.IService;
import tq.tyd.knows.portal.vo.AnswerVo;

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
