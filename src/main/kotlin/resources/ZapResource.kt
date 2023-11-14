package com.github.zap_lib.resources

/**
 * A registry of resources supported by Zap and their identification keys.
 */
enum class ZapResource(val key: UByte) {
    ACCELEROMETER(10.toUByte()),
    UI_EVENT(20.toUByte()),
    TEXT(30.toUByte());

    companion object {
        fun from(key: UByte): ZapResource =
            when (key) {
                ACCELEROMETER.key -> ACCELEROMETER
                UI_EVENT.key -> UI_EVENT
                TEXT.key -> TEXT
                else -> throw Exception("Unknown resource")
            }
    }
}
