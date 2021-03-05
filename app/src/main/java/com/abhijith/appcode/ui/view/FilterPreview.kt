package com.abhijith.appcode.ui.view

import android.content.Context
import android.graphics.LightingColorFilter
import android.net.Uri
import android.util.AttributeSet
import android.widget.Filter
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ScrollView
import com.abhijith.appcode.R
import com.abhijith.appcode.ui.activity.convertScale
import com.abhijith.filter.videofilters.filter.GlFilterGroup
import com.abhijith.filter.videofilters.filter.GlRGBFilter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import kotlin.math.roundToInt

class FilterPreview(context: Context?, attrs: AttributeSet?) : LinearLayout(context, attrs) {

    private lateinit var imageFilterChangedListener: ImageFilterChangedListener
    private lateinit var videoFilterChangedListener: VideoFilterChangedListener
    private var linerLayout:LinearLayout
    init {
        inflate(context, R.layout.filterpreview, this)
        val x = findViewById<LinearLayout>(R.id.itemContainer)
        linerLayout = x
    }

    fun setVideoPreviewItems(uri: Uri, vararg colorFilter: Filters){
        linerLayout.removeAllViews()
        colorFilter.forEach {
            ImageView(context).apply {
                layoutParams = LayoutParams(context.dpToPx(100), context.dpToPx(100))
                Glide.with(context)
                    .load(uri) // or URI/path
                    .skipMemoryCache(true)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .into(this@apply);
//                setImageDrawable(context.getDrawable(R.drawable.default_image))
                linerLayout.addView(this)
                this.colorFilter = it.imageColorFilter
                setOnClickListener { _->
                    if(this@FilterPreview::videoFilterChangedListener.isInitialized){
                        videoFilterChangedListener.onFilterChanged(it.videoColorFilter)
                    }
                }
            }
        }
    }

    fun setImagePreviewItems(uri: Uri, vararg colorFilter: Filters) {
        linerLayout.removeAllViews()
        colorFilter.forEach {
            ImageView(context).apply {
                layoutParams = LayoutParams(context.dpToPx(100), context.dpToPx(100))
                setImageURI(uri)
                linerLayout.addView(this)
                this.colorFilter = it.imageColorFilter
                setOnClickListener { _->
                    if(this@FilterPreview::imageFilterChangedListener.isInitialized){
                        imageFilterChangedListener.onFilterChanged(it.imageColorFilter)
                    }
                }
            }
        }
    }


    fun setOnImageFilterChangedListener(imageFilterChangedListener: ImageFilterChangedListener) {
        this.imageFilterChangedListener = imageFilterChangedListener
    }

    fun setOnVideoFilterChangedListener(imageFilterChangedListener: VideoFilterChangedListener) {
        this.videoFilterChangedListener = imageFilterChangedListener
    }

    interface ImageFilterChangedListener {
        fun onFilterChanged(colorFilter: LightingColorFilter)
    }
    interface VideoFilterChangedListener {
        fun onFilterChanged(colorFilter: GlFilterGroup)
    }
}

sealed class Filters(val imageColorFilter: LightingColorFilter, val videoColorFilter: GlFilterGroup){

    object ColorOne:Filters(
        LightingColorFilter(0xFF968273.toInt(), 0x00000000), GlFilterGroup(
            GlRGBFilter().apply {
                this.setRed(convertScale(154))
                this.setGreen(convertScale(131))
                this.setBlue(convertScale(114))
            })
    )

    object ColorTwo:Filters(
        LightingColorFilter(0x0072618f, 0x00000000),
        GlFilterGroup(GlRGBFilter().apply {
            this.setRed(convertScale(114))
            this.setGreen(convertScale(97))
            this.setBlue(convertScale(143))
        })
    )

    object ColorThree:Filters(
        LightingColorFilter(0x00678f75, 0x00000000),
        GlFilterGroup(GlRGBFilter().apply {
            this.setRed(convertScale(103))
            this.setGreen(convertScale(143))
            this.setBlue(convertScale(117))
        })
    )

    object ColorFour:Filters(
        LightingColorFilter(0x00375773, 0x00000000),
        GlFilterGroup(GlRGBFilter().apply {
            this.setRed(convertScale(55))
            this.setGreen(convertScale(87))
            this.setBlue(convertScale(115))
        })
    )

    companion object{
        fun getAllFilters()= arrayOf(ColorOne,ColorTwo,ColorThree,ColorFour)
    }
}

fun Context.dpToPx(dp: Int): Int {
    val density: Float = resources
        .displayMetrics.density
    return (dp.toFloat() * density).roundToInt()
}
