package com.abhijith.appcode.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
//import com.abhijith.appcode.VideoCaptureActivity
import com.abhijith.appcode.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ActivityMainBinding.inflate(layoutInflater).apply {
            setContentView(root)
            image.setOnClickListener {
                Intent(this@MainActivity,ImageCaptureActivity::class.java).apply {
                    startActivity(this)
                }
            }
            video.setOnClickListener {
                Intent(this@MainActivity, VideoCaptureActivity::class.java).apply {
                    startActivity(this)
                }
            }
        }
    }
}

/*
*filters
* https://github.com/MasayukiSuda/Mp4Composer-android/tree/master/mp4compose/src/main/java/com/daasuu/mp4compose/filter
*
* lib
* https://github.com/MasayukiSuda/Mp4Composer-android
*
* */