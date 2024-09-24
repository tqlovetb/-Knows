package tq.tyd.knows.portal.controller;


import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import tq.tyd.knows.portal.exception.ServiceException;
import tq.tyd.knows.portal.mapper.QuestionMapper;
import tq.tyd.knows.portal.model.Question;
import tq.tyd.knows.portal.service.IQuestionService;
import tq.tyd.knows.portal.vo.QuestionVo;

import java.util.List;

import static jdk.nashorn.internal.runtime.regexp.joni.Config.log;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 糖球
 * @since 2024-06-26
 */
@RestController
@RequestMapping("/v1/questions")
@Slf4j
public class QuestionController {
    @Autowired
    IQuestionService iQuestionService;

    /*@AuthenticationPrincipal 注解用于在 Spring Security 中从当前经过身份验证的用户中提取用户信息，并将其注入到控制器的方法参数中。它通常与 UserDetails 一起使用，后者是 Spring Security 中的一个接口，表示用户的详细信息。

@AuthenticationPrincipal 注解的作用
@AuthenticationPrincipal 注解告诉 Spring Security 将当前经过身份验证的用户的 Principal 对象注入到标注了此注解的方法参数中。在 Spring Security 中，Principal 通常是实现了 UserDetails 接口的对象。

如何拿到用户信息的
Spring Security 在用户成功身份验证后，会将用户的信息存储在 SecurityContext 中。这个信息可以通过 SecurityContextHolder 访问。@AuthenticationPrincipal 简化了从 SecurityContext 中获取当前用户信息的过程。*/
    @GetMapping("/my")
    public PageInfo<Question> my(@AuthenticationPrincipal UserDetails user,Integer pageNum){
        Integer pageSize = 8;
        if(pageNum == null){
            pageNum = 1;
        }
        PageInfo<Question> questions = iQuestionService.getQuestions(user.getUsername(),pageNum,pageSize);
        return questions;
    }

    @PostMapping("")
    public String createQuestion(@Validated QuestionVo questionVo, BindingResult result,@AuthenticationPrincipal UserDetails user){
        log.debug("接收表单信息为：{}",questionVo);
        if(result.hasErrors()){
            String msg = result.getFieldError().getDefaultMessage();
            return msg;
        }
        try{
            iQuestionService.saveQuestion(questionVo, user.getUsername());
            return "ok";
        }catch (ServiceException e){
            log.error("新增失败", e);
            return e.getMessage();
        }
    }
    @GetMapping("/teacher")
    @PreAuthorize("hasRole('ROLE_TEACHER')")
    public PageInfo<Question> teacher(
            @AuthenticationPrincipal UserDetails userDetails,
            Integer pageNum
    ){
        Integer pageSize = 8;
        if(pageNum == null){
            pageNum = 1;
        }
        PageInfo<Question> pageInfo = iQuestionService.getTeacherQuestions(userDetails.getUsername(), pageNum, pageSize);
        return pageInfo;
    }
    @GetMapping("/{id}")
    public Question question(@PathVariable Integer id){
        Question q = iQuestionService.getQuestionById(id);
        return q;

    }



}
