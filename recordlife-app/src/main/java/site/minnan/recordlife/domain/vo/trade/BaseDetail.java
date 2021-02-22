package site.minnan.recordlife.domain.vo.trade;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class BaseDetail {

    private String waterName;

    private String waterTime;

    private BigDecimal balance;

    private BigDecimal expend;

    private BigDecimal income;

    private TradeList monthList;
}
