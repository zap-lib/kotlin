package com.zap_lib.core.models

import com.zap_lib.core.resources.ZapResource

class ZapHeader(
    val id: String,
    val resource: ZapResource,
) {
    /**
     *	Convert [ZapHeader] to string and return it. It MUST be formatted as `id,resource`.
     */
    override fun toString(): String {
        return "$id,${resource.key}"
    }
}

typealias ZapPayload = String
fun <T> ZapPayload.appendIfNotNull(x: T?, delimiter: String = ","): ZapPayload =
    x?.let {  "$this$delimiter$x" } ?: this

/**
 * A protocol defined on top of datagrams for client and server to exchange [Zapable] data.
 *
 * @property header - A header part.
 * @property payload - A payload part.
 */
class ZapDatagram(
    val header: ZapHeader,
    val payload: ZapPayload,
) {
    /**
     * Compose a string to send with the header and payload.
     * The composed string MUST be formatted to `header;payload`.
     */
    override fun toString(): String = "$header;$payload"

    operator fun component1(): ZapHeader = header
    operator fun component2(): ZapPayload = payload

    companion object {
        /**
         * Convert the given string in received datagram to [ZapDatagram].
         *
         * @param str - A string to convert that MUST be formatted as `header;payload`.
         */
        fun from(str: String): ZapDatagram {
            val (header, payload) = str.split(";")
            val (id, resource) = header.split(",")

            return ZapDatagram(
                ZapHeader(id, ZapResource.from(resource)),
                payload,
            )
        }
    }
}