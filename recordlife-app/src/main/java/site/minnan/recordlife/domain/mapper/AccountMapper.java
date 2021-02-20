package site.minnan.recordlife.domain.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import site.minnan.recordlife.domain.aggregate.Account;

/**
 *
 * @author Minnan on 2021/2/20
 */
@Mapper
@Repository
public interface AccountMapper extends BaseMapper<Account> {

    @Select("select id from record_account where account_name = #{accountName} and user_id = #{userId} limit 1")
    Integer checkAccountNameUsed(@Param("accountName") String accountName, @Param("userId")Integer userId);


}
