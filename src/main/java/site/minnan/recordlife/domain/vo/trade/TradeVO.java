package site.minnan.recordlife.domain.vo.trade;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Console;
import cn.hutool.core.util.ArrayUtil;
import lombok.Builder;
import lombok.Data;
import site.minnan.recordlife.domain.entity.TradeInfo;

import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * 流水列表
 *
 * @author Minnan on 2021/3/1
 */
@Data
@Builder
public class TradeVO {

    private Integer id;

    private String createTime;

    private String seniorTypeName;

    private String secondTypeName;

    private String accountName;

    private BigDecimal amount;

    public static TradeVO assemble(TradeInfo trade) {
        return TradeVO.builder()
                .id(trade.getId())
                .createTime(DateUtil.format(trade.getTime(), "yyyy-MM-dd HH:mm:ss"))
                .seniorTypeName(trade.getFirstTypeName())
                .secondTypeName(trade.getSecondTypeName())
                .accountName(trade.getAccountName())
                .amount(trade.getAmount())
                .build();
    }

    public String[] getFileInfo(Integer ordinal) {
        return new String[]{String.valueOf(ordinal), createTime, seniorTypeName, secondTypeName, accountName,
                amount.toString()};
    }
}
