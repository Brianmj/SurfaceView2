package com.brianj.surfaceview2

/**
 * Created by brianj on 9/1/17.
 */

data class Point(val x: Float, val y: Float)
class Model
{
    private val dataPoints = arrayListOf<Point>()

    public fun hasPoints() = !dataPoints.isEmpty()
    public fun addPoint(x: Float, y: Float) {
        dataPoints.add(Point(x, y))
    }

    public val pointList: ArrayList<Point>
    get() = dataPoints
}