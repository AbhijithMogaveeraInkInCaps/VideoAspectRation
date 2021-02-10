package com.abhijith.videoaspectration

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import androidx.appcompat.app.AppCompatActivity
import com.abhijith.videoaspectration.databinding.ActivityImageCropingBinding
import com.abhijith.videoaspectration.helper.four_to_five
import com.abhijith.videoaspectration.helper.one_to_one
import com.abhijith.videoaspectration.helper.three_to_two
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import jp.wasabeef.glide.transformations.CropTransformation
import java.io.File
import java.io.FileOutputStream


class ImageCroppingActivity : AppCompatActivity() {

    lateinit var myPath:String
    lateinit var binding:ActivityImageCropingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityImageCropingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        myPath = intent.getStringExtra(ImagePath)!!

        Glide.with(this).load(Uri.fromFile(File(myPath)))
            .apply(RequestOptions().override(one_to_one.width, one_to_one.height))
            .into(binding.ivImagePreview)

        binding.btnRatioThreeToTwo.setOnClickListener {
            Glide.with(this).load(Uri.fromFile(File(myPath)))
                .transform(CropTransformation(three_to_two.width, three_to_two.height))
                .into(binding.ivImagePreview)
        }

        binding.btnRatioFourToFive.setOnClickListener {
            Glide.with(this).load(Uri.fromFile(File(myPath)))
                .transform(CropTransformation(four_to_five.width, four_to_five.height))
                .into(binding.ivImagePreview)
        }

        binding.btnRatioOneToOne.setOnClickListener {
            Glide.with(this).load(Uri.fromFile(File(myPath)))
                .transform(CropTransformation(one_to_one.width, one_to_one.height))
                .into(binding.ivImagePreview)
        }

        binding.btnSave.setOnClickListener {
            val bitmap = ((binding.ivImagePreview.drawable) as BitmapDrawable).bitmap
            var outStream: FileOutputStream? = null
            val sdCard: File = Environment.getExternalStorageDirectory()
            val dir = File(sdCard.absolutePath + "/NewAbhi")
            dir.mkdirs()
            val fileName = String.format("%d.jpg", System.currentTimeMillis())
            val outFile = File(dir, fileName)
            outStream = FileOutputStream(outFile)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outStream)


            startActivity(Intent(this@ImageCroppingActivity,ImageViewActivity::class.java).apply {
                putExtra(ImagePath,outFile.absolutePath)
            })
            outStream.flush()
            outStream.close()
            finish()
        }
    }

    companion object{
        val ImagePath:String="ImagePath"
    }
}