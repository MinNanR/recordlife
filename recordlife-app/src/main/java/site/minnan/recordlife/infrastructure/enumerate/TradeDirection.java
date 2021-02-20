package site.minnan.recordlife.infrastructure.enumerate;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;

/***
 * 流水记录方向
 * @author Minnan on 2021/2/20
 */
public enum TradeDirection {

    EXPEND("EXPEND", "支出"),
    INCOME("INCOME","收入");

    TradeDirection(String direction, String value){
        this.direction = direction;
        this.value = value;
    }

    @EnumValue
    private final String direction;

    @JsonValue
    private final String value;

    public String getDirection() {
        return direction;
    }

    public String getValue() {
        return value;
    }
}
