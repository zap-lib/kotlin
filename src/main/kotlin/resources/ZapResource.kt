package com.github.zap_lib.resources

/**
 * A registry of resources supported by Zap and their identification keys.
 */
enum class ZapResource(val key: UByte) {
    ACCELEROMETER(10.toUByte()),
    GRAVITY(11.toUByte()),
    GYROSCOPE(12.toUByte()),
    ILLUMINANCE(13.toUByte()),
    MAGNETIC_FIELD(14.toUByte()),
    UI_EVENT(20.toUByte()),
    TEXT(30.toUByte()),
    GEO_POINT(31.toUByte());

    companion object {
        fun from(key: UByte): ZapResource =
            when (key) {
                ACCELEROMETER.key -> ACCELEROMETER
                GRAVITY.key -> GRAVITY
                GYROSCOPE.key -> GYROSCOPE
                ILLUMINANCE.key -> ILLUMINANCE
                MAGNETIC_FIELD.key -> MAGNETIC_FIELD
                UI_EVENT.key -> UI_EVENT
                TEXT.key -> TEXT
                GEO_POINT.key -> GEO_POINT
                else -> throw Exception("Unknown resource")
            }
    }
}
