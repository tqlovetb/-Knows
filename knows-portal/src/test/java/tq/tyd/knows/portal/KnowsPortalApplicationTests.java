package tq.tyd.knows.portal;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tq.tyd.knows.portal.mapper.TagMapper;
import tq.tyd.knows.portal.model.Tag;

import java.util.List;

@SpringBootTest
class KnowsPortalApplicationTests {

    @Test
    void contextLoads() {
    }

    @Autowired
    TagMapper tagMapper;
    // 测试TagMapper是否可用
    @Test
    public void getAll(){
        System.out.println("1111");
        List<Tag> tags = tagMapper.selectList(null);
        if(tags != null){
            for(Tag t:tags){
                System.out.println(t);
            }
        }

    }

}
