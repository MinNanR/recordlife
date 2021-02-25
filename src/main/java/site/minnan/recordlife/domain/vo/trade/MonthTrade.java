package site.minnan.recordlife.domain.vo.trade;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MonthTrade extends TradeList {

    private String id;

    @JsonFormat(pattern = "M月", timezone = "GMT+08:00")
    private Date monthTime;

    private BigDecimal monthBalance;

    private BigDecimal monthIncome;

    private BigDecimal monthExpend;

    public static MonthTrade monthList(Date monthTime, BigDecimal income, BigDecimal expend){
        MonthTrade monthTrade = new MonthTrade();
        monthTrade.monthTime = monthTime;
        monthTrade.monthIncome = income;
        monthTrade.monthExpend = expend;
        monthTrade.monthBalance = income.subtract(expend);
        monthTrade.id = String.valueOf(monthTrade.hashCode());
        return monthTrade;
    }
}
