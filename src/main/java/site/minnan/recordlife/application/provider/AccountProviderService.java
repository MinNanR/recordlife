package site.minnan.recordlife.application.provider;

import site.minnan.recordlife.infrastructure.enumerate.TradeDirection;

import java.math.BigDecimal;

public interface AccountProviderService {


    /**
     * 计算账户余额
     *
     * @param accountId 账户id
     * @param amount    金额
     * @param direction 方向
     */
    void settle(Integer accountId, BigDecimal amount, TradeDirection direction);
}
