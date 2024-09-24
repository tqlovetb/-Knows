package tq.tyd.knows.portal.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import tq.tyd.knows.portal.model.Question;
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
public interface QuestionMapper extends BaseMapper<Question> {
    @Select("select count(*) from question where user_id=#{id}")
    int countQuestionsByUserId(Integer userId);
    @Select("select count(*) from user_collect where user_id=#{id}")
    int countCollectionsByUserId(Integer userId);

    @Select("select q.* from question q " +
            "left join user_question uq on q.id=uq.question_id " +
            "where uq.user_id=#{id} or q.user_id=#{id} " +
            "order by q.createtime desc")
    List<Question> findTeacherQuestions(Integer id);

    @Update("update question set status=#{status} " +
            " where id=#{questionId}")
    int updateStatus(@Param("status") Integer status,
                     @Param("questionId") Integer questionId );
}
