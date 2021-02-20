package site.minnan.recordlife.application.service;

import site.minnan.recordlife.userinterface.dto.trade.AddTradeDTO;

/***
 * 流水相关操作
 * @author Minnan on 2021/2/20
 */
public interface TradeService {

    /**
     * 添加流水记录
     *
     * @param dto
     */
    void addTrade(AddTradeDTO dto);
}
