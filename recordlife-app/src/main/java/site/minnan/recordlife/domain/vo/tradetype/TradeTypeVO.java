package site.minnan.recordlife.domain.vo.tradetype;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.List;

/**
 * 流水类型
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TradeTypeVO {

    private Integer id;

    private String name;

    private List<TradeTypeVO> children;

    public static TradeTypeVO secondLevel(Integer id, String name) {
        return new TradeTypeVO(id, name, null);
    }

    public static TradeTypeVO firstLevel(Integer id,String name, List<TradeTypeVO> children){
        return new TradeTypeVO(id, name, children);
    }
}
