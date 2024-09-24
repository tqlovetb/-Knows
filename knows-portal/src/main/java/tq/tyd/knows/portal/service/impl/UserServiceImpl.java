package tq.tyd.knows.portal.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import tq.tyd.knows.portal.exception.ServiceException;
import tq.tyd.knows.portal.mapper.ClassroomMapper;
import tq.tyd.knows.portal.mapper.QuestionMapper;
import tq.tyd.knows.portal.mapper.UserRoleMapper;
import tq.tyd.knows.portal.model.Classroom;
import tq.tyd.knows.portal.model.User;
import tq.tyd.knows.portal.mapper.UserMapper;
import tq.tyd.knows.portal.model.UserRole;
import tq.tyd.knows.portal.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import tq.tyd.knows.portal.vo.RegisterVo;
import tq.tyd.knows.portal.vo.UserVo;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 糖球
 * @since 2024-06-26
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
    @Autowired
    UserMapper userMapper;
    @Autowired
    ClassroomMapper classroomMapper;
    @Autowired
    UserRoleMapper userRoleMapper;
    @Autowired
    QuestionMapper questionMapper;

    public UserVo getUserVo(String username){
        User user = userMapper.findUserByUsername(username);
        UserVo userVo = new UserVo()
                .setId(user.getId())
                .setUsername(user.getUsername())
                .setNickname(user.getNickname())
                .setQuestions(questionMapper.countQuestionsByUserId(user.getId()))
                .setCollections(questionMapper.countCollectionsByUserId(user.getId()));
        return userVo;
    }

    @Override
    public void registerStudent(RegisterVo registerVo) {
        QueryWrapper<Classroom> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("invite_code", registerVo.getInviteCode());
        Classroom classroom = classroomMapper.selectOne(queryWrapper);
        if(classroom == null){
            throw new SecurityException("邀请码错误");
        }
        User user = userMapper.findUserByUsername(registerVo.getPhone());
        if(user != null){
            throw new ServiceException("手机号已经注册过了");
        }
        User stu = new User()
                .setUsername(registerVo.getPhone())
                .setNickname(registerVo.getNickname())
                .setPassword(registerVo.getPassword())
                .setClassroomId(classroom.getId())
                .setCreatetime(LocalDateTime.now())
                .setEnabled(1)
                .setLocked(0)
                .setType(0);
        int num  = userMapper.insert(stu);
        if(num != 1){
            throw new ServiceException("数据库异常");
        }
        UserRole userRole = new UserRole()
                .setRoleId(2)
                .setUserId(stu.getId());
        num = userRoleMapper.insert(userRole);
        if(num != 1){
            throw new ServiceException("数据库异常");
        }

    }
    private List<User> teachers = new CopyOnWriteArrayList<>();
    private Map<String, User> teacherMap = new ConcurrentHashMap<>();
    @Override
    public List<User> getTeachers() {
        if(teachers.isEmpty()){
            synchronized (teachers){
                if(teachers.isEmpty()){
                    List<User> users = userMapper.findTeachers();
                    teachers.addAll(users);
                    for(User u:users){
                        teacherMap.put(u.getNickname(), u);
                    }
                }
            }
        }
        return teachers;
    }


    @Override
    public Map<String, User> getTeacherMap() {
        if(teacherMap.isEmpty()){
            getTeachers();
        }

        return teacherMap;
    }
}
