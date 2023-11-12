package com.github.zap_lib.models

import com.github.zap_lib.resources.ZapResource

/**
 * An interface for data exchange through Zap. Data objects exchanged via Zap MUST implement
 * this interface. If an object can be transmitted through Zap, it can be referred to as “Zapable”.
 */
interface Zapable {
    val resource: ZapResource

    /**
     * Convert [Zapable] to [ZapPayload] and return it.
     */
    fun toPayload(): ZapPayload
}

interface DeZapable {
    /**
     * Converts and returns [Zapable] object from ZapPayload.
     */
    fun fromPayload(payload: ZapPayload): Zapable
}