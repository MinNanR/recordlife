package site.minnan.recordlife.userinterface.dto.trade;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/***
 * 添加流水记录参数
 * @author Minnan on 2021/2/20
 */
@Data
public class AddTradeDTO {

    @NotNull(message = "金额不能为空")
    private BigDecimal amount;

    @NotNull(message = "未指定分类")
    private Integer classifyId;

    @NotNull(message = "未指定账户")
    private Integer accountId;

    @NotEmpty(message = "未指定账户")
    private String accountName;

    private String remarks;
}
