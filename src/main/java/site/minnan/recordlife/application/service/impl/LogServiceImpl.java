package site.minnan.recordlife.application.service.impl;

import cn.hutool.core.text.csv.CsvUtil;
import cn.hutool.core.text.csv.CsvWriter;
import cn.hutool.core.util.ArrayUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import site.minnan.recordlife.application.service.LogService;
import site.minnan.recordlife.domain.entity.JwtUser;
import site.minnan.recordlife.domain.entity.Log;
import site.minnan.recordlife.domain.mapper.LogMapper;
import site.minnan.recordlife.domain.vo.ListQueryVO;
import site.minnan.recordlife.domain.vo.log.LogVO;
import site.minnan.recordlife.infrastructure.annocation.OperateLog;
import site.minnan.recordlife.userinterface.dto.log.GetLogListDTO;

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 日志serivce
 *
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

    /**
     * 获取日志列表
     *
     * @param dto
     * @return
     */
    @Override
    public ListQueryVO<LogVO> getLogList(GetLogListDTO dto) {
        QueryWrapper<Log> queryWrapper = new QueryWrapper<>();
        Optional.ofNullable(dto.getUsername()).ifPresent(s -> queryWrapper.like("username", s));
        Optional.ofNullable(dto.getOperation()).ifPresent(s -> queryWrapper.eq("operation", s));
        queryWrapper.orderByDesc("time");
        Page<Log> queryPage = new Page<>(dto.getPageIndex(), dto.getPageSize());
        IPage<Log> page = logMapper.selectPage(queryPage, queryWrapper);
        List<LogVO> list = page.getRecords().stream().map(LogVO::assemble).collect(Collectors.toList());
        return new ListQueryVO<>(list, page.getTotal());
    }

    /**
     * 下载日志
     *
     * @param dto
     * @param outputStream
     */
    @Override
    public void downloadLog(GetLogListDTO dto, OutputStream outputStream) {
        QueryWrapper<Log> queryWrapper = new QueryWrapper<>();
        Optional.ofNullable(dto.getUsername()).ifPresent(s -> queryWrapper.like("username", s));
        Optional.ofNullable(dto.getOperation()).ifPresent(s -> queryWrapper.eq("operation", s));
        queryWrapper.orderByDesc("time");
        List<Log> page = logMapper.selectList(queryWrapper);
        List<LogVO> list = page.stream().map(LogVO::assemble).collect(Collectors.toList());
        CsvWriter writer = CsvUtil.getWriter(new OutputStreamWriter(outputStream));
        writer.write(new String[]{"序号", "账号", "IP", "操作时间", "模块", "操作", "操作内容"});
        int ordinal = 1;
        for (LogVO vo : list) {
            String[] line = vo.getLogInfo(ordinal);
            writer.write(line);
            ordinal++;
        }
    }
}
