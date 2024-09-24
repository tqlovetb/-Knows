package tq.tyd.knows.faq.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import tq.tyd.knows.commons.exception.ServiceException;
import tq.tyd.knows.commons.model.Answer;
import tq.tyd.knows.faq.service.IAnswerService;
import tq.tyd.knows.faq.vo.AnswerVo;

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
@RequestMapping("/v2/answers")
@Slf4j
public class AnswerController {

    @Autowired
    IAnswerService answerService;
    @PostMapping("")
    @PreAuthorize("hasAuthority('/question/answer')")
    public Answer postAnswer(@Validated AnswerVo answerVo, BindingResult result, @AuthenticationPrincipal UserDetails userDetails){
        log.debug("表单信息:{}",answerVo);
        if(result.hasErrors()){
            String msg = result.getFieldError().getDefaultMessage();
            throw new ServiceException(msg);
        }
        Answer answer = answerService.saveAnswer(answerVo, userDetails.getUsername());
        return answer;
    }

    @GetMapping("/question/{id}")
    public List<Answer> questionAnswers(@PathVariable Integer id){
        List<Answer> answers = answerService.getAnswerByQuestionId(id);
        return answers;
    }
    @GetMapping("/{answerId}/solved")
    public String solved(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Integer answerId){
        boolean acceptd = answerService.accept(answerId, userDetails.getUsername());
        if(acceptd){
            return "采纳完成";
        }else{
            return "您不能采纳别人的问题";
        }
    }

}
