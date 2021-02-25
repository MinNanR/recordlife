package site.minnan.recordlife.userinterface.fascade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.minnan.recordlife.application.service.CarouselService;
import site.minnan.recordlife.domain.vo.ListQueryVO;
import site.minnan.recordlife.domain.vo.carousel.CarouselVO;
import site.minnan.recordlife.userinterface.dto.carousel.AddCarouselDTO;
import site.minnan.recordlife.userinterface.dto.carousel.GetCarouselDTO;
import site.minnan.recordlife.userinterface.response.ResponseEntity;

import javax.validation.Valid;

@RestController
@RequestMapping("/recordApplets/carousel")
public class CarouselController {

    @Autowired
    private CarouselService carouselService;

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @PostMapping("addCarousel")
    public ResponseEntity<?> addCarousel(@RequestBody @Valid AddCarouselDTO dto){
        carouselService.addCarousel(dto);
        return ResponseEntity.success();
    }

    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    @PostMapping("getCarouselList")
    public ResponseEntity<ListQueryVO<CarouselVO>> getCarouselList(@RequestBody @Valid GetCarouselDTO dto){
        ListQueryVO<CarouselVO> vo = carouselService.getCarouselList(dto);
        return ResponseEntity.success(vo);
    }
}
