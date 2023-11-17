package com.github.zap_lib.resources

import com.github.zap_lib.models.DeZapable
import com.github.zap_lib.models.Zapable
import com.github.zap_lib.models.ZappPayload
import java.nio.ByteBuffer
import java.nio.ByteOrder

/**
 * Represent a device's rate of rotation.
 *
 * ```text
 * +-------------+-------------+-------------+
 * | x (32 bits) | y (32 bits) | z (32 bits) |
 * +-------------+-------------+-------------+
 * ```
 *
 * @property x Rate of rotation around the x axis. (rad/s)
 * @property y Rate of rotation around the y axis. (rad/s)
 * @property z Rate of rotation around the z axis. (rad/s)
 */
class ZapGyroscope(val x: Float, val y: Float, val z: Float) : Zapable {
    override val resource: ZapResource = ZapResource.GYROSCOPE

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

        override fun from(payload: ZappPayload): ZapGyroscope {
            val x = payload.float
            val y = payload.float
            val z = payload.float
            return ZapGyroscope(x, y, z)
        }
    }
}