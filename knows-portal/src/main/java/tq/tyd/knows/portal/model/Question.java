package tq.tyd.knows.portal.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDate;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 *
 * </p>
 *
 * @author 糖球
 * @since 2024-06-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("question")
public class Question implements Serializable {

    public static final Integer POSTED=0;
    public static final Integer SOLVING=1;
    public static final Integer SOLVED=2;

    private static final long serialVersionUID = 1L;
    @TableField(exist = false)
    private List<Tag> tags;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 问题的标题
     */
    @TableField("title")
    private String title;

    /**
     * 提问内容
     */
    @TableField("content")
    private String content;

    /**
     * 提问者用户名
     */
    @TableField("user_nick_name")
    private String userNickName;

    /**
     * 提问者id
     */
    @TableField("user_id")
    private Integer userId;

    /**
     * 创建时间
     */
    @TableField("createtime")
    private LocalDateTime createtime;

    /**
     * 状态，0-》未回答，1-》待解决，2-》已解决
     */
    @TableField("status")
    private Integer status;

    /**
     * 浏览量
     */
    @TableField("page_views")
    private Integer pageViews;

    /**
     * 该问题是否公开，所有学生都可见，0-》否，1-》是
     */
    @TableField("public_status")
    private Integer publicStatus;

    @TableField("modifytime")
    private LocalDate modifytime;

    @TableField("delete_status")
    private Integer deleteStatus;

    @TableField("tag_names")
    private String tagNames;


}
