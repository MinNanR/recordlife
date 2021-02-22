package site.minnan.recordlife.application.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.minnan.recordlife.application.provider.AccountProviderService;
import site.minnan.recordlife.application.service.TradeService;
import site.minnan.recordlife.domain.aggregate.Trade;
import site.minnan.recordlife.domain.entity.JwtUser;
import site.minnan.recordlife.domain.entity.TradeInfo;
import site.minnan.recordlife.domain.entity.TradeType;
import site.minnan.recordlife.domain.mapper.TradeMapper;
import site.minnan.recordlife.domain.mapper.TradeTypeMapper;
import site.minnan.recordlife.domain.vo.ListQueryVO;
import site.minnan.recordlife.domain.vo.trade.*;
import site.minnan.recordlife.infrastructure.enumerate.TradeDirection;
import site.minnan.recordlife.infrastructure.exception.EntityNotExistException;
import site.minnan.recordlife.infrastructure.utils.RedisUtil;
import site.minnan.recordlife.userinterface.dto.GetTradeRecordDTO;
import site.minnan.recordlife.userinterface.dto.trade.AddTradeDTO;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Duration;
import java.util.ArrayList;
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

    @Autowired
    private RedisUtil redisUtil;

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
                .firstTypeId(tradeType.getParentId())
                .secondTypeId(tradeType.getId())
                .amount(dto.getAmount())
                .time(new Timestamp(System.currentTimeMillis()))
                .userId(user.getId())
                .direction(tradeType.getDirection())
                .remarks(dto.getRemarks())
                .build();
        tradeMapper.insert(record);
        redisUtil.delete("tradeList::" + user.getId());
    }

    /**
     * 获取流水列表
     *
     * @param dto
     * @return
     */
    @Override
    public ListQueryVO<TradeList> getTradeList(GetTradeRecordDTO dto) {
        JwtUser user = (JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        QueryWrapper<Trade> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", user.getId());
        Integer count = tradeMapper.selectCount(queryWrapper);
        if (count > 0) {
            Integer[] start = dto.getInitialTime();
            Integer[] end = dto.getByTime();
            DateTime startTime = DateUtil.parse(StrUtil.format("{}-{}", start[0], start[1]), "yyyy-M");
            DateTime endTime = DateUtil.parse(StrUtil.format("{}-{}", end[0], end[1]), "yyyy-M");
            endTime.offset(DateField.MONTH, 1);
            List<TradeInfo> tradeInfoList = getData(startTime, endTime, user.getId());

            List<TradeList> list = tradeInfoList.stream()
                    .map(TradeInfoVO::assemble)
                    .collect(Collectors.groupingBy(e -> DateUtil.beginOfDay(e.getTime())))
                    .entrySet().stream()
                    .map(entry -> {
                        DateTime dayTime = DateUtil.beginOfDay(entry.getKey());
                        return TradeList.dayList(dayTime, entry.getValue());
                    })
                    .collect(Collectors.groupingBy(e -> DateUtil.beginOfMonth(e.getDayTime())))
                    .entrySet().stream()
                    .map(entry -> {
                        DateTime monthTime = DateUtil.beginOfMonth(entry.getKey());
                        return TradeList.monthList(monthTime, entry.getValue());
                    })
                    .collect(Collectors.groupingBy(e -> DateUtil.beginOfYear(e.getMonthTime())))
                    .entrySet().stream()
                    .map(entry -> {
                        DateTime yearTime = DateUtil.beginOfYear(entry.getKey());
                        return TradeList.yearList(yearTime, entry.getValue());
                    })
                    .collect(Collectors.toList());
            return new ListQueryVO<>(list, (long) count);
        } else {
            return new ListQueryVO<>(ListUtil.empty(), 0L);
        }
    }

    /**
     * 获取图表数据
     *
     * @return
     */
    @Override
    public DataChart getDateChart() {
        JwtUser jwtUser = (JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        DateTime endTime = DateUtil.beginOfDay(DateTime.now());
        DateTime startTime = DateUtil.beginOfMonth(endTime.offsetNew(DateField.MONTH, -5));
        List<TradeInfo> list = getData(startTime, endTime, jwtUser.getId());
        Map<DateTime, List<TradeInfo>> groupByMonth = list.stream()
                .collect(Collectors.groupingBy(e -> DateUtil.beginOfMonth(e.getTime())));
        DataChart dataChart = new DataChart(6);
        List<DateTime> lastSixMonths = new ArrayList<>();
        DateTime temp = startTime;
        do {
            lastSixMonths.add(temp);
            temp = temp.offsetNew(DateField.MONTH, 1);
        } while (!DateUtil.isSameMonth(temp, endTime));
        lastSixMonths.add(temp);
        lastSixMonths.forEach(time -> {
            if (groupByMonth.containsKey(time)) {
                List<TradeInfo> tradeList = groupByMonth.get(time);
                BigDecimal[] total = calculateTotal(tradeList);
                dataChart.add(total[0], total[1], time.month() + 1);
            } else {
                dataChart.add(BigDecimal.ZERO, BigDecimal.ZERO, time.month() + 1);
            }
        });
        return dataChart;
    }


    /**
     * 获取首页数据
     *
     * @return
     */
    @Override
    public BaseInfoVO getBaseInfo() {
        JwtUser jwtUser = (JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        BaseInfoVO vo = new BaseInfoVO();
        //计算年度情况
        List<TradeInfo> yearData = getThis(DateField.YEAR, jwtUser.getId());
        BigDecimal[] yearTotal = calculateTotal(yearData);
        vo.setYearlyInfo(yearTotal[0], yearTotal[1]);
        //计算月度情况
        List<TradeInfo> monthData = getThis(DateField.MONTH, jwtUser.getId());
        BigDecimal[] monthTotal = calculateTotal(monthData);
        vo.setMonthlyInfo(monthTotal[0], monthTotal[1]);
        //计算本周情况
        List<TradeInfo> weekData = getThis(DateField.DAY_OF_WEEK, jwtUser.getId());
        BigDecimal[] weekTotal = calculateTotal(weekData);
        vo.setWeeklyInfo(weekTotal[0], weekTotal[1]);
        //计算今日情况
        List<TradeInfo> dayData = getThis(DateField.DAY_OF_MONTH, jwtUser.getId());
        BigDecimal[] dayTotal = calculateTotal(dayData);
        vo.setDailyInfo(dayTotal[0], dayTotal[1]);
        //查询最近一条记录
        TradeInfo recentTradeInfo = tradeMapper.getRecentTradeInfo(jwtUser.getId());
        vo.setRecentTrade(recentTradeInfo);
        return vo;
    }


    /**
     * (今天、本周、本月)详情查询接口
     *
     * @param dateField
     * @return
     */
    @Override
    public BaseDetail getBaseDetail(DateField dateField) {
        JwtUser jwtUser = (JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<TradeInfo> data = getThis(dateField, jwtUser.getId());
        return null;
    }

    /**
     * 计算总支出和总收入
     *
     * @param tradeList 源数据
     * @return 数组第一项为总收入，第二项总支出
     */
    private BigDecimal[] calculateTotal(List<? extends Trade> tradeList) {
        if (CollectionUtil.isEmpty(tradeList)) {
            return new BigDecimal[]{BigDecimal.ZERO, BigDecimal.ZERO};
        }
        BigDecimal totalExpend = tradeList.stream()
                .filter(e -> TradeDirection.EXPEND.equals(e.getDirection()))
                .map(Trade::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal totalIncome = tradeList.stream()
                .filter(e -> TradeDirection.INCOME.equals(e.getDirection()))
                .map(Trade::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return new BigDecimal[]{totalIncome, totalExpend};
    }


    /**
     * 获取过去一年的流水记录，先从redis中取，没有则在数据库中加载最近一年的记录
     *
     * @param userId 用户id
     * @return
     */
    private List<TradeInfo> loadData(Integer userId) {
        Object dataInRedis = redisUtil.getValue("tradeList::" + userId);
        List<TradeInfo> list;
        if (dataInRedis != null) {
            list = (List<TradeInfo>) dataInRedis;
        } else {
            DateTime endTime = DateTime.now();
            DateTime startTime = endTime.offsetNew(DateField.YEAR, -1);
            list = tradeMapper.getTradeInfoList(userId, startTime, endTime);
            redisUtil.valueSet("tradeList::" + userId, list, Duration.ofMinutes(30));
        }
        return list;
    }

    /**
     * 获取指定区间的流水记录
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @param userId    用户id
     * @return
     */
    private List<TradeInfo> getData(DateTime startTime, DateTime endTime, Integer userId) {
        List<TradeInfo> rawData = loadData(userId);
        return rawData.stream()
                .filter(e -> DateUtil.isIn(e.getTime(), startTime, endTime))
                .collect(Collectors.toList());
    }

    /**
     * 获取本日/本周/本月/本年的流水记录
     *
     * @param field  时间
     * @param userId 用户id
     * @return
     */
    private List<TradeInfo> getThis(DateField field, Integer userId) {
        DateTime endTime = DateTime.now();
        DateTime startTime;
        switch (field) {
            case YEAR: {
                startTime = DateUtil.beginOfYear(endTime);
                break;
            }
            case MONTH: {
                startTime = DateUtil.beginOfMonth(endTime);
                break;
            }
            case DAY_OF_WEEK: {
                startTime = DateUtil.beginOfWeek(endTime);
                break;
            }
            default: {
                startTime = DateUtil.beginOfDay(endTime);
            }
        }
        return getData(startTime, endTime, userId);
    }
}
