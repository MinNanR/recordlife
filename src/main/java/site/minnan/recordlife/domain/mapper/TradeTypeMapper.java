package site.minnan.recordlife.domain.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import site.minnan.recordlife.domain.entity.TradeType;
import site.minnan.recordlife.infrastructure.enumerate.TradeDirection;

@Mapper
@Repository
public interface TradeTypeMapper extends BaseMapper<TradeType> {

    @Select("select id from dim_trade_type where name = #{name} and user_id = #{userId} and direction = #{direction} " +
            " and parent_id = #{parentId} limit 1")
    Integer checkNameUsed(@Param("name") String name, @Param("userId") Integer userId,
                          @Param("parentId") Integer parentId, @Param("direction") TradeDirection direction);

}
