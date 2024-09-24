package tq.tyd.knows.portal.mapper;

import org.apache.ibatis.annotations.Select;
import tq.tyd.knows.portal.model.Permission;
import tq.tyd.knows.portal.model.Role;
import tq.tyd.knows.portal.model.User;
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
public interface UserMapper extends BaseMapper<User> {
    @Select("select p.name , p.id from user u left join user_role ur on u.id=ur.user_id\n" +
            "left join role r on r.id=ur.role_id\n" +
            "left join role_permission rp on rp.role_id=r.id\n" +
            "left join permission p on p.id=rp.permission_id\n" +
            "where u.id=#{id}")
    List<Permission> findUserPermissionById(Integer id);
    @Select("select * from user where username=#{username}")
    User findUserByUsername(String username);

    @Select("select * from user where type=1")
    List<User> findTeachers();

    @Select("select r.id , r.name from user u " +
            "left join user_role ur on u.id=ur.user_id\n" +
            "left join role r on r.id=ur.role_id\n" +
            "where u.id=#{id}")
    List<Role> findUserRoleById(Integer userId);
}
