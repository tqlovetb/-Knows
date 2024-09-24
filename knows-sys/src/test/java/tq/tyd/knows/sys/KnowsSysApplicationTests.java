package tq.tyd.knows.sys;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import tq.tyd.knows.commons.model.User;
import tq.tyd.knows.sys.mapper.UserMapper;

import javax.annotation.Resource;

@SpringBootTest
class KnowsSysApplicationTests {

    @Resource
    UserMapper userMapper;
    @Test
    void contextLoads() {
        User user=userMapper.findUserByUsername("st2");
        System.out.println(user);

    }
}
