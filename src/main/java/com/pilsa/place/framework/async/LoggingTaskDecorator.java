package com.pilsa.place.framework.async;

import org.slf4j.MDC;
import org.springframework.core.task.TaskDecorator;

import java.util.Map;

/**
 * ThreadPoolTaskExecutor에 의해 관리되는 쓰레드가 매번 사용될 때마다 실행 전후에 끼어들어 동작을 제어할 수 있다.
 * 자신을 호출한 쓰레드의 MDC 정보를 복제함으로서 동일한 MDC 정보를 가진채 쓰레드를 실행하게 한다.
 *
 * @author : pilsa_internet
 * @since : 2021-09-14 오후 6:19
 */
public class LoggingTaskDecorator implements TaskDecorator {

    @Override
    public Runnable decorate(Runnable runnable) {

        Map<String, String> callerThreadContext = MDC.getCopyOfContextMap();
        return () -> {
            // 아래 값이 null 인 케이스도 있을테니 null 처리
            if(callerThreadContext != null){
                MDC.setContextMap(callerThreadContext);
            }
            runnable.run();
        };
    }
}
