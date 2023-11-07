package com.zap_lib.core

import com.zap_lib.core.models.ZapDatagram
import com.zap_lib.core.models.ZapHeader
import com.zap_lib.core.models.Zapable
import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.InetAddress
import java.util.UUID
import java.util.concurrent.atomic.AtomicReference

class ZapClient(private val serverAddress: InetAddress) {
    val id = UUID.randomUUID().toString()

    private val socket = DatagramSocket()
    private val isSending = AtomicReference(false)

    fun send(obj: Zapable) {
        if (!isSending.get()) {
            Thread {
                isSending.set(true)

                val bytes = ZapDatagram(
                    header = ZapHeader(id, obj.resource),
                    payload = obj.toPayload(),
                ).toString().toByteArray()

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