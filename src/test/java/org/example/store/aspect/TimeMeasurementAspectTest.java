package org.example.store.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class TimeMeasurementAspectTest {

    private final TimeMeasurementAspect aspect = new TimeMeasurementAspect();

    @Test
    void logTime_synchronousMethod_measuresExecutionTimeAndReturnsValue() throws Throwable {
        ProceedingJoinPoint joinPoint = mock(ProceedingJoinPoint.class);
        Signature signature = mock(Signature.class);

        when(signature.toString()).thenReturn("testSyncServiceMethod()");
        when(joinPoint.getSignature()).thenReturn(signature);
        when(joinPoint.getKind()).thenReturn("method-execution");
        when(joinPoint.getArgs()).thenReturn(new Object[] { "arg1" });
        when(joinPoint.proceed()).thenAnswer(invocation -> {
            Thread.sleep(10);
            return "syncResult";
        });

        Object result = aspect.logTime(joinPoint);

        assertEquals("syncResult", result);
        verify(joinPoint, times(1)).proceed();
    }

    @Test
    void logTime_monoReturnValue_returnsMono() throws Throwable {
        ProceedingJoinPoint joinPoint = mock(ProceedingJoinPoint.class);
        Signature signature = mock(Signature.class);

        when(signature.toString()).thenReturn("testMonoServiceMethod()");
        when(joinPoint.getSignature()).thenReturn(signature);
        when(joinPoint.getKind()).thenReturn("method-execution");
        when(joinPoint.getArgs()).thenReturn(new Object[] {});
        when(joinPoint.proceed()).thenReturn(Mono.just("monoResult"));

        Object result = aspect.logTime(joinPoint);

        Mono<?> mono = (Mono<?>) result;
        assertEquals("monoResult", mono.block());
    }

    @Test
    void logTime_fluxReturnValue_returnsFlux() throws Throwable {
        ProceedingJoinPoint joinPoint = mock(ProceedingJoinPoint.class);
        Signature signature = mock(Signature.class);

        when(signature.toString()).thenReturn("testFluxServiceMethod()");
        when(joinPoint.getSignature()).thenReturn(signature);
        when(joinPoint.getKind()).thenReturn("method-execution");
        when(joinPoint.getArgs()).thenReturn(new Object[] {});
        when(joinPoint.proceed()).thenReturn(Flux.just("flux1", "flux2"));

        Object result = aspect.logTime(joinPoint);

        Flux<?> flux = (Flux<?>) result;
        assertEquals("flux1", flux.blockFirst());
    }
}
