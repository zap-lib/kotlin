package com.zap.zap_example.widgets

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class PlaygroundView : View {
    var paint: Paint = Paint().apply { color = Color.RED }
    var px = 0f
    var py = 0f

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        px = width / 2f
        py = height / 2f
    }

    override fun onDraw(canvas: Canvas) {
        if (px < 0) px = 0f
        if (py < 0) py = 0f

        if (px > width) px = width.toFloat()
        if (py > height) py = height.toFloat()

        canvas.drawCircle(px, py, 30f, paint)
        invalidate()
    }
}