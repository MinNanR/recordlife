package site.minnan.recordlife.application.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import site.minnan.recordlife.application.service.TradeService;
import site.minnan.recordlife.domain.aggregate.Trade;
import site.minnan.recordlife.domain.entity.JwtUser;
import site.minnan.recordlife.domain.mapper.TradeMapper;
import site.minnan.recordlife.userinterface.dto.trade.AddTradeDTO;

@Service
public class TradeServiceImpl implements TradeService {

    @Autowired
    private TradeMapper tradeMapper;

    /**
     * 添加流水记录
     *
     * @param dto
     */
    @Override
    public void addTrade(AddTradeDTO dto) {

    }
}
