package com.zap.core

import java.lang.Exception
import java.net.DatagramPacket
import java.net.DatagramSocket
import java.util.concurrent.atomic.AtomicBoolean

open class ZapServer {
    private var isRunning = AtomicBoolean(false)

    private val thread = Thread {
        val socket = DatagramSocket(PORT)
        val buffer = ByteArray(2048)
        val packet = DatagramPacket(buffer, buffer.size)

        while (true) {
            socket.receive(packet)
            val received = packet.data.decodeToString().toZapData()
            when (val data = received.data) {
                is ZapAccelerometerData -> onAccelerometerChanged(received.uuid, data.x, data.y)
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

    open fun onAccelerometerChanged(uuid: String, x: Int, y: Int) {
        throw Exception("Not yet implemented")
    }

    /**
     * Parses [ZapString] to [ZapData].
     */
    private fun ZapString.toZapData(): ZapDataFromClient {
        val (uuid, resource, value) = this.split(";")
        return when (resource) {
            ZapResource.ACCELEROMETER.key -> {
                val (x, y) = value.split(",").map { it.toInt() }
                ZapDataFromClient(uuid, ZapAccelerometerData(x, y))
            }
            else -> throw Exception("Unknown Zap resource")
        }
    }

    private data class ZapDataFromClient(val uuid: String, val data: ZapData)

    companion object {
        private const val PORT = 65500
    }
}