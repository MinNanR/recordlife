package site.minnan.recordlife.userinterface.dto.carousel;

import lombok.Data;
import site.minnan.recordlife.userinterface.dto.ListQueryDTO;

/***
 * 获取轮播图列表参数
 * @author Minnan on 2021/2/22
 */
@Data
public class GetCarouselDTO extends ListQueryDTO {

    private String sketch;
}
