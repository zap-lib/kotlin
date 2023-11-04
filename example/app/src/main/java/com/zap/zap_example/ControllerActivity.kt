package com.zap.zap_example

import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.zap.core.ZapAccelerometerData
import com.zap.core.ZapClient
import com.zap.zap_example.widgets.PlaygroundView
import java.net.InetAddress

class ControllerActivity : AppCompatActivity(), SensorEventListener {
    private lateinit var control: PlaygroundView
    private lateinit var sensorManager: SensorManager
    private lateinit var zap: ZapClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_controller)

        control = findViewById(R.id.playground)
        control.add(POINTER_ID, Paint().apply { color = Color.BLUE })

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager

        zap = ZapClient(InetAddress.getByName("192.168.0.22"))
    }

    override fun onSensorChanged(event: SensorEvent) {
        if (event.sensor.type == Sensor.TYPE_ACCELEROMETER) {
            control.x = event.values[0] * -30
            control.y = event.values[1] * 30
            zap.send(ZapAccelerometerData(event.values[0].toInt(), event.values[1].toInt()))
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
        zap.stop()
    }

    companion object {
        private const val POINTER_ID = "0"
    }
}