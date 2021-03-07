package site.minnan.recordlife.userinterface.fascade;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.minnan.recordlife.application.service.TradeService;
import site.minnan.recordlife.domain.vo.ListQueryVO;
import site.minnan.recordlife.domain.vo.trade.*;
import site.minnan.recordlife.infrastructure.annocation.OperateLog;
import site.minnan.recordlife.infrastructure.enumerate.Operation;
import site.minnan.recordlife.infrastructure.enumerate.TradeDirection;
import site.minnan.recordlife.userinterface.dto.trade.*;
import site.minnan.recordlife.userinterface.response.ResponseEntity;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("/recordApplets/trade")
public class TradeController {

    @Autowired
    private TradeService tradeService;

    @PreAuthorize("hasAnyAuthority('USER')")
    @PostMapping("addTradeRecord")
    public ResponseEntity<?> addTradeRecord(@RequestBody @Valid AddTradeDTO dto) {
        tradeService.addTrade(dto);
        return ResponseEntity.success();
    }

    @PreAuthorize("hasAnyAuthority('USER')")
    @PostMapping("getRecordData")
    public ResponseEntity<ListQueryVO<TradeList>> getTradeRecord(@RequestBody GetTradeRecordDTO dto) {
        ListQueryVO<TradeList> vo = tradeService.getTradeList(dto);
        return ResponseEntity.success(vo);
    }

    @PreAuthorize("hasAnyAuthority('USER')")
    @PostMapping("getDataChart")
    public ResponseEntity<DataChart> getDataChart() {
        DataChart vo = tradeService.getDateChart();
        return ResponseEntity.success(vo);
    }

    @PreAuthorize("hasAnyAuthority('USER')")
    @PostMapping("getBaseList")
    public ResponseEntity<BaseInfoVO> getBaseList() {
        BaseInfoVO vo = tradeService.getBaseInfo();
        return ResponseEntity.success(vo);
    }

    @PreAuthorize("hasAnyAuthority('USER')")
    @PostMapping("getBaseDetail")
    public ResponseEntity<BaseDetail> getBaseDetail(@RequestBody GetBaseDetailDTO dto) {
        BaseDetail vo = tradeService.getBaseDetail(dto);
        return ResponseEntity.success(vo);
    }

    @PreAuthorize("hasAnyAuthority('USER')")
    @PostMapping("getExpendReportList")
    public ResponseEntity<ListQueryVO<TradeVO>> getExpendReportList(@RequestBody @Valid GetTradeListDTO dto) {
        ListQueryVO<TradeVO> vo = tradeService.getTradeList(dto, TradeDirection.EXPEND);
        return ResponseEntity.success(vo);
    }

    @PreAuthorize("hasAnyAuthority('USER')")
    @PostMapping("getIncomeReportList")
    public ResponseEntity<ListQueryVO<TradeVO>> getIncomeReportList(@RequestBody @Valid GetTradeListDTO dto) {
        ListQueryVO<TradeVO> vo = tradeService.getTradeList(dto, TradeDirection.INCOME);
        return ResponseEntity.success(vo);
    }

    @OperateLog(operation = Operation.DOWNLOAD, module = "流水", content = "下载收入记录")
    @PreAuthorize("hasAnyAuthority('USER')")
    @PostMapping("downloadIncome")
    public void downloadIncome(@RequestBody GetTradeListDTO dto, HttpServletResponse response) {
        ServletOutputStream outputStream = null;
        try {
            String time = DateUtil.format(DateTime.now(), "yyyyMMddHHmmss");
            response.setCharacterEncoding("utf-8");
            response.setContentType("multipart/form-data");
            response.setHeader("Content-Disposition", "attachment;fileName=" + time + ".csv");
            outputStream = response.getOutputStream();
            tradeService.downloadTrade(dto, TradeDirection.INCOME, outputStream);
        } catch (IOException e) {
            log.error("下载收入列表IO异常", e);
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    log.error("下载收入列表关闭输出流异常", e);
                }
            }
        }
    }

    @OperateLog(operation = Operation.DOWNLOAD, module = "流水", content = "下载支出记录")
    @PreAuthorize("hasAnyAuthority('USER')")
    @PostMapping("downloadExpend")
    public void downloadExpend(@RequestBody GetTradeListDTO dto, HttpServletResponse response) {
        ServletOutputStream outputStream = null;
        try {
            String time = DateUtil.format(DateTime.now(), "yyyyMMddHHmmss");
            response.setCharacterEncoding("utf-8");
            response.setContentType("multipart/form-data");
            response.setHeader("Content-Disposition", "attachment;fileName=" + time + ".csv");
            outputStream = response.getOutputStream();
            tradeService.downloadTrade(dto, TradeDirection.EXPEND, outputStream);
        } catch (IOException e) {
            log.error("下载支出列表IO异常", e);
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    log.error("下载支出列表关闭输出流异常", e);
                }
            }
        }
    }

    @PreAuthorize("hasAnyAuthority('USER')")
    @PostMapping("getExpendRankList")
    public ResponseEntity<ListQueryVO<RankVO>> getExpendRank(@RequestBody @Valid GetExpendRankDTO dto) {
        ListQueryVO<RankVO> vo = tradeService.getExpendRankList(dto);
        return ResponseEntity.success(vo);
    }
}
