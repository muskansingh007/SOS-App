package com.sosapp

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener

class ShakeDetector : SensorEventListener {

    private var shakeThreshold = 12f
    private var onShakeListener: (() -> Unit)? = null

    fun setOnShakeListener(listener: () -> Unit) {
        onShakeListener = listener
    }

    override fun onSensorChanged(event: SensorEvent) {
        val x = event.values[0]
        val y = event.values[1]
        val z = event.values[2]

        val acceleration = Math.sqrt((x*x + y*y + z*z).toDouble()).toFloat()

        if (acceleration > shakeThreshold) {
            onShakeListener?.invoke()
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
}