package site.minnan.recordlife.domain.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import site.minnan.recordlife.domain.aggregate.Trade;

/***
 * 流水记录
 * @author Minnan on 2021/2/20
 */
@Mapper
@Repository
public interface TradeMapper extends BaseMapper<Trade> {

}
