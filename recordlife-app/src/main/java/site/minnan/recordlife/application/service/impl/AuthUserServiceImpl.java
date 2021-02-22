package site.minnan.recordlife.application.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import site.minnan.recordlife.application.service.AuthUserService;
import site.minnan.recordlife.domain.aggregate.AuthUser;
import site.minnan.recordlife.domain.entity.JwtUser;
import site.minnan.recordlife.domain.mapper.UserMapper;
import site.minnan.recordlife.infrastructure.utils.RedisUtil;
import site.minnan.recordlife.userinterface.dto.ChangePasswordDTO;

@Service
public class AuthUserServiceImpl implements AuthUserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private PasswordEncoder encoder;

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
