package tq.tyd.knows.portal.vo;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Data
@Slf4j
public class QuestionVo {
    @NotBlank(message = "标题不能为空")
    @Pattern(regexp = "^.{3,50}$",message="标题需要3~50个字符")
    private String title;
    @NotEmpty(message="至少选择一个标签")
    private String[] tagNames={};
    @NotEmpty(message="至少选择一个讲师")
    private String[] teacherNicknames={};
    @NotBlank(message = "问题内容不能为空")
    private String content;

}
