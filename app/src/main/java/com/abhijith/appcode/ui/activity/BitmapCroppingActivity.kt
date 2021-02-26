package com.abhijith.appcode.ui.activity

import android.graphics.*
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.abhijith.appcode.databinding.ActivityBitmapCroppingBinding
import java.io.File


class BitmapCroppingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBitmapCroppingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBitmapCroppingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val file = File(intent.getStringExtra(ImageCroppingActivity.ImagePath)!!)
        binding.ivImagePreview.setImageURI(Uri.fromFile(file))
        val originalImage = BitmapFactory.decodeFile(
            file.absolutePath,
            BitmapFactory.Options().apply {
                inMutable = true
            })
        binding.btnCrop.setOnClickListener {
            binding.ivImagePreview.setImageBitmap(scaleImage(originalImage))
        }

    }

    private fun scaleImage(originalImage:Bitmap):Bitmap {
        val background = Bitmap.createBitmap(1080, 1080, Bitmap.Config.ARGB_8888)
        val originalWidth: Float = originalImage.width.toFloat()
        val originalHeight: Float = originalImage.height.toFloat()
        val canvas = Canvas(background)
        val scale: Float = 1080 / originalWidth

        val xTranslation = 0.0f
        val yTranslation: Float = (1080 - originalHeight * scale) / 2.0f

        val transformation = Matrix()
        transformation.postTranslate(xTranslation, yTranslation)
        transformation.preScale(scale, scale)

        val paint = Paint()
        paint.isFilterBitmap = true
        canvas.drawBitmap(originalImage, transformation, paint)
        return background
    }
}