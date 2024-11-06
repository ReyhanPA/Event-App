package com.example.myfundamentalsubmission.worker

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.myfundamentalsubmission.R
import com.example.myfundamentalsubmission.data.remote.retrofit.ApiConfig
import com.example.myfundamentalsubmission.ui.MainActivity
import kotlinx.coroutines.runBlocking

class DailyReminderWorker(
    context: Context,
    params: WorkerParameters,
) : Worker(context, params) {
    companion object {
        const val NOTIFICATION_ID = 1
        const val CHANNEL_ID = "channel_01"
        const val CHANNEL_NAME = "Daily Reminder Notification"
    }

    override fun doWork(): Result {
        return runBlocking {
            try {
                val apiService = ApiConfig.getApiService()
                val response = apiService.getEvents(active = "-1", keyword = null)
                val event = response.listEvents.firstOrNull()

                if (event != null) {
                    showNotification(event.name ?: "Upcoming Event", event.beginTime)
                    Log.d("DailyReminderWorker", "Notification shown for event: ${event.name}")
                    Result.success()
                } else {
                    Log.d("DailyReminderWorker", "No events found.")
                    Result.failure()
                }
            } catch (e: Exception) {
                Log.e("DailyReminderWorker", "Error fetching events: ${e.message}", e)
                Result.retry()
            }
        }
    }

    private fun showNotification(eventName: String, eventTime: String? = null) {
        val intent = Intent(applicationContext, MainActivity::class.java)
        val pendingIntent: PendingIntent = PendingIntent.getActivity(
            applicationContext,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val builder = NotificationCompat.Builder(applicationContext, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle(eventName)
            .setContentText(eventTime)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        val notificationManager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(channel)
        }

        notificationManager.notify(NOTIFICATION_ID, builder.build())
    }
}