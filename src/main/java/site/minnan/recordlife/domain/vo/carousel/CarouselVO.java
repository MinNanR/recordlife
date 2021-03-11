package site.minnan.recordlife.domain.vo.carousel;

import cn.hutool.core.date.DateUtil;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import site.minnan.recordlife.domain.entity.Carousel;

/***
 * 轮播图列表数据
 * @author Minnan on 2021/2/22
 */
@Data
@Builder
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CarouselVO {

    private Integer id;

    private String imageUrl;

    private String sketch;

    private Integer isShow;

    private String createTime;

    public static CarouselVO assemble(Carousel carousel){
        return CarouselVO.builder()
                .id(carousel.getId())
                .imageUrl(carousel.getUrl())
                .sketch(carousel.getSketch())
                .isShow(carousel.getIsShow())
                .createTime(DateUtil.format(carousel.getCreateTime(), "yyyy-MM-dd HH:mm"))
                .build();
    }
}
