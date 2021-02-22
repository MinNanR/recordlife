package site.minnan.recordlife.application.service;

import cn.hutool.core.date.DateField;
import site.minnan.recordlife.domain.vo.trade.BaseDetail;
import site.minnan.recordlife.domain.vo.ListQueryVO;
import site.minnan.recordlife.domain.vo.trade.BaseInfoVO;
import site.minnan.recordlife.domain.vo.trade.DataChart;
import site.minnan.recordlife.domain.vo.trade.TradeList;
import site.minnan.recordlife.userinterface.dto.GetTradeRecordDTO;
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

    /**
     * 获取流水列表
     *
     * @param dto
     * @return
     */
    ListQueryVO<TradeList> getTradeList(GetTradeRecordDTO dto);

    /**
     * 获取图表数据
     *
     * @return
     */
    DataChart getDateChart();

    /**
     * 获取首页数据
     *
     * @return
     */
    BaseInfoVO getBaseInfo();

    /**
     * (今天、本周、本月)详情查询接口
     * @param dateField
     * @return
     */
    BaseDetail getBaseDetail(DateField dateField);
}
