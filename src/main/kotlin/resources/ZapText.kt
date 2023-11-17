package com.github.zap_lib.resources

import com.github.zap_lib.getUByte
import com.github.zap_lib.models.DeZapable
import com.github.zap_lib.models.ZappPayload
import com.github.zap_lib.models.Zapable
import com.github.zap_lib.models.toByteArray
import com.github.zap_lib.putUByte
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.charset.Charset

/**
 * Represent simple text.
 *
 * ```text
 * +------------------+--------------+
 * | charset (8 bits) | str (n bits) |
 * +------------------+--------------+
 * ```
 *
 * @property str Just string.
 * @property charset A character set of [str]. (default: UTF-8)
 */
class ZapText(val str: String, val charset: ZapCharset = ZapCharset.UTF_8) : Zapable {
    override val resource: ZapResource = ZapResource.TEXT

    override fun toPayload(): ZappPayload {
        val strBytes = str.toByteArray()
        return ByteBuffer.allocate(UByte.SIZE_BYTES + strBytes.size)
            .order(ByteOrder.BIG_ENDIAN)
            .putUByte(charset.key)
            .put(strBytes)
            .also { it.clear() }
    }

    operator fun component1(): String = str
    operator fun component2(): ZapCharset = charset

    companion object : DeZapable {
        override fun from(payload: ZappPayload): ZapText {
            val charset = ZapCharset.from(payload.getUByte())

            val strBytes = payload.toByteArray(payload.remaining())
            val str = String(strBytes, charset.std)

            return ZapText(str, charset)
        }
    }
}

enum class ZapCharset(val key: UByte, val std: Charset) {
    UTF_8(0.toUByte(), Charsets.UTF_8),
    UTF_16(1.toUByte(), Charsets.UTF_16),
    UTF_16BE(2.toUByte(), Charsets.UTF_16BE),
    UTF_16LE(3.toUByte(), Charsets.UTF_16LE),
    UTF_32(4.toUByte(), Charsets.UTF_32),
    UTF_32BE(5.toUByte(), Charsets.UTF_32BE),
    UTF_32LE(6.toUByte(), Charsets.UTF_32LE),
    ISO_8859_1(7.toUByte(), Charsets.ISO_8859_1),
    US_ASCII(8.toUByte(), Charsets.US_ASCII);

    companion object {
        fun from(key: UByte) =
            when (key) {
                UTF_8.key -> UTF_8
                UTF_16.key -> UTF_16
                UTF_16BE.key -> UTF_16BE
                UTF_16LE.key -> UTF_16LE
                UTF_32.key -> UTF_32
                UTF_32BE.key -> UTF_32BE
                UTF_32LE.key -> UTF_32LE
                ISO_8859_1.key -> ISO_8859_1
                US_ASCII.key -> US_ASCII
                else -> throw Exception("Unknown charset")
            }
    }
}