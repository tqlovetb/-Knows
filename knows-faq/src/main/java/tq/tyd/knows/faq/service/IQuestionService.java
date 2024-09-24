package tq.tyd.knows.faq.service;
import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import tq.tyd.knows.commons.model.Question;
import tq.tyd.knows.faq.vo.QuestionVo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 糖球
 * @since 2024-06-26
 */
public interface IQuestionService extends IService<Question> {

    PageInfo<Question> getQuestions(String username,Integer pageNum, Integer pageSize);
    void saveQuestion(QuestionVo questionVo, String username);
    PageInfo<Question> getTeacherQuestions(String username,Integer pageNum,Integer pageSize);
    Question getQuestionById(Integer id);
    Integer countQuestionsByUserId(Integer userId);
    PageInfo<Question> getQuestions(Integer pageNum, Integer pageSize);

}
