package com.example.applicationg

import android.content.Context

import androidx.glance.appwidget.updateAll
import androidx.work.CoroutineWorker

import androidx.work.WorkerParameters


class DataSyncWorker(
    val context: Context,
    val params: WorkerParameters,
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        MyAppWidget().updateAll(context)
        return Result.success()
    }
}