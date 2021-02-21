package site.minnan.recordlife.domain.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import site.minnan.recordlife.domain.aggregate.Trade;
import site.minnan.recordlife.domain.entity.TradeInfo;
import site.minnan.recordlife.domain.vo.account.CheckAccountResult;

import java.util.List;

/***
 * 流水记录
 * @author Minnan on 2021/2/20
 */
@Mapper
@Repository
public interface TradeMapper extends BaseMapper<Trade> {

    @Select("select distinct account_id accountId, account_name accountName from record_trade where user_id = #{userId}")
    List<CheckAccountResult> getUsingAccount(@Param("userId")Integer userId);

    @Select("select t1.id id, t2.name secondTypeName, t1.direction direction, t1.time time, t1.account_name accountName, amount amount\n" +
            "from record_trade t1\n" +
            "left join dim_trade_type t2 on t1.second_type_id = t2.id where t1.user_id = #{userId}")
    List<TradeInfo> getTradeInfoList(@Param("user_id") Integer userId);
}
