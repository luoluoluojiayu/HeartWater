package com.example.heartwater

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import com.example.heartwater.heartView.HeartView

class HeartActivity : AppCompatActivity() {
    private var beginButton: Button? = null
    private var heartView: HeartView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_heart)
        beginButton = findViewById(R.id.start_again)
        heartView = findViewById(R.id.heart_view)
        beginButton?.setOnClickListener {
            heartView?.start()
        }
    }
}
