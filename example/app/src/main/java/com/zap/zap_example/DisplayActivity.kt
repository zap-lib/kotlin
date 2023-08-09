package com.zap.zap_example

import android.graphics.Color
import android.graphics.Paint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.zap.zap_example.lib.ZapServer
import com.zap.zap_example.widgets.PlaygroundView

class DisplayActivity : AppCompatActivity() {
    private lateinit var target: PlaygroundView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display)

        target = findViewById(R.id.playground)
        target.add(POINTER_ID, Paint().apply { color = Color.RED })

        ZapServer(this).also { zap ->
            zap.listen { value ->
                val values = value.split(',').map { it.toFloat() }
                target.moveTo(POINTER_ID, x = target.get(POINTER_ID).x - values[0] * 2f)
                target.moveTo(POINTER_ID, y = target.get(POINTER_ID).y + values[1] * 2f)
            }
        }
    }

    companion object {
        private const val POINTER_ID = "0"
    }
}
