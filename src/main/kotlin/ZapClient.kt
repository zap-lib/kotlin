package com.zap.core

import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.InetAddress
import java.util.UUID
import java.util.concurrent.atomic.AtomicReference

class ZapClient(private val serverAddress: InetAddress) {
    val uuid = UUID.randomUUID().toString()

    private val socket = DatagramSocket()
    private val isSending = AtomicReference(false)

    fun send(data: ZapData) {
        if (!isSending.get()) {
            Thread {
                isSending.set(true)
                val bytes = data.toZapString().toByteArray()
                val packet = DatagramPacket(bytes, bytes.size, serverAddress, PORT)
                socket.send(packet)
                isSending.set(false)
            }.start()
        }
    }

    fun stop() {
        socket.close()
    }

    /**
     * Converts [ZapData] to [ZapString].
     *
     * - [ZapAccelerometerData] has the following format: [ACC](ZapResource.ACCELEROMETER);[x](ZapAccelerometerData.x),[y](ZapAccelerometerData.y) (e.g., `ACC;107,42`)
     */
    private fun ZapData.toZapString(): ZapString {
        val payload = when (this) {
            is ZapAccelerometerData -> "${ZapResource.ACCELEROMETER.key};${this.x},${this.y}"
            else -> throw Exception("Unknown Zap resource")
        }

        return "$uuid;$payload"
    }

    companion object {
        private const val PORT = 65500
    }
}