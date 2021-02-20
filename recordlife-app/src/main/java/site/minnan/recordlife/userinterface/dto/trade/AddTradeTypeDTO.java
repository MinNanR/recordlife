package site.minnan.recordlife.userinterface.dto.trade;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class AddTradeTypeDTO {

    private Integer id;

    private String seniorName;

    @NotEmpty(message = "二级分类名称不能为空")
    private String secondName;
}
