package site.minnan.recordlife.infrastructure.schedule;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import site.minnan.recordlife.application.service.CurrencyService;

import javax.annotation.PostConstruct;

@Component
@Slf4j
public class Scheduler {

    @Autowired
    private CurrencyService currencyService;

    @Scheduled(cron = "0 0 0 * * *")
//    @PostConstruct
    public void currencyTask() {
        log.info("刷新汇率开始");
        currencyService.refreshRate();
        log.info("刷新汇率结束");
    }
}
