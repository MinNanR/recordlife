package site.minnan.recordlife.application.service;

import site.minnan.recordlife.domain.vo.ListQueryVO;
import site.minnan.recordlife.domain.vo.tradetype.TradeTypeBox;
import site.minnan.recordlife.domain.vo.tradetype.TradeTypeVO;
import site.minnan.recordlife.userinterface.dto.trade.AddTradeTypeDTO;
import site.minnan.recordlife.userinterface.dto.trade.GetTradeTypeDTO;

import java.util.List;

/**
 * @author Minnan on 2021/2/20
 */
public interface TradeTypeService {

    /**
     * 添加支出分类
     *
     * @param dto
     */
    void addExpendType(AddTradeTypeDTO dto);

    /**
     * 添加收入分类
     *
     * @param dto
     */
    void addIncomeType(AddTradeTypeDTO dto);

    /**
     * 获取流水类型列表
     *
     * @param dto
     * @return
     */
    List<TradeTypeVO> getTradeTypeList(GetTradeTypeDTO dto);

    /**
     * 获取流水类型下拉框
     *
     * @param dto
     * @return
     */
    ListQueryVO<TradeTypeBox> getTradeTypeBox(GetTradeTypeDTO dto);
}
