package com.zap.zap_example.lib

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log
import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.InetAddress
import java.util.concurrent.atomic.AtomicBoolean

class ClientService : Service() {
    private val runnable = Thread {
        val socket = DatagramSocket()

        if (isWorking.get()) {
            while (true) {
                valueToSend?.let {
                    val bytes = it.toByteArray()
                    val packet = DatagramPacket(bytes, bytes.size, IP, PORT)
                    socket.send(packet)
                }

                Thread.sleep(30L)
            }
        }
    }

    private val binder = LocalBinder()
    inner class LocalBinder : Binder() {
        val service: ClientService = this@ClientService
    }

    private var valueToSend: String? = null
    fun send(value: String) {
        valueToSend = value
    }

    override fun onBind(intent: Intent?): IBinder {
        return binder
    }

    override fun onCreate() {
        Log.i(TAG, "client service created")
        isWorking.set(true)
        runnable.start()
    }

    override fun onDestroy() {
        isWorking.set(false)
    }

    companion object {
        val isWorking = AtomicBoolean(false)
        private val TAG = ClientService::class.java.simpleName
        private val IP = InetAddress.getByName("192.168.0.27")
        private const val PORT = 65500
    }
}