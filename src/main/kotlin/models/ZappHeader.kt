package com.github.zap_lib.models

import com.github.zap_lib.getUByte
import com.github.zap_lib.getULong
import com.github.zap_lib.putUByte
import com.github.zap_lib.putULong
import com.github.zap_lib.resources.ZapResource
import java.nio.ByteBuffer

/**
 * A header part of [ZappObject].
 *
 * @property resource A resource type of the payload. It indicates a format of payload.
 * @property timestamp An epoch time in milliseconds for creation time of [ZappObject]. (default: Current epoch)
 */
class ZappHeader(
    val resource: ZapResource,
    val timestamp: ULong = System.currentTimeMillis().toULong(),
) {
    /**
     * Write [ZappHeader] to given [ByteBuffer] and return it.
     * The given [ByteBuffer] MUST be encoded as ZAPP Header.
     */
    fun writeTo(buf: ByteBuffer): ByteBuffer {
        buf.clear()
        return buf.putULong(timestamp).putUByte(resource.key)
    }

    companion object {
        private const val TIMESTAMP_SIZE_BYTES = ULong.SIZE_BYTES
        private const val RESOURCE_SIZE_BYTES = UByte.SIZE_BYTES
        const val SIZE_BYTES = TIMESTAMP_SIZE_BYTES + RESOURCE_SIZE_BYTES

        /**
         * Read bytes from the given [ByteBuffer] and decode it to [ZappHeader].
         */
        fun from(buf: ByteBuffer): ZappHeader {
            val timestamp = buf.getULong()
            val resource = buf.getUByte()

            return ZappHeader(ZapResource.from(resource), timestamp)
        }
    }
}