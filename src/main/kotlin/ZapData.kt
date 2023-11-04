package com.zap.core

open class ZapData

data class ZapAccelerometerData(val x: Int, val y: Int) : ZapData()

typealias ZapString = String

/**
 * Converts [ZapData] to [ZapString].
 *
 * - [ZapAccelerometerData] has the following format: [ACC](ZapResource.ACCELEROMETER.key);[x](ZapAccelerometerData.x),[y](ZapAccelerometerData.y) (e.g., `ACC;107,42`)
 */
fun ZapData.toZapString(): ZapString {
    return when (this) {
        is ZapAccelerometerData -> "${ZapResource.ACCELEROMETER.key};${this.x},${this.y}"
        else -> throw Exception("Unknown Zap resource")
    }
}

/**
 * Parses [ZapString] to [ZapData].
 */
fun ZapString.toZapData(): ZapData {
    val splitted = this.split(";")
    return when (splitted.first()) {
        ZapResource.ACCELEROMETER.key -> {
            val (x, y) = splitted[1].split(",").map { it.toInt() }
            ZapAccelerometerData(x, y)
        }
        else -> throw Exception("Unknown Zap resource")
    }
}