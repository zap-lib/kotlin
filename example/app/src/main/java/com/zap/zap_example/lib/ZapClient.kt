package com.zap.zap_example.lib

import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.InetAddress
import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.atomic.AtomicReference

class ZapClient {
    private var isRunning = AtomicBoolean(false)
    private var value = AtomicReference<String?>(null)
    private val thread = Thread {
        val socket = DatagramSocket()

        while (isRunning.get()) {
            value.get()?.let {
                val bytes = it.toByteArray()
                val packet = DatagramPacket(bytes, bytes.size, IP, PORT)
                socket.send(packet)
            }

            value = AtomicReference(null)
            Thread.sleep(30L)
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

    fun send(nvalue: String) {
        value.set(nvalue)
    }

    companion object {
        private val TAG = ZapClient::class.java.simpleName
        private val IP = InetAddress.getByName("192.168.0.27")
        private const val PORT = 65500
    }
}