package site.minnan.recordlife.application.provider;

import site.minnan.recordlife.domain.entity.TradeType;

import java.util.Collection;
import java.util.Map;

public interface TradeTypeProviderService {

    /**
     * 获取流水类型详细信息
     * @param ids 二级分类id
     * @return key:二级分类id，value：分类信息
     */
    Map<Integer, TradeType> getTradeTypeDetails(Collection<Integer> ids);
}
