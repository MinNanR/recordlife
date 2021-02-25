package site.minnan.recordlife.userinterface.dto.account;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * 创建账户参数
 * @author Minnan on 2021/2/20
 */
@Data
public class AddAccountDTO {

    @NotEmpty(message = "账户名称不能为空")
    private String accountName;

    @NotEmpty(message = "币种不能为空")
    private String currency;
}
