package tq.tyd.knows.sys.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tq.tyd.knows.commons.exception.ServiceException;
import tq.tyd.knows.commons.model.User;
import tq.tyd.knows.sys.service.IUserService;
import tq.tyd.knows.sys.vo.RegisterVo;
import tq.tyd.knows.sys.vo.UserVo;

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
@Slf4j
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
    @PostMapping("/register")
    public String register(@Validated RegisterVo registerVo, BindingResult result){
        log.debug("接收到表单信息:{}",registerVo);
        if(result.hasErrors()){
            String msg = result.getFieldError().getDefaultMessage();
            return msg;
        }
        try{
            userService.registerStudent(registerVo);
            return "ok";
        }catch (ServiceException e){
            log.error("注册失败", e);
            return e.getMessage();
        }
    }
}
