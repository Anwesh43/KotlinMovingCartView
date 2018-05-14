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

    private val renderer : Renderer = Renderer(this)

    override fun onDraw(canvas : Canvas) {
        renderer.draw(canvas, paint)
    }

    override fun onTouchEvent(event : MotionEvent) : Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                renderer.handleTap()
            }
        }
        return true
    }

    data class State (var prevScale : Float = 0f, var dir : Float = 0f, var j : Int = 0) {

        val scales : Array<Float> = arrayOf(0f, 0f)

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

    data class Animator (var view : MovingCartView, var animated : Boolean = false) {

        fun animate(updatecb : () -> Unit) {
            if (animated) {
                updatecb()
                try {
                    Thread.sleep(50)
                    view.invalidate()
                } catch (ex : Exception) {

                }
            }
        }

        fun start() {
            if (!animated) {
                animated = true
                view.postInvalidate()
            }
        }

        fun stop() {
            if (animated) {
                animated = false
            }
        }
    }

    data class MovingCart(var i : Int, val state : State = State()) {

        fun draw(canvas : Canvas, paint : Paint) {
            val w : Float = canvas.width.toFloat()
            val h : Float = canvas.height.toFloat()
            paint.strokeWidth = Math.min(w, h) / 75
            paint.strokeCap = Paint.Cap.ROUND
            val hSize : Float = h/3
            val wSize : Float = w/2 - w/10
            val r : Float = Math.min(w, h)/10
            paint.color = Color.parseColor("#BDBDBD")
            canvas.save()
            canvas.translate(w/2 * this.state.scales[1], h/2)
            canvas.drawLine(w/10, -h/6, w/10, h/6, paint)
            canvas.drawLine(0f, -h/6, w/10, -h/6, paint)
            canvas.save()
            canvas.translate(wSize/2, h/6 + r)
            for (i in 0..1) {
                canvas.drawCircle(0.4f * w * (1 - 2 * i), 0f, r, paint)
            }
            canvas.restore()

            canvas.save()
            canvas.translate(0f, -h/8 + (h/6 - h/8) * (1 - state.scales[0]))
            canvas.drawRect(RectF(0f, 0f, 2 * wSize/3, h/4), paint)
            val path : Path = Path()
            path.moveTo(2 * wSize/3, h/4)
            path.lineTo(wSize, 0f)
            path.lineTo(2 * wSize/3, 0f)
            canvas.drawPath(path, paint)
            canvas.restore()
            canvas.restore()
        }

        fun update(stopcb : (Float) -> Unit) {
            state.update(stopcb)
        }

        fun startUpdating(startcb : () -> Unit) {
            state.startUpdating(startcb)
        }
    }

    data class Renderer(var view : MovingCartView) {

        val animator : Animator = Animator(view)

        val movingCart : MovingCart = MovingCart(0)

        fun draw(canvas : Canvas, paint : Paint) {
            canvas.drawColor(Color.parseColor("#212121"))
            movingCart.draw(canvas, paint)
            animator.animate {
                movingCart.update {
                    animator.stop()
                }
            }
        }

        fun handleTap() {
            movingCart.startUpdating {
                animator.start()
            }
        }
    }
}


