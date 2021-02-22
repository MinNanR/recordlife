package site.minnan.recordlife.domain.vo.trade;

import cn.hutool.core.collection.CollectionUtil;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Comparator;
import java.util.Date;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TradeList {

    private String dayId;

    @JsonFormat(pattern = "d", timezone = "GMT+08:00")
    private Date dayTime;

    private List<TradeInfoVO> billList;

    private String monthId;

    @JsonFormat(pattern = "M", timezone = "GMT+08:00")
    private Date monthTime;

    private List<TradeList> dayList;

    private String yearId;

    @JsonFormat(pattern = "yyyy", timezone = "GMT+08:00")
    private Date yearTime;

    private List<TradeList> monthList;

    public static TradeList dayList(Date dayTime, List<TradeInfoVO> billList) {
        TradeList tradeList = new TradeList();
        tradeList.dayTime = dayTime;
        tradeList.billList = CollectionUtil.sort(billList, Comparator.comparing(TradeInfoVO::getTime));
        CollectionUtil.reverse(billList);
        tradeList.dayId = String.valueOf(tradeList.hashCode());
        return tradeList;
    }

    public static TradeList monthList(Date monthTime, List<TradeList> dayList) {
        TradeList tradeList = new TradeList();
        tradeList.monthTime = monthTime;
        tradeList.dayList = CollectionUtil.sort(dayList, Comparator.comparing(TradeList::getDayTime));
        CollectionUtil.reverse(dayList);
        tradeList.monthId = String.valueOf(tradeList.hashCode());
        return tradeList;
    }

    public static TradeList yearList(Date yearTime, List<TradeList> monthList) {
        TradeList tradeList = new TradeList();
        tradeList.yearTime = yearTime;
        tradeList.monthList = CollectionUtil.sort(monthList, Comparator.comparing(TradeList::getMonthTime));
        CollectionUtil.reverse(monthList);
        tradeList.yearId = String.valueOf(tradeList.hashCode());
        return tradeList;
    }
}
