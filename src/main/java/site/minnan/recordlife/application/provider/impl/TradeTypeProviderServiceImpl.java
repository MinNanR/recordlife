package site.minnan.recordlife.application.provider.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import site.minnan.recordlife.application.provider.TradeTypeProviderService;
import site.minnan.recordlife.domain.entity.TradeType;
import site.minnan.recordlife.domain.mapper.TradeTypeMapper;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TradeTypeProviderServiceImpl implements TradeTypeProviderService {

    @Autowired
    private TradeTypeMapper tradeTypeMapper;

    /**
     * 获取流水类型详细信息
     *
     * @param ids
     */
    @Override
    public Map<Integer, TradeType> getTradeTypeDetails(Collection<Integer> ids) {
        List<TradeType> list = tradeTypeMapper.getTradeDetails(ids);
        return list.stream().collect(Collectors.toMap(TradeType::getId, e -> e));
    }
}
