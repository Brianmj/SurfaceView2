package com.brianj.surfaceview2

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView

/**
 * Created by brianj on 8/24/17.
 */
class MySurfaceView(val ctext: Context, attributeSet: AttributeSet): SurfaceView(ctext, attributeSet), SurfaceHolder.Callback {

    lateinit var myThread: MyThread
    val dataModel = Model()

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
        val yellowPaint = Paint()
        yellowPaint.color = Color.YELLOW
        yellowPaint.style = Paint.Style.FILL

        if(dataModel.hasPoints()){
            for(p in dataModel.pointList){
                canvas.drawCircle(p.x, p.y, 20.0f, yellowPaint )
            }
        }

    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        super.onTouchEvent(event)
        event?.let {
            val x = it.x
            val y = it.y

            dataModel.addPoint(x, y)
        }

        return false
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