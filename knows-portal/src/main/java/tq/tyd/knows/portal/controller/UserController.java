package tq.tyd.knows.portal.controller;


import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import tq.tyd.knows.portal.model.User;
import tq.tyd.knows.portal.service.IUserService;
import tq.tyd.knows.portal.vo.UserVo;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 糖球
 * @since 2024-06-26
 */
@RestController
@RequestMapping("/v1/users")
public class UserController {
    @Autowired
    private IUserService userService;

    @GetMapping("/demo")
    public String demo(){
        return "hello demo!";
    }
    @GetMapping("/ask")
    @PreAuthorize("hasAuthority('answer')")
    public String ask(){
        return "可以回答问题";
    }
    @GetMapping("/delete")
    @PreAuthorize("hasAuthority('remove')")
    public String delete(){
        return "可以删除";
    }

    @GetMapping("/master")
    public List<User> master(){
        List<User> users = userService.getTeachers();
        return users;
    }
    @GetMapping("/me")
    public UserVo me(@AuthenticationPrincipal UserDetails user){
        UserVo userVo = userService.getUserVo(user.getUsername());
        return userVo;
    }
}
