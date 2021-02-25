package site.minnan.recordlife.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 登录返回参数
 * @author Minnan on 2021/2/25
 */
@Data
@AllArgsConstructor
public class LoginVO {

    private String authority;

    private String role;
}
