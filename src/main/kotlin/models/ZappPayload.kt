package com.github.zap_lib.models

import java.nio.ByteBuffer
import java.nio.charset.StandardCharsets

/**
 * A payload part of [ZappObject].
 */
typealias ZappPayload = ByteBuffer

fun ZappPayload.readAsString(): String =
    StandardCharsets.UTF_8.decode(this).toString()