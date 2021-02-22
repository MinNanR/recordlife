package site.minnan.recordlife.userinterface.fascade;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.minnan.recordlife.domain.vo.carousel.CurrencyBox;
import site.minnan.recordlife.domain.vo.ListQueryVO;
import site.minnan.recordlife.infrastructure.enumerate.Currency;
import site.minnan.recordlife.userinterface.response.ResponseEntity;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/recordApplets/public")
public class CommonController {

    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    @PostMapping("getCurrencyBox")
    public ResponseEntity<ListQueryVO<CurrencyBox>> getCurrencyBox(){
        List<CurrencyBox> currencyBoxes = Stream.of(Currency.values())
                .map(e -> new CurrencyBox(e.getCurrencyCode(), e.getCurrencyName()))
                .collect(Collectors.toList());
        ListQueryVO<CurrencyBox> vo = new ListQueryVO<>(currencyBoxes, null);
        return ResponseEntity.success(vo);
    }
}
