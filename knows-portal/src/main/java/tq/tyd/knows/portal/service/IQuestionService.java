package tq.tyd.knows.portal.service;

import com.github.pagehelper.PageInfo;
import tq.tyd.knows.portal.model.Question;
import com.baomidou.mybatisplus.extension.service.IService;
import tq.tyd.knows.portal.vo.QuestionVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 糖球
 * @since 2024-06-26
 */
public interface IQuestionService extends IService<Question> {

    PageInfo<Question> getQuestions(String username,Integer pageNum,Integer pageSize);
    void saveQuestion(QuestionVo questionVo,String username);
    PageInfo<Question> getTeacherQuestions(String username,Integer pageNum,Integer pageSize);
    Question getQuestionById(Integer id);

}
