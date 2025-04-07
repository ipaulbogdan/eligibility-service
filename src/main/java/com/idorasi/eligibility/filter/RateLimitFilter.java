package com.idorasi.eligibility.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.idorasi.eligibility.dto.ApiResponse;
import com.idorasi.eligibility.error.ErrorCode;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
@Slf4j
public class RateLimitFilter extends OncePerRequestFilter {

    private final static int REQUEST_LIMIT = 10; // should be configurable
    private final static long TIME_LIMIT_SECONDS = 60; //should be configurable

    private final Map<String, Bucket> bucketMap;
    private final ObjectMapper objectMapper;

    public RateLimitFilter(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        this.bucketMap = new ConcurrentHashMap<>();
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        var userIp = request.getRemoteAddr();
        var userBucket = bucketMap.computeIfAbsent(userIp, k -> createNewBucket());

        if (userBucket.tryConsume(1)) {
            filterChain.doFilter(request, response);
        } else {
            var errorBody = new ApiResponse.ErrorBody(ErrorCode.RATE_LIMIT_EXCEEDED, ErrorCode.RATE_LIMIT_EXCEEDED.getMessage(), null);

            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.getWriter().write(objectMapper.writeValueAsString(errorBody));
            response.getWriter().flush();
        }
    }

    private Bucket createNewBucket() {
        return Bucket.builder()
                .addLimit(Bandwidth.builder()
                        .capacity(REQUEST_LIMIT)
                        .refillIntervally(10, Duration.ofSeconds(TIME_LIMIT_SECONDS))
                        .build())
                .build();
    }
}
