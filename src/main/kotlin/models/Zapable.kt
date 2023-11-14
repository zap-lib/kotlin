package com.github.zap_lib.models

import com.github.zap_lib.resources.ZapResource

/**
 * An interface for data exchange through Zap.
 *
 * The data objects exchanged via Zap MUST implement this interface.
 * If an object can be transmitted through Zap, it can be referred to as “Zapable”.
 *
 * @property resource A resource type of the object.
 */
interface Zapable {
    val resource: ZapResource

    /**
     * Convert [Zapable] to [ZappPayload] and return it.
     */
    fun toPayload(): ZappPayload
}

interface DeZapable {
    /**
     * Decode and return [Zapable] object from [ZappPayload].
     *
     * @param payload A payload to decode to [Zapable] object.
     */
    fun from(payload: ZappPayload): Zapable
}