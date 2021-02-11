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
import com.abhijith.videoaspectration.helper.four_to_five
import com.abhijith.videoaspectration.helper.one_to_one
import com.abhijith.videoaspectration.helper.three_to_two
import com.isseiaoki.simplecropview.callback.CropCallback
import com.isseiaoki.simplecropview.callback.SaveCallback
import java.io.File
import java.io.FileOutputStream


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
        binding.cid.setHandleSizeInDp(2)
        binding.cid.setImageBitmap(bitMap)
        binding.cid.setCropMode(com.isseiaoki.simplecropview.CropImageView.CropMode.SQUARE)
        binding.btnRatioThreeToTwo.setOnClickListener {
            binding.cid.setCustomRatio(three_to_two.width, three_to_two.height)
        }

        binding.btnRatioFourToFive.setOnClickListener {
            binding.cid.setCustomRatio(four_to_five.width, four_to_five.height)
        }

        binding.btnRatioOneToOne.setOnClickListener {
            binding.cid.setCustomRatio(one_to_one.width, one_to_one.height)
        }

        binding.btnSave.setOnClickListener {

            binding.cid.crop(uri)
                .execute(object : CropCallback {
                    override fun onSuccess(cropped: Bitmap) {
                        var outStream: FileOutputStream? = null
                        val sdCard: File = Environment.getExternalStorageDirectory()
                        val dir = File(sdCard.absolutePath + "/NewAbhi")
                        dir.mkdirs()
                        val fileName = String.format("%d.jpg", System.currentTimeMillis())
                        val outFile = File(dir, fileName)
                        outStream = FileOutputStream(outFile)
                        cropped.compress(Bitmap.CompressFormat.JPEG, 100, outStream)
                        startActivity(Intent(this@ImageCroppingActivity, ImageViewActivity::class.java).apply {
                            putExtra(ImagePath, outFile.absolutePath)
                        })
                        outStream.flush()
                        outStream.close()
                        finish()
                    }

                    override fun onError(e: Throwable) {}
                })
        }
    }

    companion object {
        val ImagePath: String = "ImagePath"
    }
}