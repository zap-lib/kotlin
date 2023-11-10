package com.zap_lib.core.resources

enum class ZapResource(val key: String) {
    ACCELEROMETER("ACC"),
    UI_COMPONENT("UIC"),
    TEXT("TXT");

    companion object {
        fun from(str: String): ZapResource =
            when (str) {
                ACCELEROMETER.key -> ACCELEROMETER
                UI_COMPONENT.key -> UI_COMPONENT
                TEXT.key -> TEXT
                else -> throw Exception("Unknown resource")
            }
    }
}
