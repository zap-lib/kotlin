package com.github.zap_lib.models

import java.nio.ByteBuffer
import java.nio.ByteOrder

/**
 * An object that represents the encoded bytes on a single datagram.
 *
 * ZAPP(Zap Protocol) is network protocol defined on top of UDP datagram,
 * enabling the exchange of 'Zapable' data between client and server.
 * For further details about the protocol, refer to the [ZAPP section in the Zap Documentation](https://zap-lib.github.io/architectures/zap-protocol.html).
 *
 * @property header A header part.
 * @property payload A payload part.
 */
class ZappObject(
    val header: ZappHeader,
    val payload: ZappPayload,
) {
    /**
     * Encode [ZappObject] to [ByteBuffer]. The sequence of bytes is MUST encoded as ZAPP Object.
     */
    fun toByteBuffer(): ByteBuffer {
        val length = ZappHeader.LENGTH + payload.capacity()
        val buf = ByteBuffer.allocate(length).order(ByteOrder.BIG_ENDIAN)

        header.writeTo(buf) // First bytes is encoded as header part.
        buf.put(payload)

        return buf
    }

    operator fun component1(): ZappHeader = header
    operator fun component2(): ZappPayload = payload

    companion object {
        /**
         * Decode the given [ByteBuffer] in received datagram to [ZappObject].
         *
         * @param buf A [ByteBuffer] to convert that MUST be encoded as ZAPP Object.
         */
        fun from(buf: ByteBuffer): ZappObject {
            val header = ZappHeader.from(buf)
            val payload = buf.slice()

            return ZappObject(header, payload)
        }
    }
}