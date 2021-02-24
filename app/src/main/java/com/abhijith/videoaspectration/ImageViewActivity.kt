package com.abhijith.videoaspectration

//import com.zomato.photofilters.geometry.Point
//import com.zomato.photofilters.imageprocessors.Filter
//import com.zomato.photofilters.imageprocessors.subfilters.ContrastSubFilter
//import com.zomato.photofilters.imageprocessors.subfilters.ToneCurveSubFilter
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.abhijith.photofilters.SampleFilters
import com.abhijith.photofilters.geometry.Point
import com.abhijith.photofilters.imageprocessors.Filter
import com.abhijith.photofilters.imageprocessors.subfilters.ToneCurveSubFilter
import com.abhijith.videoaspectration.databinding.ActivityImageViewBinding
//import com.zomato.photofilters.SampleFilters
import java.io.File


class ImageViewActivity : AppCompatActivity() {

    companion object {init {
        System.loadLibrary("NativeImageProcessor")
    }}

    private lateinit var binding: ActivityImageViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        binding = ActivityImageViewBinding.inflate(layoutInflater)

        setContentView(binding.root)

        val file = File(
            intent.getStringExtra(
                ImageCroppingActivity.ImagePath
            )!!
        )

        binding.ivImagePreview.setImageURI(
            Uri.fromFile(
                file
            )
        )

        binding.btn1.setOnClickListener {
//            val myFilter = Filter()
//            val rgbKnots: Array<Point?> = arrayOfNulls(3)
//            rgbKnots[0] = Point(0F, 0F)
//            rgbKnots[1] = Point(175F, 139F)
//            rgbKnots[2] = Point(255F, 255F)
            val myFilter = Filter()
//          myFilter.addSubFilter()
            binding.ivImagePreview
                .setImageBitmap(
                    SampleFilters
                        .getBlueMessFilter()
                        .processFilter(BitmapFactory.decodeFile(file.absolutePath,BitmapFactory.Options().apply {
                            inMutable = true
                        }))
                )
        }

        binding.btn2.setOnClickListener {
            val myFilter = Filter()
            val rgbKnots: Array<Point?> = arrayOfNulls(3)
            rgbKnots[0] = Point(0F, 0F)
            rgbKnots[1] = Point(125F, 139F)
            rgbKnots[2] = Point(200F, 145F)
            myFilter.addSubFilter(ToneCurveSubFilter(rgbKnots, null, null, null))
            val outputImage = myFilter.processFilter(BitmapFactory.decodeFile(file.absolutePath,BitmapFactory.Options().apply {
                inMutable = true
            }))
            binding.ivImagePreview.setImageBitmap(outputImage)
        }

        binding.btn3.setOnClickListener {
            val myFilter = Filter()
            val rgbKnots: Array<Point?> = arrayOfNulls(3)
            rgbKnots[0] = Point(0F, 0F)
            rgbKnots[1] = Point(133F, 139F)
            rgbKnots[2] = Point(234F, 255F)
            myFilter.addSubFilter(ToneCurveSubFilter(rgbKnots, null, null, null))
            val outputImage = myFilter.processFilter(
                BitmapFactory.decodeFile(
                    file.absolutePath,
                    BitmapFactory.Options().apply {
                        inMutable = true
                    })
            )
            binding.ivImagePreview.setImageBitmap(outputImage)
        }

        binding.btn4.setOnClickListener {
            val myFilter = Filter()
            val rgbKnots: Array<Point?> = arrayOfNulls(3)
            rgbKnots[0] = Point(0F, 0F)
            rgbKnots[1] = Point(135F, 129F)
            rgbKnots[2] = Point(235F, 265F)
            myFilter.addSubFilter(ToneCurveSubFilter(rgbKnots, null, null, null))
            val outputImage = myFilter.processFilter(BitmapFactory.decodeFile(file.absolutePath,BitmapFactory.Options().apply {
                inMutable = true
            }))
            binding.ivImagePreview.setImageBitmap(outputImage)
        }

        binding.btn5.setOnClickListener {
            val myFilter = Filter()
            val rgbKnots: Array<Point?> = arrayOfNulls(3)
            rgbKnots[0] = Point(0F, 0F)
            rgbKnots[1] = Point(235F, 129F)
            rgbKnots[2] = Point(125F, 235F)
            myFilter.addSubFilter(ToneCurveSubFilter(rgbKnots, null, null, null))
            val outputImage = myFilter.processFilter(BitmapFactory.decodeFile(file.absolutePath,BitmapFactory.Options().apply {
                inMutable = true
            }))
            binding.ivImagePreview.setImageBitmap(outputImage)
        }

        binding.btn6.setOnClickListener {
            val myFilter = Filter()
            val rgbKnots: Array<Point?> = arrayOfNulls(3)
            rgbKnots[0] = Point(0F, 0F)
            rgbKnots[1] = Point(135F, 139F)
            rgbKnots[2] = Point(235F, 235F)
            myFilter.addSubFilter(ToneCurveSubFilter(rgbKnots, null, null, null))
            val outputImage = myFilter.processFilter(BitmapFactory.decodeFile(file.absolutePath,BitmapFactory.Options().apply {
                inMutable = true
            }))
            binding.ivImagePreview.setImageBitmap(outputImage)
        }

    }
}