package com.abhijith.videoaspectration

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.util.Size
import android.view.View

class CustomGridView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    val paint = Paint().apply{
        color = Color.BLACK
        style = Paint.Style.STROKE
        strokeWidth = 10f
    }
    private var myWidth = 1080
    private var myHeight = 1080

    override fun draw(canvas: Canvas?) {
        super.draw(canvas)


        canvas?.apply {

            drawRect(Rect(0,0, myWidth, myHeight), paint)

            //horizontal line
            drawLine((myWidth /3).toFloat(),0.toFloat(),(myWidth /3).toFloat(), myHeight.toFloat(),paint)
            drawLine((myWidth -(myWidth /3)).toFloat(),0.toFloat(),(myWidth -(myWidth /3)).toFloat(), myHeight.toFloat(),paint)

            //vertical line
            drawLine(0.toFloat(),((myHeight-(myHeight/3).toFloat())),myWidth.toFloat(),((myHeight-((myHeight/3))).toFloat()),paint)
            drawLine(0.toFloat(),((myHeight-((myHeight/3)*2).toFloat())),myWidth.toFloat(),((myHeight-(((myHeight/3)*2))).toFloat()),paint)

            translate(0f,200f)
        }


        fun updateRation(size:Size){
            myHeight = size.height
            myWidth = size.width
            invalidate()
        }
    }
}