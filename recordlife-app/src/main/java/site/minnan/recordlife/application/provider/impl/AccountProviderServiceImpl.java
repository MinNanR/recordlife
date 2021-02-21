package site.minnan.recordlife.application.provider.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.minnan.recordlife.application.provider.AccountProviderService;
import site.minnan.recordlife.domain.aggregate.Account;
import site.minnan.recordlife.domain.mapper.AccountMapper;
import site.minnan.recordlife.infrastructure.enumerate.TradeDirection;
import site.minnan.recordlife.infrastructure.exception.EntityNotExistException;

import java.math.BigDecimal;

@Service
public class AccountProviderServiceImpl implements AccountProviderService {

    @Autowired
    private AccountMapper accountMapper;

    /**
     * 计算账户余额
     *
     * @param accountId 账户id
     * @param amount    金额
     * @param direction 方向
     */
    @Override
    @Transactional
    public void settle(Integer accountId, BigDecimal amount, TradeDirection direction) {
        Account account = accountMapper.selectById(accountId);
        if (account == null) {
            throw new EntityNotExistException("账户不存在");
        }
        direction.settle(account, amount);
        accountMapper.updateById(account);
    }
}
