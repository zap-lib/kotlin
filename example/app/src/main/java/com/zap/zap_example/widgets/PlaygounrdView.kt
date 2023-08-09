package com.zap.zap_example.widgets

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

data class Pointer(var x: Float, var y: Float, val paint: Paint)

class PlaygroundView : View {
    private val pointers = mutableMapOf<String, Pointer>()

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)

        pointers.forEach {
            it.value.x = width / 2f
            it.value.y = height / 2f
        }
    }

    override fun onDraw(canvas: Canvas) {
        pointers.forEach { (_, pointer) ->
            if (pointer.x < 0) pointer.x = 0f
            if (pointer.y < 0) pointer.y = 0f

            if (pointer.x > width) pointer.x = width.toFloat()
            if (pointer.y > height) pointer.y = height.toFloat()

            canvas.drawCircle(pointer.x, pointer.y, 30f, pointer.paint)
        }

        invalidate()
    }

    fun add(id: String, paint: Paint) {
        pointers[id] = Pointer(width / 2f, height / 2f, paint)
    }

    fun get(id: String) = pointers[id] ?: throw Exception("pointer $id not found")

    fun moveTo(id: String, x: Float? = null, y: Float? = null) {
        x?.let { pointers[id]?.x = x }
        y?.let { pointers[id]?.y = y }
    }
}