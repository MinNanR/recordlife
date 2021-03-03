package site.minnan.recordlife.userinterface.fascade;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.map.MapBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.minnan.recordlife.application.service.LogService;
import site.minnan.recordlife.domain.vo.ListQueryVO;
import site.minnan.recordlife.domain.vo.log.LogVO;
import site.minnan.recordlife.infrastructure.enumerate.Operation;
import site.minnan.recordlife.infrastructure.enumerate.TradeDirection;
import site.minnan.recordlife.userinterface.dto.log.GetLogListDTO;
import site.minnan.recordlife.userinterface.response.ResponseEntity;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 日志控制器
 *
 * @author Minnan on 2021/3/3
 */
@Slf4j
@RestController
@RequestMapping("/recordApplets/log")
public class LogController {

    @Autowired
    private LogService logService;

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @PostMapping("getLogOperation")
    public ResponseEntity<ListQueryVO<Map<Object, Object>>> getLogOperation() {
        List<Map<Object, Object>> list = Arrays.stream(Operation.values())
                .map(e -> MapBuilder.create().put("key", e.operationName()).put("value", e.operationName()).build())
                .collect(Collectors.toList());
        ListQueryVO<Map<Object, Object>> vo = new ListQueryVO<>(list, null);
        return ResponseEntity.success(vo);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @PostMapping("getLogList")
    public ResponseEntity<ListQueryVO<LogVO>> getLogList(@RequestBody @Valid GetLogListDTO dto){
        ListQueryVO<LogVO> vo = logService.getLogList(dto);
        return ResponseEntity.success(vo);
    }

    @PostMapping("downloadLog")
    public void downloadLog(@RequestBody GetLogListDTO dto, HttpServletResponse response){
        ServletOutputStream outputStream = null;
        try {
            String time = DateUtil.format(DateTime.now(), "yyyyMMddHHmmss");
            response.setCharacterEncoding("utf-8");
            response.setContentType("multipart/form-data");
            response.setHeader("Content-Disposition", "attachment;fileName=" + time + ".csv");
            outputStream = response.getOutputStream();
            logService.downloadLog(dto, outputStream);
        } catch (IOException e) {
            log.error("下载日志IO异常", e);
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    log.error("下载日志关闭输出流异常", e);
                }
            }
        }
    }
}
