package com.zap.zap_example.views

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
        canvas.drawCircle(px, py, 30f, paint)
        invalidate()
    }
}