package site.minnan.recordlife.application.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.MD5;
import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import site.minnan.recordlife.application.service.UserService;
import site.minnan.recordlife.domain.aggregate.AuthUser;
import site.minnan.recordlife.domain.entity.JwtUser;
import site.minnan.recordlife.domain.mapper.UserMapper;
import site.minnan.recordlife.domain.vo.ListQueryVO;
import site.minnan.recordlife.domain.vo.LoginVO;
import site.minnan.recordlife.domain.vo.auth.AdminVO;
import site.minnan.recordlife.infrastructure.enumerate.Role;
import site.minnan.recordlife.infrastructure.exception.EntityAlreadyExistException;
import site.minnan.recordlife.infrastructure.exception.EntityNotExistException;
import site.minnan.recordlife.infrastructure.exception.OperationNotSupportException;
import site.minnan.recordlife.infrastructure.utils.JwtUtil;
import site.minnan.recordlife.infrastructure.utils.RedisUtil;
import site.minnan.recordlife.userinterface.dto.DetailsQueryDTO;
import site.minnan.recordlife.userinterface.dto.auth.*;

import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder encoder;

    /**
     * Locates the user based on the username. In the actual implementation, the search
     * may possibly be case sensitive, or case insensitive depending on how the
     * implementation instance is configured. In this case, the <code>UserDetails</code>
     * object that comes back may have a username that is of a different case than what
     * was actually requested..
     *
     * @param username the username identifying the user whose data is required.
     * @return a fully populated user record (never <code>null</code>)
     * @throws UsernameNotFoundException if the user could not be found or the user has no
     *                                   GrantedAuthority
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<AuthUser> userOptional = getAuthUser(username);
        AuthUser authUser = userOptional.orElseThrow(() -> new UsernameNotFoundException("用户名不存在"));
        String roleName = authUser.getRole().getValue();
        List<GrantedAuthority> grantedAuthorities = Collections.singletonList(new SimpleGrantedAuthority(roleName));
        return JwtUser.builder()
                .id(authUser.getId())
                .username(username)
                .password(authUser.getPassword())
                .authorities(grantedAuthorities)
                .enabled(authUser.getEnabled().equals(1))
                .passwordStamp(authUser.getPasswordStamp())
                .build();
    }


    /**
     * 生成登录token
     *
     * @param authentication
     * @return
     */
    @Override
    public LoginVO generateLoginVO(Authentication authentication) {
        JwtUser jwtUser = (JwtUser) authentication.getPrincipal();
        String token = jwtUtil.generateToken(jwtUser);
        String role =
                jwtUser.getAuthorities().stream().map(GrantedAuthority::getAuthority).findFirst().orElse("");
        return new LoginVO(StrUtil.format("Bearer {}", token), role);
    }

    private Optional<AuthUser> getAuthUser(String username) {
        if (StrUtil.isBlank(username)) return Optional.empty();
        AuthUser userInRedis = (AuthUser) redisUtil.getValue("authUser::" + username);
        if (userInRedis != null) {
            log.info("从redis中取出用户信息: {}", new JSONObject(userInRedis));
            return Optional.of(userInRedis);
        }
        QueryWrapper<AuthUser> wrapper = new QueryWrapper<>();
        wrapper.eq("username", username);
        AuthUser userInDB = userMapper.selectOne(wrapper);
        Optional<AuthUser> userOptional = Optional.ofNullable(userInDB);
        userOptional.ifPresent(user -> redisUtil.valueSet("authUser::" + username, user, Duration.ofMinutes(30)));
        return userOptional;
    }

    /**
     * 获取jwt用户，如果不存在则创建
     *
     * @param dto
     * @return
     */
    @Override
    public JwtUser getUser(PasswordLoginDTO dto) {
        Optional<AuthUser> userOptional = getAuthUser(dto.getUsername());
        AuthUser authUser = userOptional.orElseGet(() -> createAuthUser(dto));
        String roleName = authUser.getRole().getValue();
        List<GrantedAuthority> grantedAuthorities = Collections.singletonList(new SimpleGrantedAuthority(roleName));
        return JwtUser.builder()
                .id(authUser.getId())
                .username(authUser.getUsername())
                .password(authUser.getPassword())
                .authorities(grantedAuthorities)
                .enabled(authUser.getEnabled().equals(AuthUser.ENABLE))
                .passwordStamp(authUser.getPasswordStamp())
                .build();
    }

    /**
     * 用户修改密码
     *
     * @param dto
     */
    @Override
    public void changePassword(ChangePasswordDTO dto) {
        String encodedPassword = encoder.encode(dto.getPassword());
        JwtUser user = (JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UpdateWrapper<AuthUser> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("password", encodedPassword)
                .set("password_stamp", UUID.randomUUID().toString().replaceAll("-", ""))
                .eq("id", user.getId());
        userMapper.update(null, updateWrapper);
        redisUtil.delete("authUser::" + user.getUsername());
    }

    /**
     * 添加管理员
     *
     * @param dto
     */
    @Override
    public void addAdmin(AddAdminDTO dto) {
        JwtUser jwtUser = (JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<AuthUser> authUser = getAuthUser(dto.getUsername());
        if (authUser.isPresent()) {
            throw new EntityAlreadyExistException("用户名已使用");
        }
        String md5Password = MD5.create().digestHex("123456");
        AuthUser user = AuthUser.builder()
                .username(dto.getUsername())
                .nickName(dto.getNickName())
                .password(encoder.encode(md5Password))
                .enabled(AuthUser.ENABLE)
                .role(Role.ADMIN)
                .build();
        user.setCreateUser(jwtUser);
        userMapper.insert(user);
    }

    @Override
    public ListQueryVO<AdminVO> getAdminUserList(GetAdminDTO dto) {
        QueryWrapper<AuthUser> queryWrapper = new QueryWrapper<>();
        Optional.ofNullable(dto.getUsername()).ifPresent(s -> queryWrapper.like("username", s));
        queryWrapper.eq("role", Role.ADMIN);
        Page<AuthUser> queryPage = new Page<>(dto.getPageIndex(), dto.getPageSize());
        IPage<AuthUser> page = userMapper.selectPage(queryPage, queryWrapper);
        List<AdminVO> list = page.getRecords().stream().map(AdminVO::assemble).collect(Collectors.toList());
        return new ListQueryVO<>(list, page.getTotal());
    }

    /**
     * 删除管理员
     *
     * @param dto
     */
    @Override
    public void deleteAdmin(DetailsQueryDTO dto) {
        AuthUser authUser = userMapper.selectById(dto.getId());
        if (authUser == null) {
            throw new EntityNotExistException("账户不存在");
        }
        JwtUser jwtUser = (JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(jwtUser.getId().equals(dto.getId())){
            throw new OperationNotSupportException("不可删除自己");
        }
        userMapper.deleteById(dto.getId());
        redisUtil.delete("authUser::" + authUser.getUsername());
    }

    /**
     * 修改其他人的密码
     *
     * @param dto
     */
    @Override
    public void editPassword(EditPasswordDTO dto) {
        AuthUser authUser = userMapper.selectById(dto.getId());
        if(authUser == null){
            throw new EntityNotExistException("用户不存在");
        }
        String encodedPassword = encoder.encode(dto.getNewPassword());
        UpdateWrapper<AuthUser> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", dto.getId())
                .set("password", encodedPassword)
                .set("password_stamp", UUID.randomUUID().toString().replaceAll("-", ""));
        userMapper.update(null ,updateWrapper);
        redisUtil.delete("authUser::" + authUser.getUsername());
    }

    /**
     * 创建用户
     *
     * @param dto
     * @return
     */
    private AuthUser createAuthUser(PasswordLoginDTO dto) {
        AuthUser newUser = AuthUser.builder()
                .username(dto.getUsername())
                .password(encoder.encode(dto.getPassword()))
                .role(Role.USER)
                .enabled(AuthUser.ENABLE)
                .passwordStamp(UUID.randomUUID().toString().replaceAll("-", ""))
                .build();
        userMapper.insert(newUser);
        return newUser;
    }
}
