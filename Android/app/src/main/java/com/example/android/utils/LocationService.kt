package com.example.android.utils

import android.Manifest
import android.Manifest.permission.POST_NOTIFICATIONS
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.IBinder
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.example.android.NOTIFICATION_CHANNEL_ID
import com.example.android.R
import com.example.android.data.model.LocationNote
import com.example.android.domain.usecase.InsertLocationNoteUseCase
import com.example.android.ui.activity.MainActivity
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
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

const val NOTIFICATION_ID = 1

private lateinit var sessionId: String

@AndroidEntryPoint
class LocationService : Service(){
    private val locationRequest by lazy {
        LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 2000).build()
    }
    private val fusedLocationClient by lazy { LocationServices.getFusedLocationProviderClient(this)}
    private val coroutineScope = CoroutineScope(Dispatchers.IO)
    private var locationCount = 0
    private val maxLocationCount = 10

    @Inject
    lateinit var insertLocationNoteUseCase: InsertLocationNoteUseCase

    private val locationCallback = object : LocationCallback(){
        override fun onLocationResult(locationResult: LocationResult) {
            locationResult.lastLocation?.let { location ->
                coroutineScope.launch {
                    locationCount++
                    val note = LocationNote(
                        title = "$sessionId - Location $locationCount",
                        content = "Lat: ${location.latitude}, Long: ${location.longitude}"
                    )
                    insertLocationNoteUseCase(note)

                    if(locationCount >= maxLocationCount){
                        stopLocationUpdates()
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            stopForeground(STOP_FOREGROUND_REMOVE)
                        } else {
                            stopForeground(true)
                        }
                        stopSelf()
                    }
                }
            }
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        sessionId = SimpleDateFormat("dd/MM/yyyy - HH:mm:ss", Locale.getDefault()).format(Date())
        startServiceOfForeground()
        locationUpdates()
        return START_STICKY
    }

    override fun onDestroy(){
        stopLocationUpdates()
        super.onDestroy()
        coroutineScope.cancel()
    }

    private fun locationUpdates(){
        val fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            stopSelf()
            return
        }
        fusedLocationProviderClient.requestLocationUpdates(
            locationRequest, locationCallback, null
        )
    }

    private fun startServiceOfForeground(){
        val notification = NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Location Updates")
            .setContentText("Fetching location...")
            .setOngoing(true)
            .setSilent(true)
            .build()

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
            if(ContextCompat.checkSelfPermission(this, POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED){
                startForeground(NOTIFICATION_ID, notification)
            }
        }else{
            startForeground(NOTIFICATION_ID, notification)
        }
    }

    private fun stopLocationUpdates(){
        fusedLocationClient.removeLocationUpdates((locationCallback))
    }
    override fun onBind(p0: Intent?): IBinder? {
        return null
    }
}