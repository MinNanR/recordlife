package site.minnan.recordlife.application.service;

import site.minnan.recordlife.domain.entity.JwtUser;
import site.minnan.recordlife.userinterface.dto.PasswordLoginDTO;

/**
 * 权限用户服务
 *
 * @author Minnan on 2021/2/20
 */
public interface AuthUserService {

    /**
     * 获取jwt用户，如果不存在则创建
     *
     * @param dto
     * @return
     */
    JwtUser getUser(PasswordLoginDTO dto);
}
