package com.example.recorder

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import kotlin.text.Typography.amp

// @JvmOverloads: 여러개의 생성자를 만들 수 있도록 사용
class WaveformView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val ampList = mutableListOf<Float>()
    private val rectList = mutableListOf<RectF>()

    private val rectWidth = 10f
    private var tick = 0

    private val redPaint = Paint().apply { color = Color.RED }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        for (rectF in rectList) {
            canvas?.drawRect(rectF, redPaint)
        }
    }

    fun addAmplitude(maxAmplitude: Float) {
        val amplitude = (maxAmplitude / Short.MAX_VALUE) * this.height * 0.8f

        ampList.add(amplitude)
        rectList.clear()

        val maxRect = (this.width / rectWidth).toInt()

        val amps = ampList.takeLast(maxRect)

        for ((i, amp) in amps.withIndex()) {
            val rectF = RectF()
            rectF.top = (this.height / 2) - amp / 2 - 3f
            rectF.bottom = rectF.top + amp + 3f
            rectF.left = i * rectWidth
            rectF.right = rectF.left + rectWidth - 5f // 사이 간격 조절, 여백을 위해 5를 더 줌

            rectList.add(rectF)
        }

        invalidate() // canvas 초기화
    }

    fun replayAmplitude() {
        rectList.clear()

        val maxRect = (this.width / rectWidth).toInt()
        val amps = ampList.take(tick).takeLast(maxRect)

        for ((i, amp) in amps.withIndex()) {
            val rectF = RectF()
            rectF.top = (this.height / 2) - amp / 2 - 3f // 아무말 안할 때 굵기 조절
            rectF.bottom = rectF.top + amp + 3f
            rectF.left = i * rectWidth
            rectF.right = rectF.left + rectWidth - 5f

            rectList.add(rectF)
        }

        tick++

        invalidate()
    }

    fun clearData() {
        ampList.clear()
    }

    fun clearWave() {
        rectList.clear()
        tick = 0
        invalidate()

    }
}