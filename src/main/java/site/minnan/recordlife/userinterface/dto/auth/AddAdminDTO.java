package site.minnan.recordlife.userinterface.dto.auth;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * 添加管理员参数
 * @author Minnan on 2021/2/25
 */
@Data
public class AddAdminDTO {

    @NotEmpty(message = "用户名不能为空")
    private String username;

    @NotEmpty(message = "昵称不能为空")
    private String nickName;
}
