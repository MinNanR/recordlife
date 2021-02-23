package site.minnan.recordlife.userinterface.fascade;

import cn.hutool.core.lang.Console;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.minnan.recordlife.application.service.TradeService;
import site.minnan.recordlife.domain.vo.ListQueryVO;
import site.minnan.recordlife.domain.vo.trade.BaseDetail;
import site.minnan.recordlife.domain.vo.trade.BaseInfoVO;
import site.minnan.recordlife.domain.vo.trade.DataChart;
import site.minnan.recordlife.domain.vo.trade.TradeList;
import site.minnan.recordlife.userinterface.dto.GetTradeRecordDTO;
import site.minnan.recordlife.userinterface.dto.trade.AddTradeDTO;
import site.minnan.recordlife.userinterface.dto.trade.GetBaseDetailDTO;
import site.minnan.recordlife.userinterface.response.ResponseEntity;

import javax.validation.Valid;

@RestController
@RequestMapping("/recordApplets/trade")
public class TradeController {

    @Autowired
    private TradeService tradeService;

    @PreAuthorize("hasAnyAuthority('USER')")
    @PostMapping("addTradeRecord")
    public ResponseEntity<?> addTradeRecord(@RequestBody @Valid AddTradeDTO dto) {
        tradeService.addTrade(dto);
        return ResponseEntity.success();
    }

    @PreAuthorize("hasAnyAuthority('USER')")
    @PostMapping("getRecordData")
    public ResponseEntity<ListQueryVO<TradeList>> getTradeRecord(@RequestBody GetTradeRecordDTO dto) {
        ListQueryVO<TradeList> vo = tradeService.getTradeList(dto);
        return ResponseEntity.success(vo);
    }

    @PreAuthorize("hasAnyAuthority('USER')")
    @PostMapping("getDataChart")
    public ResponseEntity<DataChart> getDataChart() {
        DataChart vo = tradeService.getDateChart();
        return ResponseEntity.success(vo);
    }

    @PreAuthorize("hasAnyAuthority('USER')")
    @PostMapping("getBaseList")
    public ResponseEntity<BaseInfoVO> getBaseList() {
        BaseInfoVO vo = tradeService.getBaseInfo();
        return ResponseEntity.success(vo);
    }

    @PreAuthorize("hasAnyAuthority('USER')")
    @PostMapping("getBaseDetail")
    public ResponseEntity<BaseDetail> getBaseDetail(@RequestBody GetBaseDetailDTO dto) {
        BaseDetail vo = tradeService.getBaseDetail(dto);
        return ResponseEntity.success(vo);
    }
}
