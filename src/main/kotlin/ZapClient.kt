import models.ZapDatagram
import models.ZapHeader
import models.Zapable
import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.InetAddress
import java.util.UUID
import java.util.concurrent.atomic.AtomicReference

/**
 * A client that sends data to server.
 *
 * @property serverAddress - An IP address of the device running ZapServer.
 */
class ZapClient(private val serverAddress: InetAddress) {
    val id = UUID.randomUUID().toString()

    private val socket = DatagramSocket()
    private val isSending = AtomicReference(false)

    /**
     * Send given Zapable object to the server.
     *
     * @param obj - An object to send.
     */
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