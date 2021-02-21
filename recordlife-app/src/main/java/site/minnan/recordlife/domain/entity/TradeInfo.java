package site.minnan.recordlife.domain.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import site.minnan.recordlife.domain.aggregate.Trade;

@Data
public class TradeInfo extends Trade {

    private String secondTypeName;
}
