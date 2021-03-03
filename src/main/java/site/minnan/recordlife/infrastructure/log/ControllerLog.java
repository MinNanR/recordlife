package site.minnan.recordlife.infrastructure.log;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import site.minnan.recordlife.application.service.LogService;
import site.minnan.recordlife.domain.entity.JwtUser;
import site.minnan.recordlife.infrastructure.annocation.OperateLog;
import site.minnan.recordlife.infrastructure.utils.RedisUtil;
import site.minnan.recordlife.infrastructure.utils.WebUtil;

import javax.servlet.http.HttpServletRequest;
import java.time.Duration;
import java.util.Arrays;

@Aspect
@Component
@Slf4j
public class ControllerLog {

    @Autowired
    private LogService logService;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private HttpServletRequest request;

    @Pointcut("execution(public * site.minnan.recordlife.userinterface.fascade..*..*(..))")
    private void controllerLog() {
    }

    @Around("controllerLog()")
    public Object logAroundController(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        long time = System.currentTimeMillis();
        Object[] args = proceedingJoinPoint.getArgs();
        JSONArray jsonArray =
                Arrays.stream(args).collect(JSONArray::new, JSONArray::add, JSONArray::addAll);
        String methodFullName = proceedingJoinPoint.getTarget().getClass().getName()
                + "." + proceedingJoinPoint.getSignature().getName();
        log.info("controller调用{}，参数：{}", methodFullName, jsonArray.toJSONString(0));
        Object retValue = proceedingJoinPoint.proceed();
        time = System.currentTimeMillis() - time;
        String responseString = new JSONObject(retValue).toJSONString(0);
        JwtUser jwtUser = (JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        redisUtil.valueSet("lastOperation::" + jwtUser.getUsername(), DateUtil.formatDateTime(DateTime.now()),
                Duration.ofDays(30));
        log.info("controller调用{}完成，返回数据:{}，用时{}ms", methodFullName, responseString, time);
        //获取操作日志注解
        MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();
        OperateLog operateLog = methodSignature.getMethod().getAnnotation(OperateLog.class);
        if (operateLog != null) {
            try {
                String ip = WebUtil.getIpAddr(request);
                logService.addLog(operateLog, ip);
            } catch (Exception e) {
                log.warn("记录日志异常,{}", e.getMessage());
            }
        }
        return retValue;
    }
}
