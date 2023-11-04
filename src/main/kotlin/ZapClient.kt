package com.zap.core

import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.InetAddress
import java.util.concurrent.atomic.AtomicReference

class ZapClient(private val serverAddress: InetAddress) {
    private val socket = DatagramSocket()
    private val isSending = AtomicReference<Boolean>(false)

    fun send(value: ZapData) {
        if (!isSending.get()) {
            Thread {
                isSending.set(true)
                val bytes = value.toZapString().toByteArray()
                val packet = DatagramPacket(bytes, bytes.size, serverAddress, PORT)
                socket.send(packet)
                isSending.set(false)
            }.start()
        }
    }

    fun stop() {
        socket.close()
    }

    companion object {
        private const val PORT = 65500
    }
}
