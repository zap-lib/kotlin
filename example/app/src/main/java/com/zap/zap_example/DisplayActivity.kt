package com.zap.zap_example

import android.graphics.Color
import android.graphics.Paint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.zap.zap_example.lib.*
import com.zap.zap_example.widgets.PlaygroundView

class DisplayActivity : AppCompatActivity() {
    private lateinit var target: PlaygroundView
    private lateinit var zap: ZapServer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display)

        target = findViewById(R.id.playground)
        target.add(POINTER_ID, Paint().apply { color = Color.RED })

        zap = object : ZapServer() {
            override fun onAccelerometerChanged(id: String, value: String) {
                val values = value.split(',').map { it.toFloat() }
                target.moveTo(POINTER_ID, x = target.get(POINTER_ID).x - values[0] * 2f)
                target.moveTo(POINTER_ID, y = target.get(POINTER_ID).y + values[1] * 2f)
            }
        }.also { it.start() }
    }

    override fun onStop() {
        super.onStop()
        zap.stop()
    }

    companion object {
        private const val POINTER_ID = "0"
    }
}
