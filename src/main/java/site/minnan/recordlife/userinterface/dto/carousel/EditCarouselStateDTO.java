package site.minnan.recordlife.userinterface.dto.carousel;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 编辑轮播图是否展示
 */
@Data
public class EditCarouselStateDTO {

    @NotNull(message = "未指定要修改的轮播图")
    private Integer id;

    @NotNull(message = "未指定是否展示")
    private Integer isShow;
}
