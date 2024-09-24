package tq.tyd.knows.faq.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;
import tq.tyd.knows.commons.model.Answer;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author 糖球
 * @since 2024-06-26
 */
@Repository
public interface AnswerMapper extends BaseMapper<Answer> {
    List<Answer> findAnswersByQuestionId(Integer questionId);

    @Update("update question set status=#{status} where id=#{questionId}")
    @Update("update answer set accept_status=#{acceptStatus} " +
            " where id=#{answerId}")
    int updateAcceptStatus(@Param("acceptStatus") Integer acceptStatus,
                           @Param("answerId") Integer answerId);


}
