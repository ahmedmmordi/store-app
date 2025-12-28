package org.example.store.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicLong;

@Order(1)
@Component
@Aspect
@Slf4j
public class TimeMeasurementAspect {
    @Around("execution(* org.example.store.service..*(..))")
    @SuppressWarnings("UnusedReturnValue")
    public Object logTime(ProceedingJoinPoint joinPoint) throws Throwable {
        Object returnValue = joinPoint.proceed();
        if (returnValue instanceof Mono<?> mono) return logMono(mono, joinPoint);
        else if (returnValue instanceof Flux<?> flux) return logFlux(flux, joinPoint);
        else {
            long startTime = System.currentTimeMillis();
            logMetrics(joinPoint, startTime);
            return returnValue;
        }
    }

    private <T> Mono<T> logMono(Mono<T> mono, ProceedingJoinPoint joinPoint) {
        AtomicLong startTime = new AtomicLong();
        return mono.doOnSubscribe(s -> logSubscribe(joinPoint, startTime))
                .doFinally(signal -> logMetrics(joinPoint, startTime.get()));
    }

    private <T> Flux<T> logFlux(Flux<T> flux, ProceedingJoinPoint joinPoint) {
        AtomicLong startTime = new AtomicLong();
        return flux.doOnSubscribe(s -> logSubscribe(joinPoint, startTime))
                .doFinally(signal -> logMetrics(joinPoint, startTime.get()));
    }

    private void logSubscribe(ProceedingJoinPoint joinPoint, AtomicLong startTime) {
        startTime.set(System.currentTimeMillis());
        log.info("Subscribed To {}", joinPoint.getSignature());
    }

    private void logMetrics(ProceedingJoinPoint joinPoint, long startTime) {
        log.info("\nMetrics:\n  - {}\n  - For: {}\n  - With Args: ({})\n  - Took: {} ms",
                joinPoint.getKind(),
                joinPoint.getSignature(),
                String.join(", ", Arrays.stream(joinPoint.getArgs()).map(String::valueOf).toArray(String[]::new)),
                System.currentTimeMillis() - startTime
        );
    }
}
