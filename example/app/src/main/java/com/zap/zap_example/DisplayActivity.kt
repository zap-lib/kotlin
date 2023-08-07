package com.zap.zap_example

import android.app.Activity
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.View

class DisplayActivity : AppCompatActivity(), SensorEventListener {
    private lateinit var sensorManager: SensorManager
    private lateinit var target: DisplayView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display)

        target = findViewById(R.id.display)
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_GAME)
    }

    override fun onSensorChanged(event: SensorEvent) {
        val padding = 30f

        if (event.sensor.type == Sensor.TYPE_ACCELEROMETER) {
            var x = (target.px + (target.px - event.values[0])) * 0.5f
            var y = (target.py + (target.py + event.values[1])) * 0.5f

            if (x < padding) x = padding
            if (y < padding) y = padding

            target.px = x
            target.py = y
        }
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {}

    override fun onStop() {
        sensorManager.unregisterListener(this);
        super.onStop()
    }
}
