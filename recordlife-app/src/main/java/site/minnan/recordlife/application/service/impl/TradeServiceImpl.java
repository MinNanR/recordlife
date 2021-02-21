package site.minnan.recordlife.application.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.minnan.recordlife.application.provider.AccountProviderService;
import site.minnan.recordlife.application.service.AccountService;
import site.minnan.recordlife.application.service.TradeService;
import site.minnan.recordlife.domain.aggregate.Trade;
import site.minnan.recordlife.domain.entity.JwtUser;
import site.minnan.recordlife.domain.entity.TradeInfo;
import site.minnan.recordlife.domain.entity.TradeType;
import site.minnan.recordlife.domain.mapper.TradeMapper;
import site.minnan.recordlife.domain.mapper.TradeTypeMapper;
import site.minnan.recordlife.domain.vo.trade.TradeInfoVO;
import site.minnan.recordlife.domain.vo.trade.TradeList;
import site.minnan.recordlife.infrastructure.exception.EntityNotExistException;
import site.minnan.recordlife.userinterface.dto.trade.AddTradeDTO;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TradeServiceImpl implements TradeService {

    @Autowired
    private TradeMapper tradeMapper;

    @Autowired
    private TradeTypeMapper tradeTypeMapper;

    @Autowired
    private AccountProviderService accountProviderService;

    /**
     * 添加流水记录
     *
     * @param dto
     */
    @Override
    @Transactional
    public void addTrade(AddTradeDTO dto) {
        JwtUser user = (JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Integer secondTypeId = dto.getClassifyId();
        TradeType tradeType = tradeTypeMapper.selectById(secondTypeId);
        if (tradeType == null) {
            throw new EntityNotExistException("分类不存在");
        }
        accountProviderService.settle(dto.getAccountId(), dto.getAmount(), tradeType.getDirection());
        Trade record = Trade.builder()
                .accountId(dto.getAccountId())
                .accountName(dto.getAccountName())
                .firstTypeId(tradeType.getId())
                .secondTypeId(tradeType.getParentId())
                .amount(dto.getAmount())
                .time(new Timestamp(System.currentTimeMillis()))
                .userId(user.getId())
                .direction(tradeType.getDirection())
                .remarks(dto.getRemarks())
                .build();
        tradeMapper.insert(record);
    }

    /**
     * 获取流水列表
     *
     * @return
     */
    @Override
    public List<TradeList> getTradeList() {
        JwtUser user = (JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<TradeInfo> tradeInfoList = tradeMapper.getTradeInfoList(user.getId());
        Map<DateTime, List<TradeInfoVO>> groupByDate = tradeInfoList.stream()
                .map(TradeInfoVO::assemble)
                .collect(Collectors.groupingBy(e -> DateUtil.beginOfDay(e.getTime())));
//        groupByDate.entrySet().stream()
//                .map(entry -> TradeList.dayList(entry.getKey(), entry.getValue()))
//                .collect(Collectors.groupingBy(e -> e.getYearTime(),
//                        Collectors.collectingAndThen(Collectors.toList(), yearList -> {
//                            yearList.stream().collect(Collectors.groupingBy(sub -> sub.getMonthTime(),
//                                    Collectors.collectingAndThen(Collectors.toList(), monthList -> {
//                                        monthList.stream().collect(Collectors.groupingBy(e -> e.getDayTime(),
//                                                Collectors.collectingAndThen()))
//                                    }))
//                        })))


        return null;
    }
}
