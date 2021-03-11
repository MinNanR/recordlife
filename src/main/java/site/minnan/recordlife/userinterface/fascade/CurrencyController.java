package site.minnan.recordlife.userinterface.fascade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.minnan.recordlife.application.service.CurrencyService;
import site.minnan.recordlife.domain.vo.ListQueryVO;
import site.minnan.recordlife.domain.vo.currency.CurrencyVO;
import site.minnan.recordlife.userinterface.dto.ListQueryDTO;
import site.minnan.recordlife.userinterface.response.ResponseEntity;

import javax.validation.Valid;

@RestController
@RequestMapping("/recordApplets/currency")
public class CurrencyController {

    @Autowired
    private CurrencyService currencyService;

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @PostMapping("getCurrencyList")
    public ResponseEntity<ListQueryVO<CurrencyVO>> getCurrencyList(@RequestBody @Valid ListQueryDTO dto){
        ListQueryVO<CurrencyVO> vo = currencyService.getCurrencyList(dto);
        return ResponseEntity.success(vo);
    }
}
