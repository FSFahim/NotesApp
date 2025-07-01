package com.example.android.service

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.location.LocationRequest
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.lifecycle.LifecycleService
import com.example.android.R
import com.example.android.domain.usecase.InsertLocationNoteUseCase
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import javax.inject.Inject

@AndroidEntryPoint
class LocationService : LifecycleService(){
    private val fusedLocationClient by lazy { LocationServices.getFusedLocationProviderClient(this)}
    private val coroutineScope = CoroutineScope(Dispatchers.IO)
    private val locationCount = 0
    private val maxLocationCount = 10

    @Inject
    lateinit var insertLocationNoteUseCase: InsertLocationNoteUseCase

    override fun onCreate(){
        super.onCreate()
        startForegroundServiceNotification()
        startLocationUpdates()
    }

    override fun onDestroy(){
        super.onDestroy()
        coroutineScope.cancel()
    }

    private fun startForegroundServiceNotification(){
        val notification = createNotification()
        startForeground(NOTIFICATION_ID, notification)
    }

    private fun createNotification() : Notification{
        val channelId = "location_channel"
        val channelName = "Location Service"

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val channel = NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_LOW)
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(channel)
        }

        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setContentTitle("Location Service Running")
            .setContentText("Collecting location data...")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setOngoing(true)

        return notificationBuilder.build()
    }
    
    @SuppressLint
    private fun startLocationUpdates(){
        val locationRequest = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 2000)
            .setMinUpdateIntervalMillis(2000)
            .build()
    }


}