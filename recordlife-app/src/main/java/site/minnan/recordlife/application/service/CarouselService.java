package site.minnan.recordlife.application.service;

import site.minnan.recordlife.domain.vo.carousel.CarouselVO;
import site.minnan.recordlife.domain.vo.ListQueryVO;
import site.minnan.recordlife.userinterface.dto.carousel.AddCarouselDTO;
import site.minnan.recordlife.userinterface.dto.carousel.GetCarouselDTO;

/***
 * 轮播图
 * @author Minnan on 2021/2/22
 */
public interface CarouselService {

    /**
     * 添加轮播图
     * @param dto
     */
    void addCarousel(AddCarouselDTO dto);

    /**
     * 获取轮播图列表
     * @param dto
     * @return
     */
    ListQueryVO<CarouselVO> getCarouselList(GetCarouselDTO dto);
}
