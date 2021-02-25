package site.minnan.recordlife.application.provider;

import site.minnan.recordlife.domain.vo.account.CheckAccountResult;

import java.util.List;

public interface TradeProviderService {

    /**
     * 获取正在使用的账户
     * @param userId 用户id
     * @return
     */
    List<CheckAccountResult> getUsingAccount(Integer userId);
}
