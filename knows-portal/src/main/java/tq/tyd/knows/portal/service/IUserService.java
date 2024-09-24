package tq.tyd.knows.portal.service;

import tq.tyd.knows.portal.model.User;
import com.baomidou.mybatisplus.extension.service.IService;
import tq.tyd.knows.portal.vo.RegisterVo;
import tq.tyd.knows.portal.vo.UserVo;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 糖球
 * @since 2024-06-26
 */
/*简化CRUD操作：
继承IService接口可以自动获得通用的CRUD方法，如save(), update(), remove(), getById()等。这样你不需要在每个服务类中手动编写这些方法。*/
    /*User：

User是一个持久化实体类，通常映射到数据库中的一张表，代表用户的数据结构。
继承IService<User>意味着你正在使用MyBatis-Plus提供的通用服务接口，它为User实体提供了一系列通用的CRUD操作，如保存、更新、删除和查询。
RegisterVo：

RegisterVo是一个值对象（Value Object），通常用于封装从前端接收到的数据，或者在业务逻辑层中传递数据。
RegisterVo可以包含与User相关的信息，但可能还包括其他不直接存储在User表中的信息，如确认密码、邀请码等。
为什么要区分User和RegisterVo？
区分实体类和值对象的做法有助于分离关注点，保持代码的清晰和模块化：

实体类（User）：

用于持久化数据，通常直接与数据库表结构对应。
包含数据库中的字段和可能的一些业务逻辑方法。
值对象（RegisterVo）：

用于封装业务逻辑层和表示层之间的数据传输。
通常包含用于处理特定业务操作的字段，例如注册操作可能需要的额外字段（如确认密码）。*/
public interface IUserService extends IService<User> {
    void registerStudent(RegisterVo registerVo);

    List<User> getTeachers();
    Map<String,User> getTeacherMap();
    UserVo getUserVo(String username);
}
