package tq.tyd.knows.faq.service;


import com.baomidou.mybatisplus.extension.service.IService;
import tq.tyd.knows.commons.model.Comment;
import tq.tyd.knows.faq.vo.CommentVo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 糖球
 * @since 2024-06-26
 */
public interface ICommentService extends IService<Comment> {

    Comment saveComment(CommentVo commentVo,String username);
    boolean removeComment(Integer commentId,String username);
    Comment updateComment(CommentVo commentVo, Integer commentId, String username);
}
