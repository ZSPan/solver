package com.pan.solver.filter;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author yemingfeng
 */
@Slf4j
@Component
public class IpLimitFilter implements Filter {

    private static final Long MAX_REQUEST_COUNT = 60L;
    private static final Long GAP = TimeUnit.MINUTES.toMillis(1);

    private Map<String, IpStatus> ipCountMap;

    @Override
    public void init(FilterConfig filterConfig) {
        this.ipCountMap = new ConcurrentHashMap<>();
        log.info("init ip limit filter");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
        FilterChain filterChain) throws IOException, ServletException {
        String ip = servletRequest.getRemoteAddr();
        IpStatus ipStatus = ipCountMap.computeIfAbsent(ip, (unused) ->
            new IpStatus(new AtomicLong(0L), System.currentTimeMillis()));
        if (System.currentTimeMillis() - ipStatus.getLastRequestTs() >= GAP) {
            ipStatus.getCount().set(0L);
            ipStatus.setLastRequestTs(System.currentTimeMillis());
        }
        if (ipStatus.getCount().addAndGet(1) >= MAX_REQUEST_COUNT) {
            log.warn("{} request count greater than: {} in: {}ms, so rejected it",
                ip, ipStatus.getCount(), GAP);
            if (servletResponse instanceof HttpServletResponse) {
                ((HttpServletResponse) servletResponse)
                    .sendError(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
                return;
            }
        }
        log.info("{} request: {} count", ip, ipStatus.getCount());
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        log.info("destroy ip limit filter");
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