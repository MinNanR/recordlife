package site.minnan.recordlife.userinterface.fascade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.minnan.recordlife.application.service.TradeTypeService;
import site.minnan.recordlife.domain.vo.ListQueryVO;
import site.minnan.recordlife.domain.vo.tradetype.TradeTypeBox;
import site.minnan.recordlife.domain.vo.tradetype.TradeTypeVO;
import site.minnan.recordlife.userinterface.dto.trade.AddTradeTypeDTO;
import site.minnan.recordlife.userinterface.dto.trade.GetTradeTypeDTO;
import site.minnan.recordlife.userinterface.response.ResponseEntity;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/recordApplets/tradeType")
public class TradeTypeController {

    @Autowired
    private TradeTypeService tradeTypeService;

    @PreAuthorize("hasAnyAuthority('USER')")
    @PostMapping("addExpendClassify")
    public ResponseEntity<?> addExpendType(@RequestBody @Valid AddTradeTypeDTO dto) {
        tradeTypeService.addExpendType(dto);
        return ResponseEntity.success();
    }

    @PreAuthorize("hasAnyAuthority('USER')")
    @PostMapping("addIncomeClassify")
    public ResponseEntity<?> addIncomeType(@RequestBody @Valid AddTradeTypeDTO dto) {
        tradeTypeService.addIncomeType(dto);
        return ResponseEntity.success();
    }

    @PreAuthorize("hasAnyAuthority('USER')")
    @PostMapping("getClassifyList")
    public ResponseEntity<List<TradeTypeVO>> getTradeTypeList(@RequestBody @Valid GetTradeTypeDTO dto) {
        List<TradeTypeVO> vo = tradeTypeService.getTradeTypeList(dto);
        return ResponseEntity.success(vo);
    }


    @PreAuthorize("hasAnyAuthority('USER')")
    @PostMapping("getClassifyBox")
    public ResponseEntity<ListQueryVO<TradeTypeBox>> getTradeTypeBox(@RequestBody @Valid GetTradeTypeDTO dto){
        ListQueryVO<TradeTypeBox> vo = tradeTypeService.getTradeTypeBox(dto);
        return ResponseEntity.success(vo);
    }
}
