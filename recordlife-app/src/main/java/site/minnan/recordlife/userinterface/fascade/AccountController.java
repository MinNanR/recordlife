package site.minnan.recordlife.userinterface.fascade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.minnan.recordlife.application.service.AccountService;
import site.minnan.recordlife.domain.vo.account.AccountVO;
import site.minnan.recordlife.domain.vo.ListQueryVO;
import site.minnan.recordlife.userinterface.dto.ListQueryDTO;
import site.minnan.recordlife.userinterface.dto.account.AddAccountDTO;
import site.minnan.recordlife.userinterface.response.ResponseEntity;

import javax.validation.Valid;

@RestController
@RequestMapping("/recordApplets/account")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @PreAuthorize("hasAnyAuthority('USER')")
    @PostMapping("addAccount")
    public ResponseEntity<?> addAccount(@RequestBody @Valid AddAccountDTO dto){
        accountService.addAccount(dto);
        return ResponseEntity.success();
    }

    @PreAuthorize("hasAnyAuthority('USER')")
    @PostMapping("getAccountList")
    public ResponseEntity<ListQueryVO<AccountVO>> getAccountList(@RequestBody @Valid ListQueryDTO dto){
        ListQueryVO<AccountVO> vo = accountService.getAccountList(dto);
        return ResponseEntity.success(vo);
    }
}
