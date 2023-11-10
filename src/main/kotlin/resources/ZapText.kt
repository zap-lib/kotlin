package com.zap_lib.core.resources

import com.zap_lib.core.models.DeZapable
import com.zap_lib.core.models.ZapPayload
import com.zap_lib.core.models.Zapable
import java.nio.charset.Charset

class ZapText(val str: String, val charset: Charset = Charsets.UTF_8) : Zapable {
    override val resource: ZapResource = ZapResource.TEXT

    override fun toPayload(): ZapPayload = "$str,$charset"

    operator fun component1(): String = str
    operator fun component2(): Charset = charset

    companion object : DeZapable {
        override fun fromPayload(payload: ZapPayload): ZapText {
            val (str, charset) = payload.split(",")
            return ZapText(str, charset(charset))
        }
    }
}