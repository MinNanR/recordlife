package site.minnan.recordlife.userinterface.dto.auth;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/***
 * 小程序登录接口
 * @author Minnan on 2021/3/4
 */
@Data
public class AppLoginDTO {

    @Pattern(regexp = "^1([3456789])\\d{9}$" ,message = "请填写正确的手机号码")
    private String username;

    @NotEmpty(message = "密码不能为空")
    private String password;
}
