package site.minnan.recordlife.userinterface.dto.trade;

import lombok.Data;
import site.minnan.recordlife.userinterface.dto.ListQueryDTO;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * 获取流水列表参数
 * @author Minnan on 2021/3/1
 */
@Data
public class GetTradeListDTO extends ListQueryDTO {

    @NotEmpty(message = "起始时间不能为空")
    private String timeBegin;

    @NotEmpty(message = "结束时间不能为空")
    private String timeEnd;

    private Integer accountId;

    private Integer seniorTypeId;
}
