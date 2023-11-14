package com.github.zap_lib

import java.nio.ByteBuffer

internal fun <T> String.appendIfNotNull(x: T?, delimiter: String = ","): String =
    x?.let { "$this$delimiter$x" } ?: this

fun ByteBuffer.putULong(x: ULong): ByteBuffer = this.putLong(x.toLong())
fun ByteBuffer.getULong(): ULong = this.long.toULong()

fun ByteBuffer.putUByte(x: UByte): ByteBuffer = this.put(x.toByte())
fun ByteBuffer.getUByte(): UByte = this.get().toUByte()