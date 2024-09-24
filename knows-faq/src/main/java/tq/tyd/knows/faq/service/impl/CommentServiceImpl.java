package tq.tyd.knows.faq.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tq.tyd.knows.commons.exception.ServiceException;
import tq.tyd.knows.commons.model.Comment;
import tq.tyd.knows.commons.model.User;
import tq.tyd.knows.faq.mapper.CommentMapper;
import tq.tyd.knows.faq.service.ICommentService;
import tq.tyd.knows.faq.vo.CommentVo;

import javax.annotation.Resource;
import java.time.LocalDateTime;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 糖球
 * @since 2024-06-26
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements ICommentService {

    @Resource
    RibbonClient ribbonClient;
    @Autowired
    CommentMapper commentMapper;
    @Override
    public Comment saveComment(CommentVo commentVo, String username) {
        User user = ribbonClient.getUser(username);
        Comment comment = new Comment()
                .setUserId(user.getId())
                .setUserNickName(user.getNickname())
                .setAnswerId(commentVo.getAnswerId())
                .setContent(commentVo.getContent())
                .setCreatetime(LocalDateTime.now());
        int num = commentMapper.insert(comment);
        if(num!=1){
            throw new ServiceException("数据库异常");
        }
        return comment;
    }

    @Override
    public boolean removeComment(Integer commentId, String username) {
        User user = ribbonClient.getUser(username);
        if(user.getType().equals(1)){
            int num = commentMapper.deleteById(commentId);
            return  num == 1;
        }
        Comment comment = commentMapper.selectById(commentId);
        if(comment.getUserId().equals(user.getId())){
            int num = commentMapper.deleteById(commentId);
            return num == 1;
        }
        throw new ServiceException("您不能删除别人的评论！");
    }

    @Override
    @Transactional
    public Comment updateComment(CommentVo commentVo, Integer commentId, String username) {
        User user = ribbonClient.getUser(username);
        Comment comment = commentMapper.selectById(commentId);
        if(user.getType().equals(1)||comment.getUserId().equals(user.getId())){
            log.debug(commentVo.getContent());
            comment.setContent(commentVo.getContent());
            int num = commentMapper.updateById(comment);
            if(num!=1){
                throw new ServiceException("数据库忙！");
            }
            return comment;
        }
        throw new ServiceException("您不能修改别人的评论");
    }
}
