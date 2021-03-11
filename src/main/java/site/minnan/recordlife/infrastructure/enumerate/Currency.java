package site.minnan.recordlife.infrastructure.enumerate;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;

/***
 * 货币枚举
 * @author Minnan on 2021/2/20
 */
public enum  Currency {


    CNY("CNY", "人民币"),
    USD("USD", "美元"),
    EUR("EUR","欧元"),
    HKD("HKD","港币"),
    JPY("JPY", "日元"),
    GBP("GBP","英镑"),
    AUD("AUD","澳大利亚元"),
    CAD("CAD","加拿大元"),
    THB("THB","泰国铢"),
    SGD("SGD","新加坡元"),
    CHF("CHF","瑞士法郎"),
    DKK("DKK","丹麦克朗"),
    MOP("MOP","澳门元"),
    MYR("MYR","林吉特"),
    NOK("NOK","挪威克朗"),
    NZD("NZD","新西兰元"),
    PHP("PHP","菲律宾比索"),
    RUB("RUB","卢布"),
    SEK("SEK","瑞典克朗"),
    TWD("TWD","新台币"),
    BRL("BRL","巴西雷亚尔"),
    KRW("KRW","韩国元"),
    ZAR("ZAR","南非兰特");

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
