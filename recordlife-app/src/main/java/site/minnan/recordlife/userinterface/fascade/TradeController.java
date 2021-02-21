package site.minnan.recordlife.userinterface.fascade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.minnan.recordlife.application.service.TradeService;
import site.minnan.recordlife.userinterface.dto.trade.AddTradeDTO;
import site.minnan.recordlife.userinterface.response.ResponseEntity;

import javax.validation.Valid;

@RestController
@RequestMapping("/recordApplets/trade")
public class TradeController {

    @Autowired
    private TradeService tradeService;

    @PreAuthorize("hasAnyAuthority('USER')")
    @PostMapping("addTradeRecord")
    public ResponseEntity<?> addTradeRecord(@RequestBody @Valid AddTradeDTO dto){
        tradeService.addTrade(dto);
        return ResponseEntity.success();
    }

    public ResponseEntity<?> getTradeRecord(){
        return ResponseEntity.success();
    }
}
