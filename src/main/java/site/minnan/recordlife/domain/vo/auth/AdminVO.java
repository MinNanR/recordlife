package site.minnan.recordlife.domain.vo.auth;

import cn.hutool.core.date.DateUtil;
import lombok.Builder;
import lombok.Data;
import site.minnan.recordlife.domain.aggregate.AuthUser;

/**
 * 管理员列表展示
 * @author Minnan on 2021/2/25
 */
@Data
@Builder
public class AdminVO {

    private Integer id;

    private String username;

    private String nickName;

    private String role;

    private String createTime;

    public static AdminVO assemble(AuthUser user){
        return AdminVO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .nickName(user.getNickName())
                .role(user.getRole().getRoleName())
                .createTime(DateUtil.format(user.getCreateTime(), "yyyy-MM-dd HH:mm"))
                .build();
    }
}
