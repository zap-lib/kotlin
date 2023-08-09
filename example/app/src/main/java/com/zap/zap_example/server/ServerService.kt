package com.zap.zap_example.server

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.os.RemoteCallbackList
import android.util.Log
import com.zap.zap_example.RemoteCallback
import com.zap.zap_example.RemoteListener
import java.net.DatagramPacket
import java.net.DatagramSocket
import java.util.concurrent.atomic.AtomicBoolean

class ServerService : Service() {
    private val callbacks = RemoteCallbackList<RemoteCallback>()

    private val runnable = Thread {
        val socket = DatagramSocket(PORT)
        val buffer = ByteArray(2048)
        val packet = DatagramPacket(buffer, buffer.size)

        if (isWorking.get()) {
            while (true) {
                socket.receive(packet)
                binder.changed(packet.data.decodeToString())
                Thread.sleep(30L)
            }
        }
    }

    private val binder = object : RemoteListener.Stub() {
        override fun registerCallback(callback: RemoteCallback?) {
            callbacks.register(callback)
        }

        override fun unregisterCallback(callback: RemoteCallback?) {
            callbacks.unregister(callback)
        }

        fun changed(value: String) {
            val n = callbacks.beginBroadcast()
            (0 until n).forEach { callbacks.getBroadcastItem(it)?.onChanged(value) }
            callbacks.finishBroadcast()
        }
    }

    override fun onBind(intent: Intent?): IBinder {
        return binder
    }

    override fun onCreate() {
        Log.i(TAG, "server service created")
        isWorking.set(true)
        runnable.start()
    }

    override fun onDestroy() {
        isWorking.set(false)
    }

    companion object {
        val isWorking = AtomicBoolean(false)
        private val TAG = ServerService::class.java.simpleName
        private const val PORT = 65500
    }
}