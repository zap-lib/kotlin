package com.github.zap_lib

import com.github.zap_lib.models.ZappObject
import com.github.zap_lib.models.ZappHeader
import com.github.zap_lib.resources.ZapAccelerometer
import com.github.zap_lib.resources.ZapResource
import com.github.zap_lib.resources.ZapText
import com.github.zap_lib.resources.ZapUiEvent
import java.lang.Exception
import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.InetAddress
import java.nio.ByteBuffer
import java.util.concurrent.atomic.AtomicBoolean

data class DgramInfo(
    val address: InetAddress,
    val port: Int,
)

/**
 * Meta information of received ZAPP Object.
 *
 * @property dgram Datagram information such as address and port.
 * @property header ZAPP Header.
 */
data class MetaInfo(
    val dgram: DgramInfo,
    val header: ZappHeader,
)

/**
 * A server that receives data from client.
 */
open class ZapServer {
    private var port: Int = DEFAULT_PORT

    private var isRunning = AtomicBoolean(false)

    private val thread = Thread {
        val socket = DatagramSocket(this.port)
        val buffer = ByteArray(2048)
        val packet = DatagramPacket(buffer, buffer.size)

        while (isRunning.get()) {
            socket.receive(packet)

            val (header, payload) = ZappObject.from(ByteBuffer.wrap(packet.data))
            val info = MetaInfo(DgramInfo(packet.address, packet.port), header)

            when (header.resource) {
                ZapResource.ACCELEROMETER ->
                    onAccelerometerChanged(info, ZapAccelerometer.from(payload))
                ZapResource.UI_EVENT ->
                    onUIEventReceived(info, ZapUiEvent.from(payload))
                ZapResource.TEXT ->
                    onTextReceived(info, ZapText.from(payload))
            }
        }
    }

    /**
     * Start listening the transmitted data from clients on the given port.
     *
     * @param port A port number for receiving data (default: `65500`).
     */
    fun listen(port: Int = DEFAULT_PORT) {
        this.port = port
        isRunning.set(true)
        thread.start()
    }

    /**
     * Stop listening to clients.
     */
    fun stop() {
        isRunning.set(false)
        thread.interrupt()
    }

    /**
     * A callback function called whenever accelerometer sensor data is received.
     */
    open fun onAccelerometerChanged(info: MetaInfo, data: ZapAccelerometer) {
        throw Exception("Not yet implemented")
    }

    /**
     * A callback function called whenever UI event data is received.
     */
    open fun onUIEventReceived(info: MetaInfo, data: ZapUiEvent) {
        throw Exception("Not yet implemented")
    }

    /**
     * A callback function called whenever text data is received.
     */
    open fun onTextReceived(info: MetaInfo, data: ZapText) {
        throw Exception("Not yet implemented")
    }

    companion object {
        private const val DEFAULT_PORT = 65500
    }
}