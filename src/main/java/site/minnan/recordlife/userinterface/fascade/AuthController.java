package site.minnan.recordlife.userinterface.fascade;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
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
import site.minnan.recordlife.domain.vo.auth.AppUserVO;
import site.minnan.recordlife.infrastructure.annocation.OperateLog;
import site.minnan.recordlife.infrastructure.enumerate.Operation;
import site.minnan.recordlife.infrastructure.enumerate.Role;
import site.minnan.recordlife.userinterface.dto.DetailsQueryDTO;
import site.minnan.recordlife.userinterface.dto.auth.*;
import site.minnan.recordlife.userinterface.response.ResponseEntity;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("/recordApplets/auth")
public class AuthController {


    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private UserService userService;

    // TODO: 2021/3/4 下版本删除
    @PostMapping("login")
    public ResponseEntity<LoginVO> appLogin(@RequestBody @Valid AppLoginDTO dto) throws AuthenticationException {
        log.info("用户登录，登录信息：{}", new JSONObject(dto));
        Authentication authentication;
        try {
            JwtUser jwtUser = userService.getUser(dto);
            String role =
                    jwtUser.getAuthorities().stream().map(GrantedAuthority::getAuthority).findFirst().orElse("");
            if(!Role.USER.getValue().equals(role)){
                throw new AccessDeniedException("无权限访问");
            }
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

    @PostMapping("login/app")
    public ResponseEntity<LoginVO> appLogin1(@RequestBody @Valid AppLoginDTO dto) throws AuthenticationException {
        log.info("用户登录，登录信息：{}", new JSONObject(dto));
        Authentication authentication;
        try {
            JwtUser jwtUser = userService.getUser(dto);
            String role =
                    jwtUser.getAuthorities().stream().map(GrantedAuthority::getAuthority).findFirst().orElse("");
            if(!Role.USER.getValue().equals(role)){
                throw new AccessDeniedException("无权限访问");
            }
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

    @OperateLog(operation = Operation.LOGIN, module = "系统登录", content = "登录成功")
    @PostMapping("login/pc")
    public ResponseEntity<LoginVO> pcLogin(@RequestBody @Valid PasswordLoginDTO dto) throws AuthenticationException {
        log.info("用户登录，登录信息：{}", new JSONObject(dto));
        Authentication authentication;
        try {
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

    @OperateLog(operation = Operation.UPDATE, module = "系统用户管理",content = "修改密码")
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

    @OperateLog(operation = Operation.DELETE, module = "系统用户管理", content = "删除管理员")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @PostMapping("deleteAdministrator")
    public ResponseEntity<?> deleteAdmin(@RequestBody @Valid DetailsQueryDTO dto){
        userService.deleteAdmin(dto);
        return ResponseEntity.success();
    }

    @OperateLog(operation =Operation.UPDATE, module = "系统用户管理", content = "修改密码")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    @PostMapping("editorPassword")
    public ResponseEntity<?> editorPassword(@RequestBody @Valid EditPasswordDTO dto){
        userService.editPassword(dto);
        return ResponseEntity.success();
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @PostMapping("getAppUserList")
    public ResponseEntity<ListQueryVO<AppUserVO>> getAppUserList(@RequestBody @Valid GetAppUserDTO dto){
        ListQueryVO<AppUserVO> vo = userService.getAppUserList(dto);
        return ResponseEntity.success(vo);
    }

    @OperateLog(operation = Operation.DOWNLOAD, module = "系统用户管理", content = "下载小程序用户列表")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @PostMapping("downloadAppUserMessage")
    public void downloadAppUser(@RequestBody GetAppUserDTO dto, HttpServletResponse response){
        ServletOutputStream outputStream = null;
        try {
            String time = DateUtil.format(DateTime.now(), "yyyyMMddHHmmss");
            response.setCharacterEncoding("utf-8");
            response.setContentType("multipart/form-data");
            response.setHeader("Content-Disposition", "attachment;fileName=" + time + ".csv");
            outputStream = response.getOutputStream();
            userService.downloadAppUser(dto, outputStream);
        } catch (IOException e) {
            log.error("下载小程序用户IO异常", e);
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    log.error("下载小程序用户关闭输出流异常", e);
                }
            }
        }
    }
}
