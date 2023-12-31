package com.github.zap_lib.resources

import com.github.zap_lib.models.DeZapable
import com.github.zap_lib.models.ZappPayload
import com.github.zap_lib.models.Zapable
import java.nio.ByteBuffer
import java.nio.ByteOrder

/**
 * Represent values measured by accelerometer sensor.
 *
 * ```text
 * +-------------+-------------+-------------+
 * | x (32 bits) | y (32 bits) | z (32 bits) |
 * +-------------+-------------+-------------+
 * ```
 *
 * @property x Acceleration force along the x axis. (m/s²)
 * @property y Acceleration force along the y axis. (m/s²)
 * @property z Acceleration force along the z axis. (m/s²)
 */
class ZapAccelerometer(
    val x: Float,
    val y: Float,
    val z: Float,
) : Zapable {
    override val resource: ZapResource = ZapResource.ACCELEROMETER

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
        const val SIZE_BYTES = Float.SIZE_BYTES * 3

        override fun from(payload: ZappPayload): ZapAccelerometer {
            val x = payload.float
            val y = payload.float
            val z = payload.float
            return ZapAccelerometer(x, y, z)
        }
    }
}