package com.abhijith.videoaspectration

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.abhijith.videoaspectration.databinding.ActivityImageViewBinding
import java.io.File

class ImageViewActivity : AppCompatActivity() {
    lateinit var binding:ActivityImageViewBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityImageViewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.ivImagePreview.setImageURI(Uri.fromFile(File(intent.getStringExtra(ImageCroppingActivity.ImagePath)!!)))

    }
}