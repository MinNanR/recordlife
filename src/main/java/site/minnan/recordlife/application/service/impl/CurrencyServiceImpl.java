package site.minnan.recordlife.application.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import site.minnan.recordlife.application.service.CurrencyService;
import site.minnan.recordlife.domain.vo.ListQueryVO;
import site.minnan.recordlife.domain.vo.currency.CurrencyVO;
import site.minnan.recordlife.infrastructure.enumerate.Currency;
import site.minnan.recordlife.infrastructure.utils.RedisUtil;
import site.minnan.recordlife.userinterface.dto.ListQueryDTO;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Minnan on 2021/3/11
 */
@Service
public class CurrencyServiceImpl implements CurrencyService {

    @Autowired
    private RedisUtil redisUtil;

    private static final Map<String, Object> refreshParam;

    private static final String SUCCESS_RESULT_CODE = "200";

    static {
        refreshParam = new HashMap<>();
        refreshParam.put("key", "79644e4e8e715215c6cdf72dc62fd4fa");
        refreshParam.put("type", 1);
    }

    /**
     * 刷新汇率
     */
    @Override
    public void refreshRate() {
        String response = HttpUtil.post("http://web.juhe.cn:8080/finance/exchange/rmbquot", refreshParam);
        JSONObject responseJson = JSONUtil.parseObj(response);
        if (SUCCESS_RESULT_CODE.equals(responseJson.getStr("resultcode"))) {
            JSONArray resultArray = responseJson.getJSONArray("result");
            JSONObject result = resultArray.getJSONObject(0);
            Set<Map.Entry<String, Object>> entrySet = result.entrySet();
            DateTime now = DateTime.now();
            Map<String, Object> nameDataMap = entrySet.stream().collect(Collectors.toMap(Map.Entry::getKey,
                    e -> {
                        JSONObject object = JSONUtil.parseObj(e.getValue());
                        return new JSONObject()
                                .putOpt("currencyRate", object.getBigDecimal("bankConversionPri"))
                                .putOpt("updateTime", StrUtil.format("{} {}", DateUtil.formatDate(now), object.getStr(
                                        "time")))
                                .toString();
                    }));
            nameDataMap.put(Currency.CNY.getCurrencyName(), new JSONObject()
                    .putOpt("currencyRate", new BigDecimal("100.00"))
                    .putOpt("updateTime", DateUtil.formatDateTime(now))
                    .toString());
            Stream.of(Currency.values())
                    .forEach(e -> redisUtil.hashPut("currency_rate", e.getCurrencyCode(),
                            nameDataMap.get(e.getCurrencyName())));
        }
    }

    /**
     * 获取货币信息列表
     *
     * @param dto
     * @return
     */
    @Override
    public ListQueryVO<CurrencyVO> getCurrencyList(ListQueryDTO dto) {
        Map<String, JSONObject> source = redisUtil.getHash("currency_rate", JSONObject.class);
        Currency[] currencies = Currency.values();
        List<CurrencyVO> list = Stream.of(currencies)
                .skip(dto.getStart())
                .limit(dto.getPageSize())
                .map(e -> {
                    JSONObject data = JSONUtil.parseObj(source.get(e.getCurrencyCode()));
                    return new CurrencyVO(e.getCurrencyCode(), e.getCurrencyName(), data.getStr("updateTime"),
                            data.getBigDecimal("currencyRate"));
                })
                .collect(Collectors.toList());
        return new ListQueryVO<>(list, currencies.length);

    }
}
