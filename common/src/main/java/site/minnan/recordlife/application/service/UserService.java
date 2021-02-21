package site.minnan.recordlife.application.service;


import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import site.minnan.recordlife.domain.vo.LoginVO;
import site.minnan.recordlife.userinterface.dto.ChangePasswordDTO;

public interface UserService extends UserDetailsService {

    /**
     * 生成登录token
     * @param authentication
     * @return
     */
    LoginVO generateLoginVO(Authentication authentication);

    /**
     * 用户修改密码
     * @param dto
     */
    void changePassword(ChangePasswordDTO dto);
}
