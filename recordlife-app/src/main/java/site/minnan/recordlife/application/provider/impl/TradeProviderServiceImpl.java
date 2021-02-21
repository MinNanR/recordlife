package site.minnan.recordlife.application.provider.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import site.minnan.recordlife.application.provider.TradeProviderService;
import site.minnan.recordlife.domain.mapper.TradeMapper;
import site.minnan.recordlife.domain.vo.account.CheckAccountResult;

import java.util.List;

@Service
public class TradeProviderServiceImpl implements TradeProviderService {

    @Autowired
    private TradeMapper tradeMapper;

    /**
     * 获取正在使用的账户
     *
     * @param userId 用户id
     * @return
     */
    @Override
    public List<CheckAccountResult> getUsingAccount(Integer userId) {
        return tradeMapper.getUsingAccount(userId);
    }
}
