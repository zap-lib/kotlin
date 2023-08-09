package com.zap.zap_example.client

import android.animation.ObjectAnimator
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.graphics.Color
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import com.zap.zap_example.R
import com.zap.zap_example.views.PlaygroundView

class ControllerActivity : AppCompatActivity(), SensorEventListener {
    private lateinit var control: PlaygroundView
    private lateinit var sensorManager: SensorManager

    private var service: ClientService? = null
    private val connection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, binder: IBinder?) {
            service = (binder as ClientService.LocalBinder).service
        }

        override fun onServiceDisconnected(p0: ComponentName?) {
            service = null
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_controller)

        control = findViewById(R.id.playground)
        control.paint.color = Color.BLUE

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
    }

    override fun onSensorChanged(event: SensorEvent) {
        if (event.sensor.type == Sensor.TYPE_ACCELEROMETER) {
            ObjectAnimator.ofFloat(control, "translationX", event.values[0] * -50).apply { duration = 30 }.start()
            ObjectAnimator.ofFloat(control, "translationY", event.values[1] * 50).apply { duration = 30 }.start()
            service?.send(event.values.joinToString(","))
        }
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {}

    override fun onStart() {
        super.onStart()
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_GAME)
        if (!ClientService.isWorking.get()) {
            Intent(this, ClientService::class.java).also { intent ->
                bindService(intent, connection, BIND_AUTO_CREATE)
            }
        }
    }

    override fun onStop() {
        super.onStop()
        unbindService(connection)
        service = null
        sensorManager.unregisterListener(this)
    }
}