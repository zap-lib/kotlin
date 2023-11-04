package com.zap.zap_example

import android.graphics.Color
import android.graphics.Paint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.zap.core.ZapServer
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
            override fun onAccelerometerChanged(id: String, x: Int, y: Int) {
                target.moveTo(POINTER_ID, x = target.get(POINTER_ID).x - x * 2f)
                target.moveTo(POINTER_ID, y = target.get(POINTER_ID).y + y * 2f)
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
