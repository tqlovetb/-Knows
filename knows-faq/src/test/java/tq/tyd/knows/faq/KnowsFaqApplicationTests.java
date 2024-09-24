package tq.tyd.knows.faq;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.client.RestTemplate;
import tq.tyd.knows.commons.model.Tag;
import tq.tyd.knows.commons.model.User;
import tq.tyd.knows.faq.mapper.TagMapper;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
class KnowsFaqApplicationTests {
    @Autowired
    TagMapper tagMapper;

    @Resource
    RedisTemplate<String,String> redisTemplate;
    @Test
    void contextLoads() {
        List<Tag> tags = tagMapper.selectList(null);
        for(Tag t : tags){
            System.out.println(t);
        }
    }
    @Test
    public void redis(){
        redisTemplate.opsForValue().set("myname", "诸葛亮");
        System.out.println("ok");
    }
    @Test
    public void getValue(){
        String name=redisTemplate.opsForValue().get("myname");
        System.out.println(name);
    }

    @Resource
    RestTemplate restTemplate;
    @Test
    public void ribbon(){
        String url="http://sys-service/v1/auth/demo";
        String str = restTemplate.getForObject(url,String.class);
        System.out.println(str);

    }

    @Test
    public void getUser(){
        /*对于 RestTemplate 中的占位符索引，通常是从 0 开始计数的。例如，如果你的 URL 中有 {0}, {1}, {2} 等占位符，那么在使用 getForObject 方法时，后续的参数 "st2", "paramValue", "anotherValue" 等会依次替换这些占位符的位置。
        * 但是这里的String.class不算参数*/
        String url = "http://sys-service/v1/auth/user?username={1}";
        User user = restTemplate.getForObject(url, User.class,"st2");
        System.out.println(user);
    }

}
