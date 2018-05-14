package com.example.movingcartview

/**
 * Created by anweshmishra on 15/05/18.
 */

import android.view.View
import android.view.MotionEvent
import android.content.*
import android.graphics.*

class MovingCartView (ctx : Context) : View(ctx) {

    private val paint : Paint = Paint(Paint.ANTI_ALIAS_FLAG)

    override fun onDraw(canvas : Canvas) {

    }

    override fun onTouchEvent(event : MotionEvent) : Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {

            }
        }
        return true
    }
}


