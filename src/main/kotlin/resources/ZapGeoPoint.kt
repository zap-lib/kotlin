package com.github.zap_lib.resources

import com.github.zap_lib.models.DeZapable
import com.github.zap_lib.models.Zapable
import com.github.zap_lib.models.ZappPayload
import java.nio.ByteBuffer
import java.nio.ByteOrder

/**
 * Represent a point on earth in geological coordinates.
 *
 * ```text
 * +--------------------+---------------------+
 * | latitude (64 bits) | longitude (64 bits) |
 * +--------------------+---------------------+
 * ```
 */
class ZapGeoPoint(val latitude: Double, val longitude: Double) : Zapable {
    override val resource: ZapResource = ZapResource.GEO_POINT

    override fun toPayload(): ZappPayload =
        ByteBuffer.allocate(SIZE_BYTES)
            .order(ByteOrder.BIG_ENDIAN)
            .putDouble(latitude)
            .putDouble(longitude)
            .also { it.clear() }

    operator fun component1(): Double = latitude
    operator fun component2(): Double = longitude

    companion object : DeZapable {
        private const val SIZE_BYTES = Double.SIZE_BYTES * 2

        override fun from(payload: ZappPayload): ZapGeoPoint {
            val lat = payload.double
            val lon = payload.double
            return ZapGeoPoint(lat, lon)
        }
    }
}