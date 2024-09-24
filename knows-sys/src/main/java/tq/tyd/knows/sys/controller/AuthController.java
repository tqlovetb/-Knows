package tq.tyd.knows.sys.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tq.tyd.knows.commons.model.Permission;
import tq.tyd.knows.commons.model.Role;
import tq.tyd.knows.commons.model.User;
import tq.tyd.knows.sys.service.IUserService;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/v1/auth")
public class AuthController {
    @Resource
    IUserService userService;
    @GetMapping("/demo")
    public String demo(){
        System.out.println("demo方法运行");
        return "controller demo";
    }
    @GetMapping("/user")
    public User getUser(String username){
        return userService.getUserByUsername(username);
    }
    @GetMapping("/permissions")
    public List<Permission> permissions(Integer id){
        return userService.getPermissionById(id);
    }
    @GetMapping("/roles")
    public List<Role> roles(Integer id){
        return userService.getRolesById(id);
    }
}
