package tq.tyd.knows.serach.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import tq.tyd.knows.commons.model.Tag;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author 糖球
 * @since 2024-06-26
 */
@Data
/* @EqualsAndHashCode(callSuper = false)
这个注解来自Lombok库，用于生成equals和hashCode方法。

作用：自动生成equals和hashCode方法，避免手动编写。
callSuper：如果设置为true，则生成的方法会调用父类的equals和hashCode方法。如果设置为false（默认），则仅使用当前类的字段。*/
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
/*@Document注解是Spring Data Elasticsearch中的一个注解，用于标识一个类是Elasticsearch索引中的一个文档。*/
@Document(indexName = "knows")
public class QuestionVo implements Serializable {

    public static final Integer POSTED=0;
    public static final Integer SOLVING=1;
    public static final Integer SOLVED=2;

    private static final long serialVersionUID = 1L;
    @Transient
    private List<Tag> tags;

    @Id
    private Integer id;

    /**
     * 问题的标题
     */
    /*analyzer和searchAnalyzer属性指定了存储和搜索时使用的分词器，ik_max_word表示使用细粒度分词器进行处理。*/
    @Field(type = FieldType.Text,analyzer = "ik_max_word",searchAnalyzer = "ik_max_word")
    private String title;

    /**
     * 提问内容
     */
    @Field(type = FieldType.Text,analyzer = "ik_max_word",searchAnalyzer = "ik_max_word")
    private String content;

    /**
     * 提问者用户名
     */
    @Field(type = FieldType.Keyword)
    private String userNickName;

    /**
     * 提问者id
     */
    @Field(type = FieldType.Integer)
    private Integer userId;

    /**
     * 创建时间
     */
    @Field(type = FieldType.Date,format = DateFormat.basic_date_time)
    private LocalDateTime createtime;

    /**
     * 状态，0-》未回答，1-》待解决，2-》已解决
     */
    @Field(type = FieldType.Integer)
    private Integer status;

    /**
     * 浏览量
     */
    @Field(type = FieldType.Integer)
    private Integer pageViews;

    /**
     * 该问题是否公开，所有学生都可见，0-》否，1-》是
     */
    @Field(type = FieldType.Integer)
    private Integer publicStatus;

    @Field(type = FieldType.Date,format = DateFormat.basic_date_time)
    private LocalDate modifytime;

    @Field(type = FieldType.Integer)
    private Integer deleteStatus;

    @Field(type = FieldType.Keyword)
    private String tagNames;


}
