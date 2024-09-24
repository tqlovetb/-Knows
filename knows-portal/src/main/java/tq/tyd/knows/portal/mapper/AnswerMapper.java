package tq.tyd.knows.portal.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import tq.tyd.knows.portal.model.Answer;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

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
