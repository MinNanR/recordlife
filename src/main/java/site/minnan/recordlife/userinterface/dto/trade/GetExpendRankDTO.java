package site.minnan.recordlife.userinterface.dto.trade;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * 支出排行参数
 * @author Minnan on 2021/03/03
 */
@Data
public class GetExpendRankDTO {

    @NotEmpty(message = "开始时间不能为空")
    private String timeBegin;

    @NotEmpty(message = "结束时间不能为空 ")
    private String timeEnd;
}
