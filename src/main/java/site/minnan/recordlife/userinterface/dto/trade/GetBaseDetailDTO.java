package site.minnan.recordlife.userinterface.dto.trade;

import cn.hutool.core.date.DateField;
import lombok.Data;

/***
 * 时间段详情查询
 * @author Minnan on 2021/2/23
 */
@Data
public class GetBaseDetailDTO {

    private DateField timeMode;
}
