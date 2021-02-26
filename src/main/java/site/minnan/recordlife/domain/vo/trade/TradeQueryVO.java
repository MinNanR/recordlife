package site.minnan.recordlife.domain.vo.trade;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import site.minnan.recordlife.domain.vo.ListQueryVO;

import java.util.List;

/**
 * 流水记录列表
 */
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TradeQueryVO extends ListQueryVO<TradeList> {

    Integer count;

    public TradeQueryVO(List<TradeList> list, Long totalCount, Integer count){
        super(list, totalCount);
        this.count = count;
    }


}
