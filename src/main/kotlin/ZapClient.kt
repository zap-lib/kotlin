package com.github.zap_lib

import com.github.zap_lib.models.ZappObject
import com.github.zap_lib.models.ZappHeader
import com.github.zap_lib.models.Zapable
import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.InetAddress
import java.util.concurrent.atomic.AtomicReference

/**
 * A client that sends data to server.
 *
 * @property serverAddress An IP address of the device running ZapServer.
 */
class ZapClient(private val serverAddress: InetAddress) {
    private val socket = DatagramSocket()
    private val isSending = AtomicReference(false)

    /**
     * Send given [Zapable] object to the server.
     *
     * @param obj An object to send.
     */
    fun send(obj: Zapable) {
        if (!isSending.get()) {
            Thread {
                isSending.set(true)

                val byteBuffer = ZappObject(
                    header = ZappHeader(obj.resource),
                    payload = obj.toPayload(),
                ).toByteBuffer()
                byteBuffer.clear()

                val bytes = ByteArray(byteBuffer.capacity())
                byteBuffer.get(bytes)

                val packet = DatagramPacket(bytes, bytes.size, serverAddress, PORT)
                socket.send(packet)

                isSending.set(false)
            }.start()
        }
    }

    /**
     * Close the socket.
     */
    fun stop() {
        socket.close()
    }

    companion object {
        private const val PORT = 65500
    }
}