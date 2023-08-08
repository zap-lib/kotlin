package com.zap.zap_example.client

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import com.zap.zap_example.server.DisplayView
import com.zap.zap_example.R

class ControllerActivity : AppCompatActivity(), SensorEventListener {
    private lateinit var control: DisplayView
    private lateinit var sensorManager: SensorManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_controller)

        control = findViewById(R.id.display)
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
    }

    override fun onSensorChanged(event: SensorEvent) {
        if (event.sensor.type == Sensor.TYPE_ACCELEROMETER) {

        }
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {}

    override fun onStart() {
        super.onStart()
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_GAME)
        if (!ClientService.isWorking.get()) {
            startService(Intent(this, ClientService::class.java))
        }
    }

    override fun onStop() {
        super.onStop()
        sensorManager.unregisterListener(this)
    }
}