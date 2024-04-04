package com.example.applicationg

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.os.SystemClock
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.glance.appwidget.updateAll
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import java.util.*
import java.util.concurrent.TimeUnit


class LongRunningService : Service() {
    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        Thread {
            Log.d(TAG, "executed at " + Date())
//            LaunchedEffect(Unit) {
//                MyAppWidget().updateAll(this@LongRunningService)
//                println("@LongRunningService")
//            }
        }.start()
        val dataSyncWorkRequest = OneTimeWorkRequestBuilder<DataSyncWorker>()
            //.setInitialDelay(5L, TimeUnit.SECONDS)
            .build()
        WorkManager.getInstance(this@LongRunningService).enqueue(dataSyncWorkRequest)

        val manager = getSystemService(ALARM_SERVICE) as AlarmManager
        val delayMillis = 10 * 1000 // 定时10s
//        val currentTime = LocalDateTime.now()
//        var nextOddHour = currentTime.plusHours(1)
//        if (nextOddHour.hour % 2 == 0) { // 如果下一个小时是偶数小时，跳过一个小时
//            nextOddHour = nextOddHour.plusHours(1)
//        }
//
//        nextOddHour = nextOddHour.withMinute(0).withSecond(0).withNano(0)
//        val delayMillis = Duration.between(currentTime, nextOddHour).toMillis()

        val trigerAtTime = SystemClock.elapsedRealtime() + delayMillis
        val i = Intent(this, AlarmReceiver::class.java)
        val pi = PendingIntent.getBroadcast(this, 0, i, 0)
        manager[AlarmManager.ELAPSED_REALTIME_WAKEUP, trigerAtTime] = pi
        return super.onStartCommand(intent, flags, startId)
    }

    companion object {
        private const val TAG = "LongRunningService"
    }
}