package com.zap.core

import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.InetAddress
import java.util.concurrent.atomic.AtomicReference

class ZapClient(private val serverAddress: InetAddress) {
    private val socket = DatagramSocket()
    private val value = AtomicReference<ZapData>()

    fun send(rvalue: ZapData) {
        value.set(rvalue)

        Thread {
            val bytes = value.get().toZapString().toByteArray()
            val packet = DatagramPacket(bytes, bytes.size, serverAddress, PORT)
            socket.send(packet)
        }.start()
    }

    fun stop() {
        socket.close()
    }

    companion object {
        private const val PORT = 65500
    }
}