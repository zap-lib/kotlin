package com.zap.zap_example

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

        ZapServer(this).also { zap ->
            zap.listen { value ->
                val values = value.split(',').map { it.toFloat() }
                target.px = target.px - values[0] * 2f
                target.py = target.py + values[1] * 2f
            }
        }
    }
}
