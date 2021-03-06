package com.abhijith.appcode.helper

import android.content.Context
import android.util.Size
import android.widget.Toast


val one_to_one = Size(1080, 1080)
val three_to_two = Size(1125, 750)
val four_to_five = Size(1080, 1350)

fun Context.makeToast(message:String){
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}
