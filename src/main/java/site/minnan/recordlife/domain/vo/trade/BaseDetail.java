package site.minnan.recordlife.domain.vo.trade;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseDetail {

    private String waterName;

    private String waterTime;

    private BigDecimal balance;

    private BigDecimal expend;

    private BigDecimal income;

    private List<? extends TradeList> monthList;

    private final static Map<DateField, String> WATER_NAME_MAP;

    static {
        WATER_NAME_MAP = new HashMap<>();
        WATER_NAME_MAP.put(DateField.MONTH, "本月");
        WATER_NAME_MAP.put(DateField.DAY_OF_WEEK, "本周");
        WATER_NAME_MAP.put(DateField.DAY_OF_MONTH, "当天");
        WATER_NAME_MAP.put(DateField.YEAR, "本年");
    }

    /**
     * 拼装年度数据
     *
     * @param balance   余额
     * @param monthList 月度信息
     * @return
     */
    public static BaseDetail assemble(BigDecimal balance, List<? extends TradeList> monthList) {
        DateTime endTime = DateTime.now();
        DateTime startTime = DateUtil.beginOfYear(endTime);
        return BaseDetail.builder()
                .waterName(WATER_NAME_MAP.get(DateField.YEAR))
                .waterTime(StrUtil.format("{}-{}", startTime.toString("M.d"), endTime.toString("M.d")))
                .balance(balance)
                .monthList(monthList)
                .build();
    }

    /**
     * 拼装日/周/月信息
     *
     * @param mode      日/周/月
     * @param startTime 流水开始时间
     * @param endTime   流水结束时间
     * @param income    总收入
     * @param expend    总支出
     * @param monthList 每日详情
     * @return
     */
    public static BaseDetail assemble(DateField mode, Date startTime, Date endTime, BigDecimal income,
                                      BigDecimal expend, List<TradeList> monthList) {
        return BaseDetail.builder()
                .waterName(WATER_NAME_MAP.get(mode))
                .waterTime(StrUtil.format("{}-{}", DateUtil.format(startTime, "M.d"),
                        DateUtil.format(endTime, "M.d")))
                .income(income)
                .expend(expend)
                .balance(income.subtract(expend))
                .monthList(monthList)
                .build();
    }
}
