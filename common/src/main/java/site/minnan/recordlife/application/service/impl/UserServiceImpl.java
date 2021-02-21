package site.minnan.recordlife.application.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
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
import site.minnan.recordlife.domain.vo.LoginVO;
import site.minnan.recordlife.infrastructure.utils.JwtUtil;
import site.minnan.recordlife.infrastructure.utils.RedisUtil;
import site.minnan.recordlife.userinterface.dto.ChangePasswordDTO;

import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

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
        return new LoginVO(StrUtil.format("Bearer {}", token));
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
                .eq("id", user.getId());
        userMapper.update(null, updateWrapper);
        redisUtil.delete("authUser::" + user.getUsername());
    }
}
