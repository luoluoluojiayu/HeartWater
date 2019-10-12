package com.example.heartwater.heartView

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import java.util.*

/**
 * @author: jiayu
 * @since:2019/7/17 17:00
 */
class HeartView : View {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)

    var paint = Paint()
    var waterPaint = Paint()
    var waveHeight = 130            //水波高度
    var waveWeight = 250            //水波宽度
    var waterPath = Path()
    var randomWeight = 0
    var randomHeight = 0


    var startX = 64                 //水波绘制的x轴起点
    var startY = 1101               //水波绘制的y轴起点
    var endx = 996

    var nowY = 1101
    var nowX = 64
    var timer = Timer()

    init {
        paint.style = Paint.Style.STROKE
        paint.color = Color.parseColor("#FCE5DF")
        paint.strokeWidth = 5f
        paint.isAntiAlias = true

        waterPaint.style = Paint.Style.FILL
        waterPaint.color = Color.parseColor("#9ECDDF")
        waterPaint.strokeWidth = 5f
        waterPaint.isAntiAlias = true
        start()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        drawHeart(canvas, paint)
    }

    private fun drawHeart(canvas: Canvas?, paint: Paint) {
        val path = Path()
        path.moveTo(530f, 631f)
        path.cubicTo(895f, 411f, 996f, 783f, 530f, 1101f)
        path.cubicTo(64f, 783f, 165f, 411f, 530f, 631f)
        path.close()
        canvas?.drawPath(path, paint)
        canvas?.clipPath(path)
        canvas?.drawColor(Color.parseColor("#FCE5DF"))
        drawWater(canvas, nowX, nowY, endx)
    }

    private fun drawWater(canvas: Canvas?, startX: Int, startY: Int, endX: Int) {
        waterPath = Path()
        waterPath.moveTo(startX.toFloat(), startY.toFloat())
        drawTheWater(startX, startY, endX, waterPath)
        waterPath.lineTo(996f, 1101f)
        waterPath.lineTo(64f, 1101f)
        waterPath.close()
        canvas?.drawPath(waterPath, waterPaint)
    }

    /**
     * 绘制一道水面
     */
    private fun drawTheWater(startX: Int, startY: Int, endX: Int, path: Path) {
        val point = drawOneWave(waveHeight, waveWeight, startX, startY, path)
        if (point.x < endX) {
            drawTheWater(point.x, point.y, endX, path)
        }
    }

    /**
     * 绘制一个水波
     */
    private fun drawOneWave(waveHeight: Int, waveWeight: Int, waveStartX: Int, waveStartY: Int, path: Path?): Point {
        val point1 = Point((waveStartX + waveWeight / 2).toInt(),
                (waveStartY - waveHeight / 2).toInt())
        val point2 = Point((waveStartX + waveWeight / 2 ).toInt(),
                (waveStartY + waveHeight / 2 ).toInt())
        val point3 = Point((waveStartX + waveWeight ).toInt(),
                (waveStartY ).toInt())
        path?.cubicTo(point1.x.toFloat(), point1.y.toFloat(), point2.x.toFloat(), point2.y.toFloat(), point3.x.toFloat(), point3.y.toFloat())
        return point3
    }

    private fun getIsNegative(): Int {
        return if (Math.random() < 0.5) -1
        else 1
    }

    fun start(){
        nowY = startY
        nowX = startX
        timer = Timer()
        timer.schedule(object : TimerTask() {
            override fun run() {
                if (nowY < 411) {
                    timer.cancel()
                }
                nowY -= 5
                nowX -= 15
                if (nowX < -waveWeight) {
                    nowX += waveWeight
                }
                postInvalidate()
            }
        }, 100, 100)
    }
}