package site.minnan.recordlife.userinterface.dto.auth;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * 修改其他用户的密码
 * @author Minnan on 2021/2/25
 */
@Data
public class EditPasswordDTO {

    @NotNull(message = "未指定要修改的用户")
    private Integer id;

    @NotEmpty(message = "新密码不能为空")
    private String newPassword;
}
