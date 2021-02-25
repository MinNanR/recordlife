package site.minnan.recordlife.application.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import site.minnan.recordlife.domain.entity.JwtUser;
import site.minnan.recordlife.domain.vo.ListQueryVO;
import site.minnan.recordlife.domain.vo.LoginVO;
import site.minnan.recordlife.domain.vo.auth.AdminVO;
import site.minnan.recordlife.userinterface.dto.DetailsQueryDTO;
import site.minnan.recordlife.userinterface.dto.auth.*;

public interface UserService extends UserDetailsService {

    /**
     * 生成登录token
     *
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
     *
     * @param dto
     */
    void changePassword(ChangePasswordDTO dto);

    /**
     * 添加管理员
     *
     * @param dto
     */
    void addAdmin(AddAdminDTO dto);

    /**
     * 获取管理员列表
     *
     * @param dto
     * @return
     */
    ListQueryVO<AdminVO> getAdminUserList(GetAdminDTO dto);

    /**
     * 删除管理员
     *
     * @param dto
     */
    void deleteAdmin(DetailsQueryDTO dto);

    /**
     * 修改其他人的密码
     * @param dto
     */
    void editPassword(EditPasswordDTO dto);
}
