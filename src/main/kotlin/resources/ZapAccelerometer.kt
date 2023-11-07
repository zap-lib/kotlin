package com.zap_lib.core.resources

import com.zap_lib.core.models.DeZapable
import com.zap_lib.core.models.ZapPayload
import com.zap_lib.core.models.Zapable

class ZapAccelerometer(
    val x: Float,
    val y: Float,
    val z: Float,
) : Zapable {
    override val resource: ZapResource = ZapResource.ACCELEROMETER

    override fun toPayload(): ZapPayload = "$x,$y,$z"

    operator fun component1(): Float = x
    operator fun component2(): Float = y
    operator fun component3(): Float = z

    companion object : DeZapable {
        override fun fromPayload(payload: ZapPayload): ZapAccelerometer {
            val (x, y, z) = payload.split(",").map { it.toFloat() }
            return ZapAccelerometer(x, y, z)
        }
    }
}