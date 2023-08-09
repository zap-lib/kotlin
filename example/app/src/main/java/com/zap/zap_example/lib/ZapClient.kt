package com.zap.zap_example.lib

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import androidx.appcompat.app.AppCompatActivity

class ZapClient(ctx: Context) {
    private var context: Context = ctx
    private var service: ClientService? = null

    init {
        val connection = object : ServiceConnection {
            override fun onServiceConnected(name: ComponentName?, binder: IBinder?) {
                service = (binder as ClientService.LocalBinder).service
            }

            override fun onServiceDisconnected(p0: ComponentName?) {
                service = null
            }
        }
        Intent(context, ClientService::class.java).also { intent ->
            context.bindService(intent, connection, AppCompatActivity.BIND_AUTO_CREATE)
        }
    }

    fun send(value: String) {
        service?.send(value)
    }
}