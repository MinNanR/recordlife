package site.minnan.recordlife.domain.vo.trade;

import lombok.Builder;
import lombok.Data;
import site.minnan.recordlife.domain.entity.TradeInfo;

import java.math.BigDecimal;

/**
 * 支出排行展示
 * @author Minnan on 2021/3/3
 */
@Data
@Builder
public class RankVO {

    private Integer id;

    private Integer rankNum;

    private String expendType;

    private BigDecimal expendAmount;

    public static RankVO of(Integer id, String typeName, BigDecimal amount){
        return RankVO.builder()
                .id(id)
                .expendType(typeName)
                .expendAmount(amount)
                .build();
    }
}
