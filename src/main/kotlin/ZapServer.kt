package com.zap_lib.core

import com.zap_lib.core.models.ZapDatagram
import com.zap_lib.core.resources.ZapAccelerometer
import com.zap_lib.core.resources.ZapResource
import com.zap_lib.core.resources.ZapUiComponent
import java.lang.Exception
import java.net.DatagramPacket
import java.net.DatagramSocket
import java.util.concurrent.atomic.AtomicBoolean

open class ZapServer {
    private var port: Int = DEFAULT_PORT

    private var isRunning = AtomicBoolean(false)

    private val thread = Thread {
        val socket = DatagramSocket(this.port)
        val buffer = ByteArray(2048)
        val packet = DatagramPacket(buffer, buffer.size)

        while (isRunning.get()) {
            socket.receive(packet)
            val (header, payload) = ZapDatagram.from(packet.data.decodeToString())
            when (header.resource) {
                ZapResource.ACCELEROMETER -> {
                    val (x, y, z) = ZapAccelerometer.fromPayload(payload)
                    onAccelerometerChanged(header.id, x, y, z)
                }
                ZapResource.UI_COMPONENT -> {
                    val (id, event, value) = ZapUiComponent.fromPayload(payload)
                    onUIComponentChanged(id, event, value)
                }
            }
        }
    }

    fun listen(port: Int = DEFAULT_PORT) {
        this.port = port
        isRunning.set(true)
        thread.start()
    }

    fun stop() {
        isRunning.set(false)
        thread.interrupt()
    }

    open fun onAccelerometerChanged(id: String, x: Float, y: Float, z: Float) {
        throw Exception("Not yet implemented")
    }

    open fun onUIComponentChanged(id: String, event: ZapUiComponent.Event, value: String? = null) {
        throw Exception("Not yet implemented")
    }

    companion object {
        private const val DEFAULT_PORT = 65500
    }
}