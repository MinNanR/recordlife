package site.minnan.recordlife.domain.entity;

import lombok.Data;
import site.minnan.recordlife.domain.aggregate.Trade;

@Data
public class TradeInfo extends Trade {

    private String secondTypeName;

    private String firstTypeName;

    public void setTradeType(TradeType tradeType){
        this.firstTypeName = tradeType.getParentName();
        this.secondTypeName = tradeType.getName();
    }
}
