package com.kintone.client.util.concurrent

import java.util.concurrent.BlockingQueue
import java.util.concurrent.CancellationException
import java.util.concurrent.ExecutionException
import java.util.concurrent.Future
import java.util.concurrent.ThreadFactory
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit

class CustomThreadPoolExecutor(
    corePoolSize: Int,
    maximumPoolSize: Int,
    keepAliveTime: Long,
    unit: TimeUnit,
    workQueue: BlockingQueue<Runnable>,
    threadFactory: ThreadFactory,
    private val exceptionHandler: (Throwable) -> Unit,
) : ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory) {

    override fun afterExecute(r: Runnable?, t: Throwable?) {
        super.afterExecute(r, t)
        extractException(r, t)?.let { it ->
            exceptionHandler(it)
        }
    }

    private fun extractException(r: Runnable?, t: Throwable?): Throwable? {
        if (t != null) {
            return t
        }
        if (r is Future<*> && r.isDone) {
            return try {
                r.get()
                null
            } catch (ce: CancellationException) {
                ce
            } catch (ee: ExecutionException) {
                ee.cause
            } catch (ie: InterruptedException) {
                Thread.currentThread().interrupt()
                null
            }
        }
        return null
    }
}
