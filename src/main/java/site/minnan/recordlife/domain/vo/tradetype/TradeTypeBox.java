package site.minnan.recordlife.domain.vo.tradetype;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

/***
 * 类型下拉框
 * @author Minnan on 2021/2/20
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TradeTypeBox {

    private String value;

    private String text;

    private List<TradeTypeBox> free;

    public static TradeTypeBox secondLevel(Integer id, String name) {
        return new TradeTypeBox(id.toString(), name, null);
    }

    public static TradeTypeBox firstLevel(Integer id,String name, List<TradeTypeBox> children){
        return new TradeTypeBox(id.toString(), name, children);
    }
}
