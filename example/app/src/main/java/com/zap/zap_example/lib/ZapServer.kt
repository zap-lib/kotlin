package com.zap.zap_example.lib

import android.app.Activity
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import com.zap.zap_example.RemoteCallback
import com.zap.zap_example.RemoteListener

class ZapServer(ctx: Context) {
    private val context: Context = ctx

    fun listen(f: (String) -> Unit) {
        val callback = object : RemoteCallback.Stub() {
            override fun onChanged(value: String) = f(value)
        }

        var listener: RemoteListener? = null
        val connection = object : ServiceConnection {
            override fun onServiceConnected(p0: ComponentName?, service: IBinder?) {
                listener = RemoteListener.Stub.asInterface(service)
                listener?.registerCallback(callback)
            }
            override fun onServiceDisconnected(p0: ComponentName?) {
                listener?.unregisterCallback(callback)
                listener = null
            }
        }

        context.bindService(Intent(context, ServerService::class.java), connection, Context.BIND_AUTO_CREATE)
    }
}