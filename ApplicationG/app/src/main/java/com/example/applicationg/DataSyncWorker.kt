package com.example.applicationg

import android.content.Context
import androidx.work.*
import java.time.Duration
import java.time.LocalDateTime

import java.util.concurrent.TimeUnit


class DataSyncWorker(
    val context: Context,
    val params: WorkerParameters,
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {

        // 检查当前时间是否是奇数小时，如果是则执行任务
        val currentTime = LocalDateTime.now()

        ganZhiDateTimeState.value = MyAppWidget().getGanZhiDateTime()

        WorkManager.getInstance(context).cancelAllWorkByTag("timer-update-p")

        // 计算下一个任务的执行时间
        val nextExecutionTime = calculateNextExecutionTime(currentTime)
        // 创建下一个 OneTimeWorkRequest
        val delayMillis = Duration.between(LocalDateTime.now(), nextExecutionTime).toMillis()
        val dataSyncWorkRequest = OneTimeWorkRequestBuilder<DataSyncWorker>()
            .setInitialDelay(delayMillis, TimeUnit.MILLISECONDS)
            .addTag("timer-update-p")
            .build()

        // 将任务加入 WorkManager 队列
        WorkManager.getInstance(context).enqueue(dataSyncWorkRequest)

        return Result.success()
    }

    private fun calculateNextExecutionTime(currentTime: LocalDateTime): LocalDateTime {
        // 计算下一个奇数小时的时间
        var nextOddHour = currentTime.plusHours(1)
        if (nextOddHour.hour % 2 == 0) {
            nextOddHour = nextOddHour.plusHours(1)
        }
        return nextOddHour
    }
}