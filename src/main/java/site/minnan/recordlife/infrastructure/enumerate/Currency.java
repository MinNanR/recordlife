package site.minnan.recordlife.infrastructure.enumerate;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;

/***
 * 货币枚举
 * @author Minnan on 2021/2/20
 */
public enum  Currency {


    CNY("CNY", "人民币");

    Currency(String code, String name){
        this.currencyCode = code;
        this.currencyName  = name;
    }

    /**
     * 货币代码
     */
    @EnumValue
    private final String currencyCode;

    /**
     * 货币中文名称
     */
    @JsonValue
    private final String currencyName;

    public String getCurrencyCode() {
        return currencyCode;
    }

    public String getCurrencyName() {
        return currencyName;
    }
}
