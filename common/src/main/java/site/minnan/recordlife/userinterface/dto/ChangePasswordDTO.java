package site.minnan.recordlife.userinterface.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * 修改密码参数
 */
@Data
public class ChangePasswordDTO {

    @NotEmpty(message = "新密码不能为空")
    private String password;
}
