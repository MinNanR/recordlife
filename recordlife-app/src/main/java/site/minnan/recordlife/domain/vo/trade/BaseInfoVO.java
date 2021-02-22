package site.minnan.recordlife.domain.vo.trade;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import lombok.Data;
import lombok.Getter;
import site.minnan.recordlife.domain.entity.TradeInfo;

import java.math.BigDecimal;

/**
 * 首页数据
 *
 * @author Minnan on 2021/2/22
 */
@Getter
public class BaseInfoVO {

    private String yearly;

    private String monthBegin;

    private String monthEnd;

    private String weekBegin;

    private String weekEnd;

    private String recentType;

    private BigDecimal recentAmount;

    private BigDecimal dayExpend;

    private BigDecimal dayIncome;

    private BigDecimal weekExpend;

    private BigDecimal weekIncome;

    private BigDecimal monthExpend;

    private BigDecimal monthIncome;

    private BigDecimal yearExpend;

    private BigDecimal yearIncome;

    public BaseInfoVO() {
        DateTime now = DateTime.now();
        this.yearly = String.valueOf(now.year());
        this.monthBegin = DateUtil.format(DateUtil.beginOfMonth(now), "M.d");
        this.monthEnd = DateUtil.format(DateUtil.endOfMonth(now), "M.d");
        this.weekBegin = DateUtil.format(DateUtil.beginOfWeek(now), "M.d");
        this.weekEnd = DateUtil.format(DateUtil.endOfWeek(now), "M.d");
    }

    public void setRecentTrade(TradeInfo trade) {
        this.recentType = trade.getSecondTypeName();
        this.recentAmount = trade.getAmount();
    }

    public void setDailyInfo(BigDecimal income, BigDecimal expend) {
        this.dayExpend = expend;
        this.dayIncome = income;
    }

    public void setWeeklyInfo(BigDecimal income, BigDecimal expend) {
        this.weekExpend = expend;
        this.weekIncome = income;
    }

    public void setMonthlyInfo(BigDecimal income, BigDecimal expend) {
        this.monthExpend = expend;
        this.monthIncome = income;
    }

    public void setYearlyInfo(BigDecimal income, BigDecimal expend) {
        this.yearExpend = expend;
        this.yearIncome = income;
    }

}
