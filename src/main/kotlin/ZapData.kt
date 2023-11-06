package com.zap.core

open class ZapData

data class ZapAccelerometerData(val x: Int, val y: Int) : ZapData()

typealias ZapString = String