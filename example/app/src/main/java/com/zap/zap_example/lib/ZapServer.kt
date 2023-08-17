package com.zap.zap_example.lib

import android.content.Context
import android.util.Log
import java.net.DatagramPacket
import java.net.DatagramSocket
import java.util.concurrent.atomic.AtomicBoolean

data class ZapGps(val latitude: Float, val longitude: Float)
data class ZapAccelerometer(val x: Float, val y: Float, val z: Float)

open class ZapServer {
    private var id = "unknown"
    private var isRunning = AtomicBoolean(false)

    private val runnable = Thread {
        val socket = DatagramSocket(PORT)
        val buffer = ByteArray(2048)
        val packet = DatagramPacket(buffer, buffer.size)

        while (true) {
            socket.receive(packet)
            // TODO: Check the resource type
            onAccelerometerChanged(id, packet.data.decodeToString())
            Thread.sleep(30L)
        }
    }

    fun start() {
        isRunning = AtomicBoolean(true)
        runnable.start()
    }

    fun stop() {
        isRunning = AtomicBoolean(false)
        runnable.interrupt()
    }

    open fun onGpsChanged(id: String, value: ZapGps) {
        Log.w(TAG, "Not yet implemented")
    }

    open fun onAccelerometerChanged(id: String, value: String) {
        Log.w(TAG, "Not yet implemented")
    }

    companion object {
        private val TAG = ZapServer::class.java.simpleName
        private const val PORT = 65500
    }
}