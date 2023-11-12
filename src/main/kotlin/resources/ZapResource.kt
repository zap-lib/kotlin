package com.github.zap_lib.resources

/**
 * A registry of resources supported by Zap and their identification values.
 */
enum class ZapResource(val key: String) {
    ACCELEROMETER("ACC"),
    UI_EVENT("UIE"),
    TEXT("TXT");

    companion object {
        fun from(str: String): ZapResource =
            when (str) {
                ACCELEROMETER.key -> ACCELEROMETER
                UI_EVENT.key -> UI_EVENT
                TEXT.key -> TEXT
                else -> throw Exception("Unknown resource")
            }
    }
}
