package com.example.textbrush.ui.utils

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.example.textbrush.data.TextElement

internal class CustomCanvasView(context: Context, attrs: AttributeSet) : View(context, attrs) {

    companion object {
        private const val SIZE_ONE = 1
        private const val SAMPLE_TEXT = "Sample Text"
    }

    private val textElements = mutableListOf<TextElement>()
    private var desiredSpacing = 0f
    private var rotationAngle = 0f
    private var fontSize = 20f
    private val textPaint = Paint().apply {
        color = Color.BLACK
        textSize = fontSize
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        for (element in textElements) {
            val text = element.text
            val x = element.x
            val y = element.y
            val spacing = element.spacing
            val rotationAngle = element.rotationAngle

            val letters = text.chunked(SIZE_ONE)
            var currentX = x

            for (letter in letters) {
                val letterWidth = textPaint.measureText(letter)

                val xWithSpacing = currentX + spacing

                val rotatedX =
                    xWithSpacing * kotlin.math.cos(Math.toRadians(rotationAngle.toDouble()))
                val rotatedY =
                    y - (xWithSpacing * kotlin.math.sin(Math.toRadians(rotationAngle.toDouble())))

                canvas.drawText(letter, rotatedX.toFloat(), rotatedY.toFloat(), textPaint)

                currentX += letterWidth + spacing
            }
        }
    }

    override fun onTouchEvent(event: MotionEvent) = when (event.action) {
        MotionEvent.ACTION_DOWN -> {
            val x = event.x
            val y = event.y
            val newText = SAMPLE_TEXT

            val newTextElement = TextElement(newText, x, y, desiredSpacing, rotationAngle)
            textElements.add(newTextElement)

            invalidate()
            true
        }

        else -> super.onTouchEvent(event)
    }

    fun updateRotation(newRotation: Float) {
        for (element in textElements) {
            element.rotationAngle = newRotation
        }
        invalidate()
    }

    fun updateSpacing(newSpacing: Float) {
        for (element in textElements) {
            element.spacing = newSpacing
        }
        invalidate()
    }

    fun updateFontSize(newFontSize: Float) {
        fontSize = newFontSize
        textPaint.textSize = fontSize
        invalidate()
    }

    fun undoLastDrawnSegment() {
        if (textElements.isNotEmpty()) {
            textElements.removeAt(textElements.size - SIZE_ONE)
            invalidate()
        }
    }
}