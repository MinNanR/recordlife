package site.minnan.recordlife.application.service;

import site.minnan.recordlife.domain.entity.Carousel;
import site.minnan.recordlife.domain.vo.ListQueryVO;
import site.minnan.recordlife.domain.vo.carousel.CarouselVO;
import site.minnan.recordlife.userinterface.dto.DetailsQueryDTO;
import site.minnan.recordlife.userinterface.dto.auth.EditPasswordDTO;
import site.minnan.recordlife.userinterface.dto.carousel.AddCarouselDTO;
import site.minnan.recordlife.userinterface.dto.carousel.EditCarouselStateDTO;
import site.minnan.recordlife.userinterface.dto.carousel.GetCarouselDTO;

/***
 * 轮播图
 * @author Minnan on 2021/2/22
 */
public interface CarouselService {

    /**
     * 添加轮播图
     *
     * @param dto
     */
    void addCarousel(AddCarouselDTO dto);

    /**
     * 获取轮播图列表
     *
     * @param dto
     * @return
     */
    ListQueryVO<CarouselVO> getCarouselList(GetCarouselDTO dto);

    /**
     * 获取小程序端展示的轮播图
     *
     * @return
     */
    ListQueryVO<CarouselVO> getCarouselMap();

    /**
     * 编辑轮播图是否展示
     *
     * @param dto
     */
    void editCarouselState(EditCarouselStateDTO dto);

    /**
     * 删除轮播图
     *
     * @param dto
     */
    void deleteCarousel(DetailsQueryDTO dto);
}
