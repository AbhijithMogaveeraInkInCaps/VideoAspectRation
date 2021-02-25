package com.abhijith.videoaspectration

//import com.zomato.photofilters.geometry.Point
//import com.zomato.photofilters.imageprocessors.Filter
//import com.zomato.photofilters.imageprocessors.subfilters.ContrastSubFilter
//import com.zomato.photofilters.imageprocessors.subfilters.ToneCurveSubFilter
//import com.zomato.photofilters.SampleFilters
import android.graphics.BitmapFactory
import android.graphics.LightingColorFilter
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.abhijith.filtercombination.FilterEnum
import com.abhijith.filtercombination.FilterList
import com.abhijith.videoaspectration.databinding.ActivityImageViewBinding
import java.io.File


class ImageViewActivity : AppCompatActivity() {

    companion object {init {
        System.loadLibrary("NativeImageProcessor")
    }
    }

    private lateinit var binding: ActivityImageViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityImageViewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val file = File(intent.getStringExtra(ImageCroppingActivity.ImagePath)!!)
        binding.ivImagePreview.setImageURI(Uri.fromFile(file))

        val l = 0x00000000
        binding.btnCotrastFilter.setOnClickListener {
            binding.ivImagePreview.clearColorFilter()
            binding.ivImagePreview.colorFilter = LightingColorFilter(0x0072618f, 0x00000000)
//            val outputImage =
//                FilterList(this).getFilter(FilterEnum.CONTRAST_FILTER).imageFilter.processFilter(
//                    BitmapFactory.decodeFile(
//                        file.absolutePath,
//                        BitmapFactory.Options().apply {
//                            inMutable = true
//                        })
//                )
//            binding.ivImagePreview.setImageBitmap(outputImage)
        }

        binding.btnBrightnessFilter.setOnClickListener {
            binding.ivImagePreview.clearColorFilter()
            binding.ivImagePreview.colorFilter = LightingColorFilter(0xFF968273.toInt(),0x00000000)
//            val outputImage =
//                FilterList(this).getFilter(FilterEnum.BRIGHTNESS_FILTER).imageFilter.processFilter(
//                    BitmapFactory.decodeFile(
//                        file.absolutePath,
//                        BitmapFactory.Options().apply {
//                            inMutable = true
//                        })
//                )
//            binding.ivImagePreview.setImageBitmap(outputImage)
//            binding.ivImagePreview.colorFilter = LightingColorFilter(l.toInt(), 0x00968372)
        }

        binding.btnSaturation.setOnClickListener {
            binding.ivImagePreview.clearColorFilter()
            binding.ivImagePreview.colorFilter = LightingColorFilter(0x00678f75, 0x00000000)
//            val outputImage =
//                FilterList(this).getFilter(FilterEnum.SATURATION_FILTER).imageFilter.processFilter(
//                    BitmapFactory.decodeFile(
//                        file.absolutePath,
//                        BitmapFactory.Options().apply {
//                            inMutable = true
//                        })
//                )
//            binding.ivImagePreview.setImageBitmap(outputImage)
        }

        binding.btnVignette.setOnClickListener {
            binding.ivImagePreview.clearColorFilter()
            binding.ivImagePreview.colorFilter = LightingColorFilter(0x00375773, 0x00000000)
//                        val outputImage = FilterList(this).getFilter(FilterEnum.VIGNETTE_FILTER).imageFilter.processFilter(
//                            BitmapFactory.decodeFile(
//                                file.absolutePath,
//                                BitmapFactory.Options().apply {
//                                    inMutable = true
//                                })
//                        )
//                        binding.ivImagePreview.setImageBitmap(outputImage)
        }
    }
}


fun convertScale(oldValueToConvert: Int): Float {
    // Old Scale 50-100
    val oldScaleMin = 0
    val oldScaleMax = 255
    val oldScaleRange = (oldScaleMax - oldScaleMin)

    //new Scale 0-1
    val newScaleMin = 0.0f
    val newScaleMax = 1.0f
    val newScaleRange = (newScaleMax - newScaleMin)

    return ((oldValueToConvert - oldScaleMin) * newScaleRange / oldScaleRange) + newScaleMin
}