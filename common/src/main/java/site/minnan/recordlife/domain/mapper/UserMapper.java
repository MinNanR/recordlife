package site.minnan.recordlife.domain.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import site.minnan.recordlife.domain.aggregate.AuthUser;

@Mapper
@Repository
public interface UserMapper extends BaseMapper<AuthUser> {
}
