package tq.tyd.knows.serach.service;

import com.github.pagehelper.PageInfo;
import tq.tyd.knows.serach.vo.QuestionVo;

public interface IQuestionService {
    void synData();
    PageInfo<QuestionVo> search(String key, String username, Integer pageNum, Integer pageSize);
    void saveQuestion(QuestionVo questionVo);
}
