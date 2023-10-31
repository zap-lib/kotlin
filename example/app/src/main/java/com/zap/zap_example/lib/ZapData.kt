package com.zap.zap_example.lib

data class ZapData(val t: ZapResource, val v: String)

fun ZapData.toJson(): String {
    return when (this.t) {
        ZapResource.ACCELEROMETER -> """{"t":"${this.t}","v":"${this.v}"}"""
    }
}
