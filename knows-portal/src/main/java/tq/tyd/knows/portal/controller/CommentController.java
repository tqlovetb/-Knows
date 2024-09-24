package tq.tyd.knows.portal.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import tq.tyd.knows.portal.exception.ServiceException;
import tq.tyd.knows.portal.mapper.CommentMapper;
import tq.tyd.knows.portal.model.Comment;
import tq.tyd.knows.portal.service.ICommentService;
import tq.tyd.knows.portal.vo.CommentVo;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 糖球
 * @since 2024-06-26
 */
@RestController
@RequestMapping("/v1/comments")
@Slf4j
public class CommentController {
    @Autowired
    ICommentService commentService;

    @PostMapping
    public Comment postComment(@AuthenticationPrincipal UserDetails userDetails, @Validated CommentVo commentVo, BindingResult result){
        log.debug("接收到的表单信息:{}", commentVo);
        if(result.hasErrors()){
            String msg = result.getFieldError().getDefaultMessage();
            throw new ServiceException(msg);
        }

        return commentService.saveComment(commentVo,userDetails.getUsername());

    }
    @GetMapping("/{id}/delete")
    public String removeComment(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Integer id){
        boolean isDelete = commentService.removeComment(id, userDetails.getUsername());
        if(isDelete){
            return "ok";
        }else{
            return "fail";
        }

    }
    @PostMapping("/{id}/update")
    public Comment updateComment(@AuthenticationPrincipal UserDetails userDetails,@Validated CommentVo commentVo, BindingResult result,@PathVariable Integer id){
        log.debug("接收到的表单信息:{}", commentVo.getContent());
        log.debug("要修改的评论id:{}", id);
        if(result.hasErrors()){
//            你可以通过 result.getFieldError() 获取第一个字段错误，然后通过 getDefaultMessage() 方法获取错误消息。
            String msg = result.getFieldError().getDefaultMessage();
            throw new ServiceException(msg);
        }
        Comment comment = commentService.updateComment(commentVo, id, userDetails.getUsername());
        return comment;

    }


}
