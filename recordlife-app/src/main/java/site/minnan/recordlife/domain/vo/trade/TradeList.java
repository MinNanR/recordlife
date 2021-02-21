package site.minnan.recordlife.domain.vo.trade;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.sql.Time;
import java.util.Date;
import java.util.List;

/**
 * @author Minnan on 2021/02/21
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TradeList {

    private Integer id;

    private String yearTime;

    private String monthTime;

    private String dayTime;

    private List<TradeList> monthList;

    private List<TradeList> dayList;

    private List<TradeInfoVO> billList;

    public static TradeList yearList(List<TradeList> monthList){
        TradeList tradeList = new TradeList();
        tradeList.monthList = monthList;
        tradeList.id = tradeList.hashCode();
        return tradeList;
    }

    public static TradeList monthList(Integer month, List<TradeList> dayList){
        TradeList tradeList = new TradeList();
        tradeList.dayList = dayList;
        tradeList.id = tradeList.hashCode();
        return tradeList;
    }

    public static TradeList dayList(Date time, List<TradeInfoVO> billList){
        TradeList tradeList = new TradeList();
        DateTime dateTime = DateTime.of(time);
        tradeList.dayTime = String.valueOf(dateTime.dayOfMonth());
        tradeList.monthTime = String.valueOf(dateTime.monthBaseOne());
        tradeList.yearTime = String.valueOf(dateTime.year());
        tradeList.billList = billList;
        tradeList.id = tradeList.hashCode();
        return tradeList;
    }

}
