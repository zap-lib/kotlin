package com.github.zap_lib.resources

import com.github.zap_lib.models.DeZapable
import com.github.zap_lib.models.Zapable
import com.github.zap_lib.models.ZappPayload
import java.nio.ByteBuffer
import java.nio.ByteOrder

/**
 * Represent the force of gravity that is applied to a device.
 *
 * ```text
 * +-------------+-------------+-------------+
 * | x (32 bits) | y (32 bits) | z (32 bits) |
 * +-------------+-------------+-------------+
 * ```
 *
 * @property x Force of gravity along the x axis. (m/s²)
 * @property y Force of gravity along the y axis. (m/s²)
 * @property z Force of gravity along the z axis. (m/s²)
 */
class ZapGravity(val x: Float, val y: Float, val z: Float) : Zapable {
    override val resource: ZapResource = ZapResource.GRAVITY

    override fun toPayload(): ZappPayload =
        ByteBuffer.allocate(SIZE_BYTES)
            .order(ByteOrder.BIG_ENDIAN)
            .putFloat(x)
            .putFloat(y)
            .putFloat(z)
            .also { it.clear() }

    operator fun component1(): Float = x
    operator fun component2(): Float = y
    operator fun component3(): Float = z

    companion object : DeZapable {
        private const val SIZE_BYTES = Float.SIZE_BYTES * 3

        override fun from(payload: ZappPayload): ZapGravity {
            val x = payload.float
            val y = payload.float
            val z = payload.float
            return ZapGravity(x, y, z)
        }
    }
}