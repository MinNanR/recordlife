package site.minnan.recordlife.userinterface.dto.trade;

import lombok.Data;

import java.math.BigDecimal;

/***
 * 添加流水记录参数
 * @author Minnan on 2021/2/20
 */
@Data
public class AddTradeDTO {

    private BigDecimal amount;

    private Integer classifyId;

    private Integer accountId;

    private String accountName;

    private String remarks;
}
