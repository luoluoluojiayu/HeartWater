package com.example.heartwater

import android.app.Activity
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.CompoundButton
import android.widget.CompoundButton.OnCheckedChangeListener
import android.widget.ToggleButton
import com.example.heartwater.bessel.*
import com.example.heartwater.bessel.BesselChart.ChartListener
import com.example.heartwater.bessel.ChartData.LabelTransform
import java.text.SimpleDateFormat
import java.util.*

class ChartActivity : Activity(), OnCheckedChangeListener, ChartListener {
    private lateinit var chart: BesselChart
    private lateinit var button: ToggleButton
    private var handler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chart_acitivyt)
        chart = findViewById(R.id.chart)
        button = findViewById(R.id.toggle_btn)
        button.setOnCheckedChangeListener(this)
        chart.setSmoothness(0.4f)       //设置光滑曲线
        chart.setChartListener(this)    //设置监听
        handler.postDelayed({
            getSeriesList(true)     //获取数据点
            chart.setSmoothness(0.33f)
        }, 1000)
    }

    /**
     * 获取无序的点
     */
    private fun getRandomSeries(title: String, color: Int, willDrawing: Boolean): Series {
        val points = ArrayList<Point>()     //点的集合
        val random = Random()               //随机类
        if (willDrawing) {      //是否正在绘制的，画面内显示十一个点
            for (i in 0..11) {
                points.add(Point(i + 1, 20000 + 1000 * random.nextInt(10), true))
//                if (i != 3)
//                    points.add(Point(i + 1, 20000 + 1000 * random.nextInt(10), true))
//                else {
//                    val tag = random.nextInt(10) > 5
//                    points.add(Point(i + 1, if (tag) 21000 else 0, tag))
//                }
            }
        } else {
            for (i in 0..35) {
                if (i % 3 == 2 && i < 20)
                    points.add(Point(i + 1, 0, false))
                else
                    points.add(Point(i + 1, 20000 + 1000 * random.nextInt(10), i % 3 == 1))
            }
        }
        for (point in points) {
            com.example.heartwater.bessel.Log.d("getRandomSeries valueY=" + point.valueY)
        }
        return Series(title, color, points)
    }

    private fun getSeriesList(willDrawing: Boolean) {
        val seriess = ArrayList<Series>()
        seriess.add(getRandomSeries("奥林匹克花园", Color.RED, willDrawing))
        var position = 0
        if (willDrawing) {
            position = 12
            chart.data.labelTransform = object : LabelTransform {

                override fun verticalTransform(valueY: Int): String {
                    return String.format("%.1fW", valueY / 10000f)
                }

                override fun horizontalTransform(valueX: Int): String {
                    return String.format("%s月", valueX)
                }

                override fun labelDrawing(valueX: Int): Boolean {
                    return true
                }
            }
        } else {
            position = 36
            chart.data.labelTransform = My36Transfer()
        }
        chart.data.seriesList = seriess
        chart.data.marker = Marker(Color.GREEN, position, 23000, BitmapFactory.decodeResource(resources, R.drawable.ajk_fj_benfangyuan), "该房源", 30, 30)
        chart.refresh(true)
    }

    override fun onCheckedChanged(buttonView: CompoundButton, isChecked: Boolean) {
        getSeriesList(!isChecked)
    }

    private inner class My36Transfer : ChartData.LabelTransform {
        private val calendar = Calendar.getInstance()
        private val format = SimpleDateFormat("yyyy.MM", Locale.CHINA)

        override fun verticalTransform(valueY: Int): String {
            Log.d("zqt", "step valueY=$valueY")
            return String.format("%.1fW", valueY / 10000f)
        }

        override fun horizontalTransform(valueX: Int): String {
            calendar.set(Calendar.YEAR, 2011)
            calendar.set(Calendar.MONTH, valueX)
            return format.format(calendar.time)
        }

        override fun labelDrawing(valueX: Int): Boolean {
            return valueX % 3 == 0
        }
    }

    override fun onMove() {
        Log.d("zqt", "onMove")
    }
}
