package com.example.android.service

import android.Manifest
import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.pm.PackageManager
import android.os.Build
import android.os.Looper
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleService
import com.example.android.R
import com.example.android.data.model.LocationNote
import com.example.android.domain.usecase.InsertLocationNoteUseCase
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class LocationService : LifecycleService(){
    private val fusedLocationClient by lazy { LocationServices.getFusedLocationProviderClient(this)}
    private val coroutineScope = CoroutineScope(Dispatchers.IO)
    private var locationCount = 0
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

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val channel = NotificationChannel(NOTIFICATION_CHANNEL_ID, NOTIFICATION_CHANNEL_NAME, NotificationManager.IMPORTANCE_LOW)
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(channel)
        }

        val notificationBuilder = NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
            .setContentTitle("Location Service Running")
            .setContentText("Collecting location data...")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setOngoing(true)

        return notificationBuilder.build()
    }
    
    @SuppressLint("MissingPermission")
    private fun startLocationUpdates(){
        val hasPermission = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        if(!hasPermission){
            stopSelf()
            return
        }
        val locationRequest = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 2000)
            .setMinUpdateIntervalMillis(2000)
            .build()

        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper())
    }

    private val locationCallback = object : LocationCallback(){
        override fun onLocationResult(locationResult: LocationResult) {
            locationResult.lastLocation?.let { location ->
                coroutineScope.launch {
                    locationCount++
                    val note = LocationNote(
                        title = "Location $locationCount",
                        content = "Lat: ${location.latitude}, Long: ${location.longitude}"
                    )
                    insertLocationNoteUseCase(note)

                    if(locationCount >= maxLocationCount){
                        stopLocationUpdates()
                        stopSelf()
                    }
                }
            }
        }
    }

    private fun stopLocationUpdates(){
        fusedLocationClient.removeLocationUpdates((locationCallback))
    }

    companion object {
        const val NOTIFICATION_ID = 1
        const val NOTIFICATION_CHANNEL_ID = "location_channel"
        const val NOTIFICATION_CHANNEL_NAME = "Location Service"
    }
}