package com.zap_lib.core.models

import com.zap_lib.core.resources.ZapResource

interface Zapable {
    val resource: ZapResource

    fun toPayload(): ZapPayload
}

interface DeZapable {
    fun fromPayload(payload: ZapPayload): Zapable
}