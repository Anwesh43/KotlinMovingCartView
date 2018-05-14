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

    data class State (var prevScale : Float = 0f, var dir : Float = 0f, var j : Int = 0) {

        private val scales : Array<Float> = arrayOf(0f, 0f, 0f)

        fun update(stopcb : (Float) -> Unit) {
            scales[this.j] += 0.1f * this.dir
            if (Math.abs(this.scales[this.j] - this.prevScale) > 1) {
                this.scales[this.j] = this.prevScale + this.dir
                this.j += this.dir.toInt()
                if (this.j == this.scales.size || this.j == -1) {

                }
            }
        }

        fun startUpdating(startcb : () -> Unit) {
            if (this.dir == 0f) {
                this.dir = 1 - 2 * prevScale
                startcb()
            }
        }
    }
}


