package site.minnan.recordlife.domain.vo.trade;

import cn.hutool.core.date.DateUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import site.minnan.recordlife.domain.entity.TradeInfo;

import java.sql.Timestamp;
import java.util.Optional;

/**
 * 流水记录显示
 *
 * @author Minnan on 2021/02/21
 */
@Data
@AllArgsConstructor
@Builder
public class TradeInfoVO {

    private Integer id;

    private String classifyName;

    private String direction;

    private String tradeTime;

    @JsonIgnore
    private Timestamp time;

    private String accountName;

    private String amounts;

    private String remark;

    public static TradeInfoVO assemble(TradeInfo trade){
        return TradeInfoVO.builder()
                .id(trade.getId())
                .classifyName(trade.getSecondTypeName())
                .direction(trade.getDirection().getValue())
                .tradeTime(DateUtil.format(trade.getTime(), "HH:mm"))
                .accountName(trade.getAccountName())
                .amounts(trade.getAmount().toString())
                .time(trade.getTime())
                .remark(Optional.of(trade.getRemarks()).orElse(""))
                .build();
    }
}
