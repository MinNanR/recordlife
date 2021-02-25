package site.minnan.recordlife.userinterface.fascade;

import cn.hutool.json.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.minnan.recordlife.application.service.UserService;
import site.minnan.recordlife.domain.entity.JwtUser;
import site.minnan.recordlife.domain.vo.ListQueryVO;
import site.minnan.recordlife.domain.vo.LoginVO;
import site.minnan.recordlife.domain.vo.auth.AdminVO;
import site.minnan.recordlife.infrastructure.annocation.OperateLog;
import site.minnan.recordlife.infrastructure.enumerate.Operation;
import site.minnan.recordlife.userinterface.dto.DetailsQueryDTO;
import site.minnan.recordlife.userinterface.dto.auth.*;
import site.minnan.recordlife.userinterface.response.ResponseEntity;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/recordApplets/auth")
public class AuthController {


    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private UserService userService;

    @OperateLog(operation = Operation.LOGIN, module = "系统登录", content = "登录成功")
    @PostMapping("login")
    public ResponseEntity<LoginVO> loginPassword(@RequestBody @Valid PasswordLoginDTO dto) throws AuthenticationException {
        log.info("用户登录，登录信息：{}", new JSONObject(dto));
        Authentication authentication;
        try {
            JwtUser jwtUser = userService.getUser(dto);
            authentication = manager.authenticate(new UsernamePasswordAuthenticationToken(dto.getUsername(),
                    dto.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (DisabledException e) {
            throw new DisabledException("用户被禁用", e);
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("用户名或密码错误", e);
        }
        LoginVO vo = userService.generateLoginVO(authentication);
        return ResponseEntity.success(vo);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    @PostMapping("editPassword")
    public ResponseEntity<?> changePassword(@RequestBody @Valid ChangePasswordDTO dto){
        userService.changePassword(dto);
        return ResponseEntity.success();
    }

    @OperateLog(operation = Operation.LOGOUT, module = "系统登录", content = "登出")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    @PostMapping("logout")
    public ResponseEntity<?> logout(){
        return ResponseEntity.success();
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @OperateLog(operation = Operation.ADD, module = "系统用户管理", content = "添加管理员")
    @PostMapping("createAdministrator")
    public ResponseEntity<?> addAdmin(@RequestBody @Valid AddAdminDTO dto){
        userService.addAdmin(dto);
        return ResponseEntity.success();
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @PostMapping("getAdministratorList")
    public ResponseEntity<ListQueryVO<AdminVO>> getAdminList(@RequestBody @Valid GetAdminDTO dto){
        ListQueryVO<AdminVO> vo = userService.getAdminUserList(dto);
        return ResponseEntity.success(vo);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @PostMapping("deleteAdministrator")
    public ResponseEntity<?> deleteAdmin(@RequestBody @Valid DetailsQueryDTO dto){
        userService.deleteAdmin(dto);
        return ResponseEntity.success();
    }


    @PostMapping("editorPassword")
    public ResponseEntity<?> editorPassword(@RequestBody @Valid EditPasswordDTO dto){
        userService.editPassword(dto);
        return ResponseEntity.success();
    }




}
