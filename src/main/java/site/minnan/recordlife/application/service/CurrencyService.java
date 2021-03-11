package site.minnan.recordlife.application.service;

import site.minnan.recordlife.domain.vo.ListQueryVO;
import site.minnan.recordlife.domain.vo.currency.CurrencyVO;
import site.minnan.recordlife.userinterface.dto.ListQueryDTO;

/***
 * 货币相关服务
 * @author Minnan on 2021/3/11
 */
public interface CurrencyService {

    /**
     * 刷新汇率
     */
    void refreshRate();

    /**
     * 获取货币信息列表
     * @param dto
     * @return
     */
    ListQueryVO<CurrencyVO> getCurrencyList(ListQueryDTO dto);
}
