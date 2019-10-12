package com.example.heartwater.heartView

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

/**
 * @author: jiayu
 * @since:2019/7/5 15:08
 */
class BesselView : View {

    private var viewWeight = 0                          // 整个view的宽
    private var viewHeight = 0                          // 整个view的高
    private val mPaint: Paint = Paint()                 // 画笔，用来画点还有画贝塞尔曲线的
    private val linePaint: Paint = Paint()              // 画笔，用来画辅助虚线的
    private var halfWeight = 0                          // 一半的宽
    private var halfHeight = 0                          // 一半的高
    private var pointArr = arrayListOf<Point>()         // 所有显示的点集合
    private var index = 0                               // 现在被选中的是第几个点
    private var status = 3                              // 二进制判断 3 为 0b0011 个位是显示线，第二位是显示点

    constructor(context: Context?) : super(context)

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)

    /**
     * 一些画笔的初始化
     */
    init {
        mPaint.style = Paint.Style.STROKE
        linePaint.pathEffect = DashPathEffect(floatArrayOf(20f, 10f), 0f)
        linePaint.style = Paint.Style.STROKE
        linePaint.strokeWidth = 1f
        linePaint.color = Color.GRAY
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        this.drawBessel(pointArr, canvas, mPaint)
        if (status and 1 == 1) this.drawLine(pointArr, canvas, linePaint)
        if (status and 2 == 2) this.drawPoint(pointArr, canvas, mPaint)
    }

    /**
     * 绘制圆点
     */
    private fun drawPoint(list: ArrayList<Point>, canvas: Canvas?, paint: Paint) {
        for (pIndex in list.indices) {
            if (index == pIndex) {
                paint.strokeWidth = 40f
                paint.color = Color.BLUE
            } else {
                paint.strokeWidth = 30f
                paint.color = Color.RED
            }
            canvas?.drawCircle(list[pIndex].x.toFloat(), list[pIndex].y.toFloat(), 10f, paint)
        }
    }

    /**
     * 绘制贝塞尔曲线
     */
    private fun drawBessel(list: ArrayList<Point>, canvas: Canvas?, paint: Paint) {
        if (list.isEmpty()) return
        val pointList = arrayListOf<Point>()
        for (i in list.indices) {
            if (i != 0) pointList.add(list[i])
        }
        var pointIndex = 0
        val path = Path()
        path.moveTo(list[0].x.toFloat(), list[0].y.toFloat())
        val pointListSize = pointList.size
        while (pointIndex < pointList.size) {
            val nowMargin = pointListSize - pointIndex
            if (nowMargin > 2) {

                //有两个点以上，做三阶贝塞尔曲线
                path.cubicTo(pointList[pointIndex].x.toFloat(), pointList[pointIndex++].y.toFloat(),
                        pointList[pointIndex].x.toFloat(), pointList[pointIndex++].y.toFloat(),
                        pointList[pointIndex].x.toFloat(), pointList[pointIndex++].y.toFloat())

            } else if (nowMargin == 2) {

                //刚好两个点，做二阶贝塞尔曲线
                path.quadTo(pointList[pointIndex].x.toFloat(), pointList[pointIndex++].y.toFloat(),
                        pointList[pointIndex].x.toFloat(), pointList[pointIndex++].y.toFloat())

            } else {

                //小于两个点，只能连线
                path.lineTo(pointList[pointIndex].x.toFloat(), pointList[pointIndex++].y.toFloat())
                if (pointIndex < pointList.size) {
                    path.lineTo(pointList[pointIndex].x.toFloat(), pointList[pointIndex++].y.toFloat())
                }

            }
        }
        paint.color = Color.BLACK
        paint.strokeWidth = 10f
        canvas?.drawPath(path, paint)
    }

    /**
     * 绘制辅助线
     */
    private fun drawLine(list: ArrayList<Point>, canvas: Canvas?, paint: Paint) {
        paint.color = Color.GRAY
        paint.strokeWidth = 10f
        val path = Path()
        for (item in list.indices) {
            if (item == 0) {
                path.moveTo(list[item].x.toFloat(), list[item].y.toFloat())
            } else {
                path.lineTo(list[item].x.toFloat(), list[item].y.toFloat())
            }
        }
        canvas?.drawPath(path, paint)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                for (it in pointArr.indices) {
                    if (hasChoice(event, pointArr[it])) index = it
                }
                pointArr[index].x = event.x.toInt()
                pointArr[index].y = event.y.toInt()
            }
            MotionEvent.ACTION_MOVE -> {
                pointArr[index].x = event.x.toInt()
                pointArr[index].y = event.y.toInt()
            }
            MotionEvent.ACTION_UP -> {
                pointArr[index].x = event.x.toInt()
                pointArr[index].y = event.y.toInt()
            }
        }
        invalidate()
        return true
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        viewWeight = width
        viewHeight = height
        halfWeight = (width / 2)
        halfHeight = (height / 2)
        pointArr.clear()
        pointArr.add(Point(halfWeight, halfHeight))
    }

    fun addPoint() {
        index = pointArr.size - 1
        if (index == -1) {
            val point = Point(halfWeight, halfHeight)
            pointArr.add(point)
        } else {
            val point = Point(pointArr[index].x + 100, pointArr[index].y)
            pointArr.add(point)
        }
        index = pointArr.size - 1
        invalidate()
    }

    private fun hasChoice(event: MotionEvent, point: Point): Boolean {
        return Math.abs(event.x - point.x) < 50 && Math.abs(event.y - point.y) < 50
    }

    fun upPoint() {
        index--
        if (index < 0) index = 0
        invalidate()
    }

    fun deletePoint() {
        if (index == -1) return
        pointArr.remove(pointArr[index])
        index = pointArr.size - 1
        invalidate()
    }

    fun hidePoint() {
        status = status and 13
        invalidate()
    }

    fun showPoint() {
        status = status or 2
        invalidate()
    }

    fun hideLine() {
        status = status and 14
        invalidate()
    }

    fun showLine() {
        status = status or 1
        invalidate()
    }

    /**
     * 生成所画贝塞尔曲线的代码
     */
    fun getCreateCode(): String {
        var code = ""
        if (pointArr.isEmpty()) return code
        val pointList = arrayListOf<Point>()
        for (i in pointArr.indices) {
            if (i != 0) pointList.add(pointArr[i])
        }
        var pointIndex = 0
        code = code.plus("var path = Path() \n")
        code = code.plus("path.moveTo(${pointArr[0].x.toFloat()} , ${pointArr[0].y.toFloat()}) \n")
        val pointListSize = pointList.size
        while (pointIndex < pointList.size) {
            val nowMargin = pointListSize - pointIndex
            if (nowMargin > 2) {

                //有两个点以上，做三阶贝塞尔曲线
                code = code.plus("path.cubicTo(${pointList[pointIndex].x.toFloat()},${pointList[pointIndex++].y.toFloat()}," +
                        "${pointList[pointIndex].x.toFloat()},${pointList[pointIndex++].y.toFloat()}," +
                        "${pointList[pointIndex].x.toFloat()},${pointList[pointIndex++].y.toFloat()}) \n")

            } else if (nowMargin == 2) {

                //刚好两个点，做二阶贝塞尔曲线
                code = code.plus("path.quadTo(${pointList[pointIndex].x.toFloat()},${pointList[pointIndex++].y.toFloat()}," +
                        "${pointList[pointIndex].x.toFloat()},${pointList[pointIndex++].y.toFloat()}) \n")
            } else {

                //小于两个点，只能连线
                code = code.plus("path.lineTo(${pointList[pointIndex].x.toFloat()},${pointList[pointIndex++].y.toFloat()}) \n")
                if (pointIndex < pointList.size) {
                    code = code.plus("path.lineTo(${pointList[pointIndex].x.toFloat()},${pointList[pointIndex++].y.toFloat()}) \n")
                }
            }
        }
        return code
    }

}