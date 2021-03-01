package site.minnan.recordlife.domain.vo.auth;

import cn.hutool.core.date.DateUtil;
import lombok.Builder;
import lombok.Data;
import site.minnan.recordlife.domain.aggregate.AuthUser;

/**
 * 小程序用户展示
 * @author Minnan on 2021/3/1
 */
@Data
@Builder
public class AppUserVO {

    private  Integer id;

    private String username;

    private String createTime;

    private String updateTime;

    public static AppUserVO assemble(AuthUser user){
        return AppUserVO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .createTime(DateUtil.format(user.getCreateTime(), "yyyy-MM-dd HH:mm:ss"))
                .updateTime(DateUtil.format(user.getUpdateTime(), "yyyy-MM-dd HH:mm:ss"))
                .build();
    }
}
