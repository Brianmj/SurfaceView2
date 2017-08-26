package com.brianj.surfaceview2

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.view.SurfaceHolder
import android.view.SurfaceView

/**
 * Created by brianj on 8/24/17.
 */
class MySurfaceView(val ctext: Context, attributeSet: AttributeSet): SurfaceView(ctext, attributeSet), SurfaceHolder.Callback {

    lateinit var myThread: MyThread

    init {
        val h = holder
        h.addCallback(this)
    }

    override fun surfaceCreated(p0: SurfaceHolder?) {
        myThread = MyThread(p0!!, ctext, this)
        myThread.running(true)
        myThread.start()
    }

    override fun surfaceDestroyed(p0: SurfaceHolder?) {
        myThread.running(false)
        var retry = true

        while(retry) {
            try{
                myThread.join()
                retry = false
            }catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    override fun surfaceChanged(p0: SurfaceHolder?, p1: Int, p2: Int, p3: Int) {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    public fun doDraw(canvas: Canvas) {
        canvas.drawColor(Color.WHITE)
        val p = Paint()
        p.color = Color.YELLOW
        p.style = Paint.Style.FILL
        canvas.drawRect(Rect(20, 20, 100, 100), p)
    }

    class MyThread(val holder: SurfaceHolder, val ctext: Context, val mySurfaceView: MySurfaceView): Thread() {
        var run = false


        public fun running(run: Boolean) {this.run = run }

        override fun run() {
            super.run()

            while(run) {
                val canvas = holder.lockCanvas()

                canvas?.let {
                    mySurfaceView.doDraw(it)
                    holder.unlockCanvasAndPost(it)
                }


            }
        }
    }

}