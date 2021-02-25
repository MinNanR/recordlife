package site.minnan.recordlife.application.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import site.minnan.recordlife.application.service.CarouselService;
import site.minnan.recordlife.domain.entity.Carousel;
import site.minnan.recordlife.domain.entity.JwtUser;
import site.minnan.recordlife.domain.mapper.CarouselMapper;
import site.minnan.recordlife.domain.vo.ListQueryVO;
import site.minnan.recordlife.domain.vo.carousel.CarouselVO;
import site.minnan.recordlife.userinterface.dto.carousel.AddCarouselDTO;
import site.minnan.recordlife.userinterface.dto.carousel.GetCarouselDTO;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CarouselServiceImpl implements CarouselService {

    @Autowired
    private CarouselMapper carouselMapper;

    /**
     * 添加轮播图
     *
     * @param dto
     */
    @Override
    public void addCarousel(AddCarouselDTO dto) {
        JwtUser jwtUser = (JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Carousel carousel = Carousel.builder()
                .url(dto.getUrl())
                .isShow(Carousel.SHOW)
                .sketch(dto.getSketch())
                .createTime(new Timestamp(System.currentTimeMillis()))
                .createUserId(jwtUser.getId())
                .createUserName(jwtUser.getUsername())
                .build();
        carouselMapper.insert(carousel);
    }

    /**
     * 获取轮播图列表
     *
     * @param dto
     * @return
     */
    @Override
    public ListQueryVO<CarouselVO> getCarouselList(GetCarouselDTO dto) {
        QueryWrapper<Carousel> queryWrapper = new QueryWrapper<>();
        Optional.ofNullable(dto.getSketch()).ifPresent(s -> queryWrapper.like("sketch", dto.getSketch()));
        queryWrapper.orderByDesc("create_time");
        Page<Carousel> queryPage = new Page<>(dto.getPageIndex(), dto.getPageSize());
        IPage<Carousel> page = carouselMapper.selectPage(queryPage, queryWrapper);
        List<CarouselVO> list = page.getRecords().stream().map(CarouselVO::assemble).collect(Collectors.toList());
        return new ListQueryVO<>(list, page.getTotal());
    }
}
