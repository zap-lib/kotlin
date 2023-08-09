package com.zap.zap_example

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Color
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.zap.zap_example.lib.ZapClient
import com.zap.zap_example.widgets.PlaygroundView

class ControllerActivity : AppCompatActivity(), SensorEventListener {
    private lateinit var control: PlaygroundView
    private lateinit var sensorManager: SensorManager
    private lateinit var zap: ZapClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_controller)

        control = findViewById(R.id.playground)
        control.paint.color = Color.BLUE

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager

        zap = ZapClient(this)
    }

    override fun onSensorChanged(event: SensorEvent) {
        if (event.sensor.type == Sensor.TYPE_ACCELEROMETER) {
            ObjectAnimator.ofFloat(control, "translationX", event.values[0] * -20).apply { duration = 30 }.start()
            ObjectAnimator.ofFloat(control, "translationY", event.values[1] * 20).apply { duration = 30 }.start()
            zap.send(event.values.joinToString(","))
        }
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {}

    override fun onStart() {
        super.onStart()
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_GAME)
    }

    override fun onStop() {
        super.onStop()
        sensorManager.unregisterListener(this)
    }
}