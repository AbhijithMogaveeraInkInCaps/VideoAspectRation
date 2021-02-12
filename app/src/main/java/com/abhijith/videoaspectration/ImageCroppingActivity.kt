package com.abhijith.videoaspectration

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.abhijith.videoaspectration.databinding.ActivityImageCropingBinding
import com.abhijith.videoaspectration.helper.one_to_one
import com.steelkiwi.cropiwa.AspectRatio
import com.steelkiwi.cropiwa.config.CropIwaSaveConfig
import java.io.File


class ImageCroppingActivity : AppCompatActivity() {

    val file by lazy {
        File(intent.getStringExtra(ImagePath)!!)
    }
    val uri: Uri by lazy {
        Uri.fromFile(file)
    }
    lateinit var binding: ActivityImageCropingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityImageCropingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val bitMap = BitmapFactory.decodeFile(file.absolutePath)
        binding.cid.visibility = View.VISIBLE
        binding.cid.configureOverlay().apply {
            aspectRatio = AspectRatio(one_to_one.height, one_to_one.width)
            isDynamicCrop = false
            apply()
        }
        binding.cid.setImageUri(uri)
        binding.btnRatioThreeToTwo.setOnClickListener {
            binding.cid.configureOverlay().apply {
                aspectRatio = AspectRatio(3, 2)
                isDynamicCrop = false
                apply()
            }
        }

        binding.btnRatioFourToFive.setOnClickListener {
            binding.cid.configureOverlay().apply {
                aspectRatio = AspectRatio(5, 4)
                isDynamicCrop = false
                apply()
            }
        }

        binding.btnRatioOneToOne.setOnClickListener {
            binding.cid.configureOverlay().apply {
                aspectRatio = AspectRatio(1, 1)
                isDynamicCrop = false
                apply()
            }
        }

        binding.btnSave.setOnClickListener {
            val sdCard: File = Environment.getExternalStorageDirectory()
            val dir = File(sdCard.absolutePath + "/NewAbhi")
            dir.mkdirs()
            val fileName = String.format("%d.jpeg", System.currentTimeMillis())
            val outFile = File(dir, fileName)

            binding.cid.crop(
                CropIwaSaveConfig.Builder(Uri.fromFile(outFile))
                    .setCompressFormat(Bitmap.CompressFormat.JPEG)
                    .setQuality(100)
                    .build()
            )
            binding.cid.setCropSaveCompleteListener {
                it?.let {
                    startActivity(
                        Intent(
                            this@ImageCroppingActivity,
                            ImageViewActivity::class.java
                        ).apply {
                            putExtra(ImagePath, File(it.path).absolutePath)
                        })
                }
            }
        }
    }

    companion object {
        const val ImagePath: String = "ImagePath"
    }
}