import models.ZapDatagram
import resources.ZapAccelerometer
import resources.ZapResource
import resources.ZapText
import resources.ZapUiEvent
import java.lang.Exception
import java.net.DatagramPacket
import java.net.DatagramSocket
import java.nio.charset.Charset
import java.util.concurrent.atomic.AtomicBoolean

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
            val (header, payload) = ZapDatagram.from(packet.data.decodeToString())
            when (header.resource) {
                ZapResource.ACCELEROMETER -> {
                    val (x, y, z) = ZapAccelerometer.fromPayload(payload)
                    onAccelerometerChanged(header.id, x, y, z)
                }
                ZapResource.UI_EVENT -> {
                    val (uiId, event, value) = ZapUiEvent.fromPayload(payload)
                    onUIEventReceived(header.id, uiId, event, value)
                }
                ZapResource.TEXT -> {
                    val (str, charset) = ZapText.fromPayload(payload)
                    onTextReceived(header.id, str, charset)
                }
            }
        }
    }

    /**
     * Start listening the transmitted data from clients on the given port.
     *
     * @param port - A port number for receiving data (default: `65500`).
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
    open fun onAccelerometerChanged(id: String, x: Float, y: Float, z: Float) {
        throw Exception("Not yet implemented")
    }

    /**
     * A callback function called whenever UI event data is received.
     */
    open fun onUIEventReceived(id: String, uiId: String, event: ZapUiEvent.Event, value: String? = null) {
        throw Exception("Not yet implemented")
    }

    /**
     * A callback function called whenever text data is received.
     */
    open fun onTextReceived(id: String, str: String, charset: Charset) {
        throw Exception("Not yet implemented")
    }

    companion object {
        private const val DEFAULT_PORT = 65500
    }
}