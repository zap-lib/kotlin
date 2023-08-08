package com.zap.zap_example.server

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import com.zap.zap_example.R
import com.zap.zap_example.RemoteCallback
import com.zap.zap_example.RemoteListener

class DisplayActivity : AppCompatActivity() {
    private lateinit var target: DisplayView

    private var serverService: RemoteListener? = null

    private val callback = object : RemoteCallback.Stub() {
        override fun onChanged(value: String) {
            val padding = 30f
            val values = value.split(',').map { it.toFloat() }
            Log.i("asdf", values.toString())

            var x = (target.px + (target.px - values[0])) * 0.5f
            var y = (target.py + (target.py + values[1])) * 0.5f

            if (x < padding) x = padding
            if (y < padding) y = padding

            target.px = x
            target.py = y
        }
    }

    private val connection = object : ServiceConnection {
        override fun onServiceConnected(p0: ComponentName?, service: IBinder?) {
            serverService = RemoteListener.Stub.asInterface(service)
            serverService?.registerCallback(callback)
        }

        override fun onServiceDisconnected(p0: ComponentName?) {
            serverService = null
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display)

        target = findViewById(R.id.display)
    }

    override fun onStart() {
        super.onStart()
        if (!ServerService.isWorking.get()) {
            bindService(Intent(this, ServerService::class.java), connection, Context.BIND_AUTO_CREATE)
        }
    }
}
