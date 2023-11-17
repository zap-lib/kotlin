package com.github.zap_lib.resources

import com.github.zap_lib.models.DeZapable
import com.github.zap_lib.models.Zapable
import com.github.zap_lib.models.ZappPayload
import java.nio.ByteBuffer
import java.nio.ByteOrder

/**
 * Represent the ambient light level.
 *
 * ```text
 * +--------------+
 * | lx (32 bits) |
 * +--------------+
 * ```
 *
 * @property lx ambient light level in lx.
 */
class ZapIlluminance(val lx: Float) : Zapable {
    override val resource: ZapResource = ZapResource.ILLUMINANCE

    override fun toPayload(): ZappPayload =
        ByteBuffer.allocate(SIZE_BYTES)
            .order(ByteOrder.BIG_ENDIAN)
            .putFloat(lx)
            .also { it.clear() }

    companion object : DeZapable {
        private const val SIZE_BYTES = Float.SIZE_BYTES

        override fun from(payload: ZappPayload): ZapIlluminance {
            return ZapIlluminance(payload.float)
        }
    }
}