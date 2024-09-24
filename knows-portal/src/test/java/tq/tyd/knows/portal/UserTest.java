package tq.tyd.knows.portal;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tq.tyd.knows.portal.mapper.*;
import tq.tyd.knows.portal.model.*;
import tq.tyd.knows.portal.service.IUserService;
import tq.tyd.knows.portal.vo.RegisterVo;

import java.util.List;
@SpringBootTest
public class UserTest {
    @Autowired
    UserMapper userMapper;
    @Autowired
    ClassroomMapper classroomMapper;
    @Autowired
    IUserService iUserService;
    @Test
    public void userTest(){
        User u = userMapper.findUserByUsername("tc2");
        List<Permission> p = userMapper.findUserPermissionById(u.getId());
        System.out.println(u);
        for(Permission per : p){
            System.out.println(per);
        }
    }
    @Test
    public void query(){
        QueryWrapper<Classroom> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("invite_code", "JSD2002-525416");
        Classroom classroom = classroomMapper.selectOne(queryWrapper);
        System.out.println(classroom);
    }
    @Test
    public void reg(){
        RegisterVo reg = new RegisterVo();
        reg.setPhone("13313313000");
        reg.setNickname("二愣子");
        reg.setInviteCode("JSD2002-525416");
        reg.setPassword("123456");

        iUserService.registerStudent(reg);
        System.out.println("ok");
    }

    @Autowired
    QuestionMapper questionMapper;
    @Test
    public void getQuestions(){
        List<Question> questions = questionMapper.findTeacherQuestions(3);
        for(Question q : questions){
            System.out.println(q);
        }
    }

    @Autowired
    AnswerMapper answerMapper;
    @Test
    public void testAnswer(){
        List<Answer> answers = answerMapper.findAnswersByQuestionId(149);
        for(Answer a : answers){
            System.out.println(a);
        }
    }

}
