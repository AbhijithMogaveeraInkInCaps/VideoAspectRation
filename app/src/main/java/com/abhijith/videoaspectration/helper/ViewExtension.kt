package com.abhijith.videoaspectration.helper

import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout

// Add extra spacing for translucent status bar and navigation bar
//fun View.applyWindowInserts() {
//    setOnApplyWindowInsetsListener { view, insets ->
//        val topSize = insets.systemWindowInsetTop
//        val bottomSize = insets.systemWindowInsetBottom
//        val params = view.layoutParams as ConstraintLayout.LayoutParams
//        params.setMargins(params.leftMargin, params.topMargin + topSize, params.rightMargin, params.bottomMargin + bottomSize)
//        view.layoutParams = params
//        insets
//    }
//}

fun Context.makeToast(message:String){
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}
