package com.example.weather_app.presentation.ui.screen.detail

import android.annotation.SuppressLint
import android.content.Context
import android.location.Geocoder
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import java.util.Locale

class LocationHelper(private val context: Context) {
    private val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

    fun getCurrentLocation(onLocationFound: (String) -> Unit, onError: () -> Unit) {
        try {
            val locationRequest = LocationRequest.create().apply {
                priority = LocationRequest.PRIORITY_HIGH_ACCURACY
                interval = 1000
            }

            fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                if (location != null) {
                    val geocoder = Geocoder(context, Locale.getDefault())
                    val addresses = geocoder.getFromLocation(location.latitude, location.longitude, 1)
                    val city = addresses?.firstOrNull()?.locality
                    if (!city.isNullOrEmpty()) {
                        onLocationFound(city)
                    } else {
                        onError()
                    }
                } else {
                    onError()
                }
            }.addOnFailureListener {
                onError()
            }
        } catch (e: SecurityException) {
            onError()
        }
    }
}
