package site.minnan.recordlife.userinterface.dto.auth;

import lombok.Data;
import site.minnan.recordlife.userinterface.dto.ListQueryDTO;

/**
 * 小程序用户查询参数
 * @author Minnan on 2021/3/1
 */
@Data
public class GetAppUserDTO extends ListQueryDTO {

    private String username;
}
