package site.minnan.recordlife.application.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import site.minnan.recordlife.domain.entity.JwtUser;
import site.minnan.recordlife.domain.vo.LoginVO;
import site.minnan.recordlife.userinterface.dto.ChangePasswordDTO;
import site.minnan.recordlife.userinterface.dto.PasswordLoginDTO;

public interface UserService  extends UserDetailsService {

    /**
     * 生成登录token
     * @param authentication
     * @return
     */
    LoginVO generateLoginVO(Authentication authentication);

    /**
     * 获取jwt用户，如果不存在则创建
     *
     * @param dto
     * @return
     */
    JwtUser getUser(PasswordLoginDTO dto);

    /**
     * 用户修改密码
     * @param dto
     */
    void changePassword(ChangePasswordDTO dto);
}