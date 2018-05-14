package com.example.anweshmishra.kotlinmovingcartview

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.movingcartview.MovingCartView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MovingCartView.create(this)
    }
}
