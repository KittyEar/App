package com.example.applicationg

import android.content.Context
import android.content.Intent
import androidx.annotation.CallSuper
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetReceiver
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit

class MyAppWidgetReceiver : GlanceAppWidgetReceiver() {
    override val glanceAppWidget: GlanceAppWidget = MyAppWidget()
    @CallSuper
    override fun onReceive(context: Context, intent: Intent) {
        super.onReceive(context, intent)

        val dataSyncWorkRequest = OneTimeWorkRequestBuilder<DataSyncWorker>()
            .addTag("timer-update")
            .build()

        WorkManager.getInstance(context).enqueue(dataSyncWorkRequest)
    }
}