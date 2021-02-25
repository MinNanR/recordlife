package site.minnan.recordlife.application.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import site.minnan.recordlife.application.service.LogService;
import site.minnan.recordlife.domain.entity.JwtUser;
import site.minnan.recordlife.domain.entity.Log;
import site.minnan.recordlife.domain.mapper.LogMapper;
import site.minnan.recordlife.infrastructure.annocation.OperateLog;

import java.sql.Timestamp;

/**
 * 日志serivce
 * @author Minnan on 2021/2/25
 */
@Service
public class LogServiceImpl implements LogService {

    @Autowired
    private LogMapper logMapper;

    /**
     * 记录日志
     *
     * @param operateLog
     * @param ip
     */
    @Override
    public void addLog(OperateLog operateLog, String ip) {
        JwtUser jwtUser = (JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Log log = Log.builder()
                .username(jwtUser.getUsername())
                .ip(ip)
                .time(new Timestamp(System.currentTimeMillis()))
                .module(operateLog.module())
                .operation(operateLog.operation().operationName())
                .content(operateLog.content())
                .build();
        logMapper.insert(log);
    }
}
