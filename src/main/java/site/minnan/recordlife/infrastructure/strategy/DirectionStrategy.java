package site.minnan.recordlife.infrastructure.strategy;

import site.minnan.recordlife.domain.aggregate.Account;

import java.math.BigDecimal;

/**
 * 支出与收入的结算策略
 */
public interface DirectionStrategy {

    /**
     * 计算账户余额
     * @param account 账户
     * @param amount 数量
     */
    void settle(Account account, BigDecimal amount);
}
