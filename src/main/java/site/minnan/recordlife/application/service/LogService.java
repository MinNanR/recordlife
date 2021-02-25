package site.minnan.recordlife.application.service;

import site.minnan.recordlife.infrastructure.annocation.OperateLog;

/**
 *
 * @author Minnan on 2021/2/25
 */
public interface LogService {

    /**
     * 记录日志
     * @param operateLog
     * @param ip
     */
    void addLog(OperateLog operateLog, String ip);
}
