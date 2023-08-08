package com.zap.zap_example.server

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.os.RemoteCallbackList
import android.util.Log
import com.zap.zap_example.RemoteCallback
import com.zap.zap_example.RemoteListener
import java.io.DataInputStream
import java.io.DataOutputStream
import java.net.ServerSocket
import java.net.Socket
import java.util.concurrent.atomic.AtomicBoolean

class ServerService : Service() {
    private var serverSocket: ServerSocket? = null
    private val callbacks = RemoteCallbackList<RemoteCallback>()

    private val runnable = Runnable {
        var socket: Socket? = null

        try {
            serverSocket = ServerSocket(PORT)

            while (isWorking.get()) {
                serverSocket?.let {
                    socket = it.accept()

                    val inputStream = DataInputStream(socket?.getInputStream())
                    val outputStream = DataOutputStream(socket?.getOutputStream())

                    object : Thread() {
                        override fun run() {
                            while (true) {
                                try {
                                    if (inputStream.available() > 0) {
                                        val received = inputStream.readUTF()
                                        Log.i(TAG, received.toString())
                                        received?.let { value -> binder.changed(value) }
                                        sleep(1000L)
                                    }
                                } catch (e: Exception) {
                                    inputStream.close()
                                    outputStream.close()
                                    throw e
                                }
                            }
                        }
                    }.start()
                }
            }
        } catch (e: Exception) {
            socket?.close()
            throw e
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
        Thread(runnable).start()
    }

    override fun onDestroy() {
        isWorking.set(false)
    }

    companion object {
        val isWorking = AtomicBoolean(false)
        private val TAG = ServerService::class.java.simpleName
        private const val PORT = 9876
    }
}