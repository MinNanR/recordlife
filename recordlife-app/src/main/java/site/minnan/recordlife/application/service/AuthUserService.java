package site.minnan.recordlife.application.service;

import site.minnan.recordlife.userinterface.dto.ChangePasswordDTO;

/**
 * 用户的增删改操作
 * @author Minnan on 2021/2/22
 */
public interface AuthUserService {

    /**
     * 用户修改密码
     * @param dto
     */
    void changePassword(ChangePasswordDTO dto);
}
