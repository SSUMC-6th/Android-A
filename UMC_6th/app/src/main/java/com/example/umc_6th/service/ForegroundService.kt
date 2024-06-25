package com.example.umc_6th.service

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.annotation.RequiresApi
import com.example.umc_6th.R
import com.example.umc_6th.activity.SongActivity
import com.example.umc_6th.model.Constant

class ForegroundService : Service() {

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            showNotification()
        }
        return START_STICKY
    }

    @SuppressLint("ForegroundServiceType")
    @RequiresApi(Build.VERSION_CODES.O)
    private fun showNotification() {
        val notificationIntent = Intent(this, SongActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this,0,notificationIntent,PendingIntent.FLAG_IMMUTABLE
        )
        val notification = Notification
            .Builder(this, Constant.CHANNEL_ID)
            .setContentText("현재 음악이 재생 중 입니다.")
            .setSmallIcon(R.drawable.ic_flo_logo)
            .setContentIntent(pendingIntent)
            .build()

        startForeground(Constant.MUSIC_NOTIFICATION_ID, notification)
    }

    private fun createNotificationChannel() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val serviceChannel = NotificationChannel(
                Constant.CHANNEL_ID,"Service Channel",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            val manager = getSystemService(
                NotificationManager::class.java
            )

            manager.createNotificationChannel(serviceChannel)
        }
    }
}