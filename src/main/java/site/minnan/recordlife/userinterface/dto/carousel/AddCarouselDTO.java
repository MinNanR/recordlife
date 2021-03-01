package site.minnan.recordlife.userinterface.dto.carousel;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * 添加轮播图参数
 *
 * @author Minnan on 2021/2/22
 */
@Data
public class AddCarouselDTO {

    @NotEmpty(message = "地址不能为空")
    private String url;

    private String sketch;
}
