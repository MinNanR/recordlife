package site.minnan.recordlife.domain.vo.log;

import cn.hutool.core.date.DateUtil;
import lombok.Builder;
import lombok.Data;
import site.minnan.recordlife.domain.entity.Log;

/**
 * 日志展示
 *
 * @author Minnan on 2021/3/3
 */
@Data
@Builder
public class LogVO {

    private String username;

    private String operation;

    private String module;

    private String operateContent;

    private String ip;

    private String createTime;

    public static LogVO assemble(Log log) {
        return LogVO.builder()
                .username(log.getUsername())
                .operation(log.getOperation())
                .module(log.getModule())
                .operateContent(log.getContent())
                .ip(log.getIp())
                .createTime(DateUtil.formatDateTime(log.getTime()))
                .build();
    }

    public String[] getLogInfo(Integer ordinal) {
        return new String[]{String.valueOf(ordinal), username, ip, operation, createTime, module, operateContent};
    }

}
