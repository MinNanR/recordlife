package site.minnan.recordlife.infrastructure.enumerate;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import site.minnan.recordlife.domain.aggregate.Account;
import site.minnan.recordlife.infrastructure.strategy.DirectionStrategy;

import java.math.BigDecimal;

public enum  TradeDirection implements DirectionStrategy {

    EXPEND("EXPEND", "支出"){
        /**
         * 计算账户余额
         *
         * @param account 账户
         * @param amount  数量
         */
        @Override
        public void settle(Account account, BigDecimal amount) {
            account.addExpend(amount);
        }
    },

    INCOME("INCOME","收入"){
        /**
         * 计算账户余额
         *
         * @param account 账户
         * @param amount  数量
         */
        @Override
        public void settle(Account account, BigDecimal amount) {
            account.addIncome(amount);
        }
    };

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
