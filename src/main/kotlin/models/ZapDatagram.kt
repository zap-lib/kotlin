package com.zap_lib.core.models

import com.zap_lib.core.resources.ZapResource

class ZapHeader(
    val id: String,
    val resource: ZapResource,
) {
    override fun toString(): String {
        return "$id,${resource.key}"
    }
}

typealias ZapPayload = String
fun <T> ZapPayload.appendIfNotNull(x: T?, delimiter: String = ","): ZapPayload =
    x?.let {  "$this$delimiter$x" } ?: this

class ZapDatagram(
    val header: ZapHeader,
    val payload: ZapPayload,
) {
    override fun toString(): String = "$header;$payload"

    operator fun component1(): ZapHeader = header
    operator fun component2(): ZapPayload = payload

    companion object {
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