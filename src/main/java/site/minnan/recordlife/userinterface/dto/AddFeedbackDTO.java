package site.minnan.recordlife.userinterface.dto;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class AddFeedbackDTO {

    @NotEmpty(message = "标题不能为空")
    private String title;

    @NotEmpty(message = "原因不能为空")
    private String reason;

    @NotNull(message = "评分不能为空")
    @Max(5)
    @Min(1)
    private Integer score;

    private String imgUrl;

    private String opinion;
}
