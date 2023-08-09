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
import com.zap.zap_example.views.PlaygroundView

class DisplayActivity : AppCompatActivity() {
    private lateinit var target: PlaygroundView

    private val callback = object : RemoteCallback.Stub() {
        override fun onChanged(value: String) {
            val values = value.split(',').map { it.toFloat() }
            target.px = target.px - values[0] * 2f
            target.py = target.py + values[1] * 2f
        }
    }

    private var serverService: RemoteListener? = null
    private val connection = object : ServiceConnection {
        override fun onServiceConnected(p0: ComponentName?, service: IBinder?) {
            serverService = RemoteListener.Stub.asInterface(service)
            serverService?.registerCallback(callback)
        }
        override fun onServiceDisconnected(p0: ComponentName?) {
            serverService?.unregisterCallback(callback)
            serverService = null
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display)

        target = findViewById(R.id.playground)
    }

    override fun onStart() {
        super.onStart()
        if (!ServerService.isWorking.get()) {
            bindService(Intent(this, ServerService::class.java), connection, Context.BIND_AUTO_CREATE)
        }
    }
}
