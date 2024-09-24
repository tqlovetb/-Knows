package tq.tyd.knows.sys.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/*Java中，一个实体类实现Serializable接口是为了使其能够被序列化。序列化的作用是将对象的状态转换为一个字节流，以便能够将对象写入文件、通过网络传输或存储在数据库中。反序列化则是将字节流恢复为对象的过程。

实现Serializable接口的几个主要原因如下：

持久化：将对象的状态保存到文件中，以便以后可以恢复。
远程调用：在分布式系统中，可以通过网络传输对象，以进行远程方法调用（RMI）。
缓存：将对象存储在缓存中，以便后续快速访问。
深度复制：通过序列化和反序列化可以实现对象的深度复制。
在你的RegisterVo类中，添加Serializable接口的目的是确保该类的实例可以被序列化和反序列化，从而满足上述需求。例如，如果你需要将注册信息通过网络传输给其他服务或将其持久化到文件系统中，序列化是必要的。*/
@Data
public class RegisterVo implements Serializable {
    @NotBlank(message = "邀请码不能为空")
    private String inviteCode;          // 邀请码
    @NotBlank(message = "手机号不能为空")
    @Pattern(regexp = "^.{2,20}$", message = "手机号必须是11位")
    private String phone;               // 手机号\用户名
    @NotBlank(message = "昵称不能为空")
    private String nickname;            // 昵称
    @NotBlank(message = "密码不能为空")
    private String password;            // 密码
    @NotBlank(message = "确认密码不能为空")
    private String confirm;             // 确认密码
}
