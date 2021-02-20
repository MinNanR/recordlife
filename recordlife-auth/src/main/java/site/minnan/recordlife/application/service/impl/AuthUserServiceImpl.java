package site.minnan.recordlife.application.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import site.minnan.recordlife.application.service.AuthUserService;
import site.minnan.recordlife.domain.aggregate.AuthUser;
import site.minnan.recordlife.domain.entity.JwtUser;
import site.minnan.recordlife.domain.mapper.UserMapper;
import site.minnan.recordlife.infrastructure.enumerate.Role;
import site.minnan.recordlife.infrastructure.utils.RedisUtil;
import site.minnan.recordlife.userinterface.dto.PasswordLoginDTO;

import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class AuthUserServiceImpl implements AuthUserService {

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder encoder;

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
                .enabled(authUser.getEnabled().equals(1))
                .build();
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
                .build();
        userMapper.insert(newUser);
        return newUser;
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
}
