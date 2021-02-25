package site.minnan.recordlife.userinterface.dto.trade;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * 添加分类参数
 * @author Minnan on 2021/2/24
 */
@Data
public class AddTradeTypeDTO {

    private Integer id;

    private String seniorName;

    @NotEmpty(message = "二级分类名称不能为空")
    private String secondName;
}
