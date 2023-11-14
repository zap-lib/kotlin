package com.github.zap_lib.resources

import com.github.zap_lib.models.DeZapable
import com.github.zap_lib.models.ZappPayload
import com.github.zap_lib.models.Zapable
import com.github.zap_lib.models.readAsString
import java.nio.ByteBuffer

/**
 * Represent values measured by accelerometer sensor.
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
        ByteBuffer.wrap("$x,$y,$z".toByteArray())

    operator fun component1(): Float = x
    operator fun component2(): Float = y
    operator fun component3(): Float = z

    companion object : DeZapable {
        override fun from(payload: ZappPayload): ZapAccelerometer {
            val (x, y, z) = payload.readAsString().split(",").map { it.toFloat() }
            return ZapAccelerometer(x, y, z)
        }
    }
}