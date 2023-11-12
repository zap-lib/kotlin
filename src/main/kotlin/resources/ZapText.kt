package resources

import models.DeZapable
import models.ZapPayload
import models.Zapable
import java.nio.charset.Charset
import java.net.URLDecoder
import java.net.URLEncoder

class ZapText(val str: String, val charset: Charset = Charsets.UTF_8) : Zapable {
    override val resource: ZapResource = ZapResource.TEXT

    override fun toPayload(): ZapPayload =
        "${URLEncoder.encode(str, charset.toString())},$charset"

    operator fun component1(): String = str
    operator fun component2(): Charset = charset

    companion object : DeZapable {
        override fun fromPayload(payload: ZapPayload): ZapText {
            val (str, charset) = payload.split(",")
            return ZapText(URLDecoder.decode(str, charset), charset(charset))
        }
    }
}