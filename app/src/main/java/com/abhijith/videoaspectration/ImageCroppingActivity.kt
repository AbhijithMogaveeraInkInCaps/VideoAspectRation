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
//            binding.cid.crop(
//                CropIwaSaveConfig.Builder(Uri.fromFile(file))
//                    .setCompressFormat(Bitmap.CompressFormat.JPEG)
//                    .setSize(
//                        one_to_one.width,
//                        one_to_one.height
//                    ) //Optional. If not specified, SRC dimensions will be used
//                    .setQuality(100) //Hint for lossy compression formats
//                    .build()
//            )

//        binding.cid.setHandleSizeInDp(3)
//        binding.cid.setImageBitmap(bitMap)
//        binding.cid.setCropMode(com.isseiaoki.simplecropview.CropImageView.CropMode.SQUARE)
        binding.btnRatioThreeToTwo.setOnClickListener {
            binding.cid.configureOverlay().apply {
                aspectRatio = AspectRatio(three_to_two.height, three_to_two.width)
                isDynamicCrop = false
                apply()
            }
        }

        binding.btnRatioFourToFive.setOnClickListener {
            binding.cid.configureOverlay().apply {
                aspectRatio = AspectRatio(four_to_five.height, four_to_five.width)
                isDynamicCrop = false
                apply()
            }
//            binding.cid.
//            binding.cid.setCustomRatio(four_to_five.width, four_to_five.height)
        }

        binding.btnRatioOneToOne.setOnClickListener {
            binding.cid.configureOverlay().apply {
                aspectRatio = AspectRatio(one_to_one.height, one_to_one.width)
                isDynamicCrop = false
                apply()
            }
//            binding.cid.setCustomRatio(one_to_one.width, one_to_one.height)
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
                    .setQuality(100) //Hint for lossy compression formats
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

//            binding.cid.crop(uri)
//                .execute(object : CropCallback {
//                    override fun onSuccess(cropped: Bitmap) {
//                        var outStream: FileOutputStream? = null
//                        val sdCard: File = Environment.getExternalStorageDirectory()
//                        val dir = File(sdCard.absolutePath + "/NewAbhi")
//                        dir.mkdirs()
//                        val fileName = String.format("%d.jpg", System.currentTimeMillis())
//                        val outFile = File(dir, fileName)
//                        outStream = FileOutputStream(outFile)
//                        cropped.compress(Bitmap.CompressFormat.JPEG, 100, outStream)
//                        startActivity(Intent(this@ImageCroppingActivity, ImageViewActivity::class.java).apply {
//                            putExtra(ImagePath, outFile.absolutePath)
//                        })
//                        outStream.flush()
//                        outStream.close()
//                        finish()
//                    }
//
//                    override fun onError(e: Throwable) {}
//                })
        }
    }

    companion object {
        val ImagePath: String = "ImagePath"
    }
}