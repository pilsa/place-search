package com.pilsa.place.framework.servlet.filter;


import com.pilsa.place.common.constant.ApiConstant;
import com.pilsa.place.framework.properties.SystemProperties;
import com.pilsa.place.framework.util.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;

import javax.servlet.*;
import java.io.IOException;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicInteger;


@Slf4j
public class FilterConfig implements Filter {

    @Override
    public void init(javax.servlet.FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        /** ======================================================================================
         * 거래에 대한 관리와 트레킹을 위해서 transactionId 를 생성한다.
        ======================================================================================= */
        String transactionId = DateUtil.dateToString(LocalDateTime.now(),DateUtil.yyyyMMddHHmmssSSS)
                .concat(SystemProperties.INSTANCE_ID)
                .concat(new DecimalFormat("00000").format(nextMessageId()));
        MDC.put(ApiConstant.TRANSACTION_ID,transactionId);
        chain.doFilter(request,response);
    }

    /*======================================================================================
     * AtomicInteger 클래스는 멀티쓰레드 환경에서 동시성을 보장한다.
    ======================================================================================*/
    private final AtomicInteger tranSerialNumber = new AtomicInteger();
    private final AtomicInteger today = new AtomicInteger();
    private static final int MAX_SEQUENCE = 99999;
    private int nextMessageId() {
        int date = Integer.parseInt(DateUtil.getDateString());
        if(today.get() != date){
            today.set(date);
            tranSerialNumber.set(1);
        }

        int sequence = tranSerialNumber.getAndIncrement();
        if (sequence > MAX_SEQUENCE) {
            tranSerialNumber.set(1);
            return sequence - MAX_SEQUENCE;
        } else {
            return sequence;
        }
    }

    @Override
    public void destroy() {
    }

}