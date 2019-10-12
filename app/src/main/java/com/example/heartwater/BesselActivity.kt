package com.example.heartwater

import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.example.heartwater.heartView.BesselView

class BesselActivity : FragmentActivity() {

    private var besselView: BesselView? = null      //贝塞尔曲线view
    private var addPoint: Button? = null            //添加多一个点
    private var deletePoint:Button? = null          //删除一个点
    private var symmetric:Button? = null            //对称
    private var createCode:Button? = null           //生成代码
    private var hidePoint:Button? = null            //隐藏点
    private var hideLine:Button? = null             //隐藏线
    private var code: TextView? = null              //显示代码的TextView
    private var status = 7                          //状态机 7 二位数 0b0111 个位控制线，第二位控制点，第三位控制生成代码

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bessel)
        initView()
        initListener()
    }

    private fun initView(){
        besselView = findViewById(R.id.heart_view)
        code = findViewById(R.id.code)
        addPoint = findViewById(R.id.add)
        deletePoint = findViewById(R.id.jian)
        symmetric = findViewById(R.id.up)
        createCode = findViewById(R.id.next)
        hidePoint = findViewById(R.id.hide_point)
        hideLine = findViewById(R.id.hide_line)
    }

    private fun initListener() {
        addPoint?.setOnClickListener { besselView?.addPoint() }

        deletePoint?.setOnClickListener { besselView?.deletePoint() }

        symmetric?.setOnClickListener { besselView?.upPoint() }

        createCode?.setOnClickListener {
            if (status and 4 == 4) {
                code?.visibility = View.VISIBLE
                createCode?.text = "隐藏代码"
                code?.text = besselView?.getCreateCode()
                status = status and 11
            } else {
                code?.visibility = View.GONE
                createCode?.text = "生成代码"
                status = status or 4
            }
        }

        hidePoint?.setOnClickListener {
            if (status and 2 == 2) {
                besselView?.hidePoint()
                hidePoint?.text = "显示点"
                status = status and 13
            } else {
                besselView?.showPoint()
                hidePoint?.text = "隐藏点"
                status = status or 2
            }
        }

        hideLine?.setOnClickListener {
            if (status and 1 == 1) {
                besselView?.hideLine()
                hideLine?.text = "显示线"
                status = status and 14
            } else {
                besselView?.showLine()
                hideLine?.text = "隐藏线"
                status = status or 1
            }
        }

    }
}
