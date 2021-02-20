package site.minnan.recordlife.application.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.minnan.recordlife.application.service.TradeTypeService;
import site.minnan.recordlife.domain.entity.JwtUser;
import site.minnan.recordlife.domain.entity.TradeType;
import site.minnan.recordlife.domain.mapper.TradeTypeMapper;
import site.minnan.recordlife.domain.vo.ListQueryVO;
import site.minnan.recordlife.domain.vo.tradetype.TradeTypeBox;
import site.minnan.recordlife.domain.vo.tradetype.TradeTypeVO;
import site.minnan.recordlife.infrastructure.enumerate.TradeDirection;
import site.minnan.recordlife.infrastructure.exception.EntityAlreadyExistException;
import site.minnan.recordlife.userinterface.dto.trade.AddTradeTypeDTO;
import site.minnan.recordlife.userinterface.dto.trade.GetTradeTypeDTO;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 流水分类相关操作
 *
 * @author Minnan on 2021/2/20
 */
@Service
public class TradeTypeServiceImpl implements TradeTypeService {

    @Autowired
    private TradeTypeMapper tradeTypeMapper;

    /**
     * 添加支出分类
     *
     * @param dto
     */
    @Override
    @Transactional
    public void addExpendType(AddTradeTypeDTO dto) {
        JwtUser jwtUser = (JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Integer firstTypeId = Optional.ofNullable(dto.getId()).orElseGet(() -> addFirstExpendType(dto.getSeniorName(),
                jwtUser.getId()));
        Integer check = tradeTypeMapper.checkNameUsed(dto.getSecondName(), jwtUser.getId(), firstTypeId,
                TradeDirection.EXPEND);
        if (check != null) {
            throw new EntityAlreadyExistException("二级分类已存在");
        }
        TradeType newType = TradeType.builder()
                .name(dto.getSecondName())
                .parentId(firstTypeId)
                .direction(TradeDirection.EXPEND)
                .userId(jwtUser.getId())
                .build();
        tradeTypeMapper.insert(newType);
    }

    @Transactional
    public Integer addFirstExpendType(String typeName, Integer userId) {
        Integer check = tradeTypeMapper.checkNameUsed(typeName, userId, 0, TradeDirection.EXPEND);
        if (check != null) {
            throw new EntityAlreadyExistException("一级分类已存在");
        }
        TradeType newType = TradeType.builder()
                .name(typeName)
                .parentId(0)
                .direction(TradeDirection.EXPEND)
                .userId(userId)
                .build();
        tradeTypeMapper.insert(newType);
        return newType.getId();
    }

    /**
     * 添加收入分类
     *
     * @param dto
     */
    @Override
    @Transactional
    public void addIncomeType(AddTradeTypeDTO dto) {
        JwtUser jwtUser = (JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Integer firstTypeId = Optional.ofNullable(dto.getId()).orElseGet(() -> addFirstIncomeType(dto.getSeniorName(),
                jwtUser.getId()));
        Integer check = tradeTypeMapper.checkNameUsed(dto.getSecondName(), jwtUser.getId(), firstTypeId,
                TradeDirection.INCOME);
        if (check != null) {
            throw new EntityAlreadyExistException("二级分类已存在");
        }
        TradeType newType = TradeType.builder()
                .name(dto.getSecondName())
                .parentId(firstTypeId)
                .direction(TradeDirection.INCOME)
                .userId(jwtUser.getId())
                .build();
        tradeTypeMapper.insert(newType);
    }

    @Transactional
    public Integer addFirstIncomeType(String typeName, Integer userId) {
        Integer check = tradeTypeMapper.checkNameUsed(typeName, userId, 0, TradeDirection.INCOME);
        if (check != null) {
            throw new EntityAlreadyExistException("一级分类已存在");
        }
        TradeType newType = TradeType.builder()
                .name(typeName)
                .parentId(0)
                .direction(TradeDirection.INCOME)
                .userId(userId)
                .build();
        tradeTypeMapper.insert(newType);
        return newType.getId();
    }

    /**
     * 获取流水类型列表
     *
     * @param dto
     * @return
     */
    @Override
    public List<TradeTypeVO> getTradeTypeList(GetTradeTypeDTO dto) {
        JwtUser jwtUser = (JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        QueryWrapper<TradeType> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", jwtUser.getId())
                .eq("direction", dto.getDirection());
        List<TradeType> list = tradeTypeMapper.selectList(queryWrapper);
        Map<Integer, List<TradeTypeVO>> groupByFirstLevelId = list.stream()
                .filter(e -> e.getParentId() != 0)//挑选出二级分类
                .collect(Collectors.groupingBy(TradeType::getParentId,
                        Collectors.mapping(e -> TradeTypeVO.secondLevel(e.getId(), e.getName()), Collectors.toList())));
        return list.stream()
                .filter(e -> e.getParentId() == 0)//挑选出一级分类
                .map(e -> {
                    List<TradeTypeVO> children = groupByFirstLevelId.get(e.getId());
                    return TradeTypeVO.firstLevel(e.getId(), e.getName(), children);
                })
                .collect(Collectors.toList());
    }

    /**
     * 获取流水类型下拉框
     *
     * @param dto
     * @return
     */
    @Override
    public ListQueryVO<TradeTypeBox> getTradeTypeBox(GetTradeTypeDTO dto) {
        JwtUser jwtUser = (JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        QueryWrapper<TradeType> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", jwtUser.getId())
                .eq("direction", dto.getDirection());
        List<TradeType> list = tradeTypeMapper.selectList(queryWrapper);
        Map<Integer, List<TradeTypeBox>> groupByFirstLevelId = list.stream()
                .filter(e -> e.getParentId() != 0)//挑选出二级分类
                .collect(Collectors.groupingBy(TradeType::getParentId,
                        Collectors.mapping(e -> TradeTypeBox.secondLevel(e.getId(), e.getName()), Collectors.toList())));
        List<TradeTypeBox> result = list.stream()
                .filter(e -> e.getParentId() == 0)//挑选出一级分类
                .map(e -> {
                    List<TradeTypeBox> children = groupByFirstLevelId.get(e.getId());
                    return TradeTypeBox.firstLevel(e.getId(), e.getName(), children);
                })
                .collect(Collectors.toList());
        return new ListQueryVO<>(result, null);
    }
}
