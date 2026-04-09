package com.sosapp

import android.content.Context
import android.location.Location
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices

fun getLocation(context: Context, callback: (String) -> Unit) {
    val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

    // First try to get last known location
    fusedLocationClient.lastLocation.addOnSuccessListener { location ->
        if (location != null) {
            val loc = "Lat: ${location.latitude}, Lng: ${location.longitude}"
            callback(loc)
        } else {
            // If no last location, request a new one
            requestCurrentLocation(fusedLocationClient, callback)
        }
    }.addOnFailureListener {
        callback("Failed to get location")
    }
}

private fun requestCurrentLocation(fusedLocationClient: FusedLocationProviderClient, callback: (String) -> Unit) {
    val locationRequest = LocationRequest.create().apply {
        priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        interval = 10000 // 10 seconds
        fastestInterval = 5000 // 5 seconds
        numUpdates = 1 // Just one update
    }

    val locationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            val location = locationResult.lastLocation
            if (location != null) {
                val loc = "Lat: ${location.latitude}, Lng: ${location.longitude}"
                callback(loc)
            } else {
                callback("Location not available")
            }
            fusedLocationClient.removeLocationUpdates(this)
        }
    }

    try {
        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null)
    } catch (e: SecurityException) {
        callback("Location permission not granted")
    }
}