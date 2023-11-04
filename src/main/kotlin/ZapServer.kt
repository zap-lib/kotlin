package com.zap.core

import java.lang.Exception
import java.net.DatagramPacket
import java.net.DatagramSocket
import java.util.concurrent.atomic.AtomicBoolean

open class ZapServer {
    private var id = "unknown"
    private var isRunning = AtomicBoolean(false)

    private val thread = Thread {
        val socket = DatagramSocket(PORT)
        val buffer = ByteArray(2048)
        val packet = DatagramPacket(buffer, buffer.size)

        while (true) {
            socket.receive(packet)
            when (val decoded = packet.data.decodeToString().toZapData()) {
                is ZapAccelerometerData -> onAccelerometerChanged(id, decoded.x, decoded.y)
            }
        }
    }

    fun start() {
        isRunning.set(true)
        thread.start()
    }

    fun stop() {
        isRunning.set(false)
        thread.interrupt()
    }

    open fun onAccelerometerChanged(id: String, x: Int, y: Int) {
        throw Exception("Not yet implemented")
    }

    companion object {
        private const val PORT = 65500
    }
}