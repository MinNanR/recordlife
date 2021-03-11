package site.minnan.recordlife.domain.vo.currency;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class CurrencyVO {

    private String currencyCode;

    private String currencyName;

    private String updateTime;

    private BigDecimal currencyRate;
}
