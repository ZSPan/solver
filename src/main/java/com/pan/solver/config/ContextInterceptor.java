package com.pan.solver.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author yemingfeng
 */
@Slf4j
@Component
public class ContextInterceptor implements HandlerInterceptor {

    private static final Long MAX_REQUEST_COUNT = 10L;
    private static final Long GAP = TimeUnit.MINUTES.toMillis(1);

    private final Map<String, IpStatus> ipCountMap;

    public ContextInterceptor() {
        this.ipCountMap = new ConcurrentHashMap<>();
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) {
        String ip = request.getRemoteAddr();
        IpStatus ipStatus = ipCountMap.computeIfAbsent(ip, (unused) ->
                new IpStatus(new AtomicLong(0L), System.currentTimeMillis()));
        if (System.currentTimeMillis() - ipStatus.getLastRequestTs() >= GAP) {
            ipStatus.getCount().set(0L);
            ipStatus.setLastRequestTs(System.currentTimeMillis());
        }
        if (ipStatus.getCount().addAndGet(1) >= MAX_REQUEST_COUNT) {
            log.warn("{} request count greater than: {} in: {}ms, so rejected it",
                    ip, ipStatus.getCount(), GAP);
            throw new IpLimitException();
        }
        log.info("{} request: {} count", ip, ipStatus.getCount());
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response,
                           Object handler, ModelAndView modelAndView) {

    }

    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response, Object handler, Exception ex) {

    }

    @Data
    @AllArgsConstructor
    private class IpStatus {

        private AtomicLong count;
        private Long lastRequestTs;
    }

    @ResponseStatus(value = HttpStatus.SERVICE_UNAVAILABLE)
    private class IpLimitException extends RuntimeException {

    }
}