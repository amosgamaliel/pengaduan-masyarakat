package com.rectangle.cepuonline.util

import android.content.Context
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.text.TextPaint
import java.util.*

class AvatarGenerator {
    companion object {

        lateinit var uiContext: Context
        var texSize = 0F

        fun avatarImage(context: Context, size: Int, shape: Int, name: String): BitmapDrawable {
            uiContext = context
            val width = size
            val hieght = size

            texSize = calTextSize(size-35)
            val label = firstCharacter(name)
            val textPaint = textPainter()
            val painter = painter()
            val areaRect = Rect(0, 0, width, width)

            if (shape == 0) {
                painter.color = RandomColors().color
            } else {
                painter.color = Color.TRANSPARENT
            }

            val bitmap = Bitmap.createBitmap(width, width, Bitmap.Config.ARGB_8888)
            val canvas = Canvas(bitmap)
            canvas.drawRect(areaRect, painter)

            //reset painter
            if (shape == 0) {
                painter.color = Color.TRANSPARENT
            } else {
                painter.color = RandomColors().color
            }

            val bounds = RectF(areaRect)
            bounds.right = textPaint.measureText(label, 0, 1)
            bounds.bottom = textPaint.descent() - textPaint.ascent()

            bounds.left += (areaRect.width() - bounds.right) / 2.0f
            bounds.top += (areaRect.height() - bounds.bottom) / 2.0f

            canvas.drawCircle(width.toFloat() / 2, hieght.toFloat() / 2, width.toFloat() / 2, painter)
            canvas.drawText(label, bounds.left, bounds.top - textPaint.ascent(), textPaint)
            return BitmapDrawable(uiContext.resources, bitmap)

        }

        private fun firstCharacter(name: String): String {
//            return name.trim().split(" ").map{it ->
//                it[0]
//            }.joinToString(separator = "")
//            return "S"
            return name.first().toString().toUpperCase()
        }

        private fun textPainter(): TextPaint {
            val textPaint = TextPaint()
            textPaint.textSize = texSize * uiContext.resources.displayMetrics.scaledDensity
            textPaint.color = Color.WHITE
            return textPaint
        }

        private fun painter(): Paint {
            return Paint()
        }

        private fun calTextSize(size: Int): Float {
            return (size / 3.125).toFloat()
        }
    }

    internal class RandomColors {
        private val recycle: Stack<Int> = Stack()
        private val colors: Stack<Int> = Stack()
        val color: Int
            get() {
                if (colors.size == 0) {
                    while (!recycle.isEmpty()) colors.push(recycle.pop())
                    Collections.shuffle(colors)
                }
                val c: Int = colors.pop()
                recycle.push(c)
                return c
            }

        init {
            recycle.addAll(
                Arrays.asList(
                    -0xbbcca, -0x16e19d, -0x63d850, -0x98c549,
                    -0xc0ae4b, -0xde690d, -0xfc560c, -0xff432c,
                    -0xff6978, -0xb350b0, -0x743cb6, -0x3223c7,
                    -0x14c5, -0x3ef9, -0x6800, -0xa8de,
                    -0x86aab8, -0x616162, -0x9f8275, -0xcccccd
                )
            )
        }
    }
}