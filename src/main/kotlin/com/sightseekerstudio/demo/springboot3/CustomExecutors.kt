package com.kintone.client.util.concurrent

import org.slf4j.LoggerFactory
import java.util.concurrent.*

object CustomExecutors {

    /**
     * Customized version of [java.util.concurrent.Executors.newCachedThreadPool]
     */
    fun newCachedThreadPool(
        threadFactory: ThreadFactory = Executors.defaultThreadFactory(),
        exceptionHandler: (Throwable) -> Unit = ::loggingExceptionHandler,
    ): ExecutorService {
        return CustomThreadPoolExecutor(
            0,
            Integer.MAX_VALUE,
            60L,
            TimeUnit.SECONDS,
            SynchronousQueue(),
            threadFactory,
            exceptionHandler,
        )
    }

    private val log = LoggerFactory.getLogger(CustomExecutors::class.java)
    private fun loggingExceptionHandler(t: Throwable) {
        log.error("Uncaught exception", t)
    }
}
