package com.zap_lib.core.resources

enum class ZapResource(val key: String) {
    ACCELEROMETER("ACC");

    companion object {
        fun from(str: String): ZapResource =
            when (str) {
                ACCELEROMETER.key -> ACCELEROMETER
                else -> throw Exception("Unknown resource")
            }
    }
}
