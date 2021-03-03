package site.minnan.recordlife.userinterface.dto.log;

import lombok.Data;
import site.minnan.recordlife.userinterface.dto.ListQueryDTO;

/**
 * 日志查询参数
 * @author Minnan on 2021/3/3
 */
@Data
public class GetLogListDTO extends ListQueryDTO {

    private String username;

    private String operation;
}
