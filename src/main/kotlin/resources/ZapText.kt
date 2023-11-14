package com.github.zap_lib.resources

import com.github.zap_lib.models.DeZapable
import com.github.zap_lib.models.ZappPayload
import com.github.zap_lib.models.Zapable
import com.github.zap_lib.models.readAsString
import java.nio.charset.Charset
import java.net.URLDecoder
import java.net.URLEncoder
import java.nio.ByteBuffer

/**
 * Represent simple text.
 *
 * @property str Just string.
 * @property charset A character set of [str]. (default: UTF-8)
 */
class ZapText(val str: String, val charset: Charset = Charsets.UTF_8) : Zapable {
    override val resource: ZapResource = ZapResource.TEXT

    override fun toPayload(): ZappPayload =
        ByteBuffer.wrap("${URLEncoder.encode(str, charset.toString())},$charset".toByteArray())

    operator fun component1(): String = str
    operator fun component2(): Charset = charset

    companion object : DeZapable {
        override fun from(payload: ZappPayload): ZapText {
            val (str, charset) = payload.readAsString().split(",")
            return ZapText(URLDecoder.decode(str, charset), charset(charset))
        }
    }
}