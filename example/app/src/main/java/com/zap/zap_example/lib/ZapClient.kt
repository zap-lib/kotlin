package com.zap.zap_example.lib

import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.InetAddress
import java.util.concurrent.atomic.AtomicReference

class ZapClient {
    private val socket = DatagramSocket()
    private val value = AtomicReference<ZapData>()

    fun send(rvalue: ZapData) {
        value.set(rvalue)

        Thread {
            val bytes = value.get().toJson().toByteArray()
            val packet = DatagramPacket(bytes, bytes.size, IP, PORT)
            socket.send(packet)
        }.start()
    }

    fun stop() {
        socket.close()
    }

    companion object {
        private val TAG = ZapClient::class.java.simpleName
        private val IP = InetAddress.getByName("192.168.0.22")
        private const val PORT = 65500
    }
}