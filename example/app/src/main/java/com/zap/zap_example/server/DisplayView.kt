package com.zap.zap_example.server

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class DisplayView : View {
    var px = 0f
    var py = 0f

    private lateinit var paint: Paint

    constructor(context: Context) : super(context) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context)
    }

    private fun init(context: Context) {
        paint = Paint().apply { color = Color.RED }
    }

    override fun onDraw(canvas: Canvas) {
        canvas.drawCircle(px, py, 30f, paint)
        invalidate()
    }
}