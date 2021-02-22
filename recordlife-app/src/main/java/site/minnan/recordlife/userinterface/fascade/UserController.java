package site.minnan.recordlife.userinterface.fascade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.minnan.recordlife.application.service.AuthUserService;
import site.minnan.recordlife.application.service.UserService;
import site.minnan.recordlife.userinterface.dto.ChangePasswordDTO;

import javax.validation.Valid;

@RestController
@RequestMapping("/recordApplets/user")
public class UserController {

    @Autowired
    private AuthUserService userService;

    @PostMapping("editPassword")
    public void changePassword(@RequestBody @Valid ChangePasswordDTO dto){
        userService.changePassword(dto);
    }
}
