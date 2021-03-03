package site.minnan.recordlife.application.service;

import site.minnan.recordlife.domain.vo.ListQueryVO;
import site.minnan.recordlife.domain.vo.trade.*;
import site.minnan.recordlife.infrastructure.enumerate.TradeDirection;
import site.minnan.recordlife.userinterface.dto.trade.*;

import java.io.OutputStream;

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
     *
     * @param dto@return
     */
    BaseDetail getBaseDetail(GetBaseDetailDTO dto);

    /**
     * 获取流水列表（PC端显示）
     * @param dto
     * @return
     */
    ListQueryVO<TradeVO> getTradeList(GetTradeListDTO dto, TradeDirection direction);

    /**
     * 下载流水列表（PC端）
     * @param dto
     * @param direction
     * @param outputStream
     */
    void downloadTrade(GetTradeListDTO dto, TradeDirection direction, OutputStream outputStream);

    /**
     * 获取支出排行
     * @param dto
     * @return
     */
    ListQueryVO<RankVO> getExpendRankList(GetExpendRankDTO dto);
}
