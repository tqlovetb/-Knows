package tq.tyd.knows.serach.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import tq.tyd.knows.commons.model.Question;
import tq.tyd.knows.serach.vo.QuestionVo;

@Repository
public interface QuestionRepository extends ElasticsearchRepository<QuestionVo, Integer> {
    @Query("{\n" +
            "    \"bool\": {\n" +
            "      \"must\": [{\n" +
            "        \"bool\": {\n" +
            "          \"should\": [\n" +
            "          {\"match\": {\"title\": \"?0\"}}, \n" +
            "          {\"match\": {\"content\": \"?1\"}}]\n" +
            "        }\n" +
            "      }, {\n" +
            "        \"bool\": {\n" +
            "          \"should\": [\n" +
            "          {\"term\": {\"publicStatus\": 1}}, \n" +
            "          {\"term\": {\"userId\": ?2}}]\n" +
            "        }\n" +
            "      }]\n" +
            "    }\n" +
            "  }")
    Page<QuestionVo> queryAllByParams(String title, String content, Integer userId, Pageable pageable);

}
