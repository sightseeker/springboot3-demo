package com.sightseekerstudio.demo.springboot3

import io.micrometer.tracing.Tracer
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect

@Aspect
class ScheduledSpanAspect(private val tracer: Tracer) {

    @Throws(Throwable::class)
    @Around("@annotation(org.springframework.scheduling.annotation.Scheduled)")
    fun traceScheduledMethod(pjp: ProceedingJoinPoint): Any? {
        // span がある場合はそのままメソッドを実行
        tracer.currentSpan()?.let {
            return pjp.proceed()
        }
        // span がない場合は新しい span を作成してメソッドを実行する
        val span = tracer.nextSpan()
        try {
            tracer.withSpan(span).use { return pjp.proceed() }
        } catch (e: Throwable) {
            span.error(e)
            throw e
        } finally {
            span.end()
        }
    }
}
