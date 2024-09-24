package tq.tyd.knows.portal;

import org.junit.jupiter.api.Test;//一定要用这个，要不然会出现空指针异常
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import tq.tyd.knows.portal.mapper.TagMapper;
import tq.tyd.knows.portal.model.Tag;

import java.util.List;

// SpringBoot环境下要想测试必须在测试类上添加这个注解
@SpringBootTest
public class TagTest {
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
    PasswordEncoder encoder = new BCryptPasswordEncoder();
    @Test
    public void pwd(){
        String str = "123456";
        String pwd = encoder.encode(str);
//        $2a$10$tLuAIELMdtNqvjbolahf0.kXUczflBSQEKfzBPS4xBsqIQnjtDj9K
        System.out.println(pwd);
    }
    @Test
    public void match(){
        boolean b = encoder.matches("123456","$2a$10$7sArRpk0xhLtR7.c.T9Uw.XYkqIpQFhPBGsYJmY/Qk1EgkbDUtl4m");
        System.out.println(b);
    }

}
