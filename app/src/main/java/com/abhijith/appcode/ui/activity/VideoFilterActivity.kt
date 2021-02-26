package com.abhijith.appcode.ui.activity

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.content.Context
import android.media.MediaMetadataRetriever
import android.net.Uri
import android.os.Bundle
import com.daasuu.gpuv.egl.filter.*;
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.annotation.AnyRes
import androidx.appcompat.app.AppCompatActivity
import com.abhijith.appcode.databinding.FilterActivityBinding
import com.abhijith.appcode.ui.view.MySimpleExoPlayer
import com.abhijith.filter.videofilters.FillMode
import com.abhijith.filter.videofilters.FillModeCustomItem
import com.abhijith.filter.videofilters.composer.Mp4Composer
//import com.abhijith.filter.videofilters.filter.*
import com.daasuu.gpuv.player.GPUPlayerView
import java.io.File


class FilterActivity : AppCompatActivity() {
    @SuppressLint("SdCardPath")
    lateinit var binding: FilterActivityBinding
    val gpuPlayerView by lazy{
        GPUPlayerView(this)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = FilterActivityBinding.inflate(layoutInflater)

        setContentView(binding.root)

        val srcFile = File(intent.getStringExtra(video_path)!!)
        val destFile = File(srcFile.parent, "mono.mp4")

        binding.mySimpleExoPlayer.apply {
            play(Uri.fromFile(srcFile))
        }
//
        // set SimpleExoPlayer
        gpuPlayerView.setSimpleExoPlayer(binding.mySimpleExoPlayer.simpleExoPlayer)
        gpuPlayerView.setLayoutParams(
            RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
        )
        // add gpuPlayerView to WrapperView
        binding.layoutMovieWrapper.addView(gpuPlayerView)
        gpuPlayerView.onResume()
        //


        binding.btnColorOne.setOnClickListener {
            applyFilter(srcFile, destFile, GlFilterGroup(GlRGBFilter().apply {
                this.setRed(convertScale(154))
                this.setGreen(convertScale(131))
                this.setBlue(convertScale(114))
            }), binding.mySimpleExoPlayer)
//            applyFilter(srcFile, destFile, FilterList(this).getFilter(FilterEnum.TONE_CURVE_FILTER).videoFilter, binding.mySimpleExoPlayer)
        }

        binding.btnColorTwo.setOnClickListener {
//            applyFilter(srcFile, destFile, FilterList(this).getFilter(FilterEnum.CONTRAST_FILTER).videoFilter, binding.mySimpleExoPlayer)
            applyFilter(srcFile, destFile, GlFilterGroup(GlRGBFilter().apply {
                this.setRed(convertScale(114))
                this.setGreen(convertScale(97))
                this.setBlue(convertScale(143))
            }), binding.mySimpleExoPlayer)

        }

/*        binding.btnHaze.setOnClickListener {
            val glFilterGroup = GlFilterGroup(GlHazeFilter().apply {
                //customize filter here
            })
            applyFilter(srcFile, destFile, glFilterGroup, binding.mySimpleExoPlayer)
        }*/

        binding.btnColorThree.setOnClickListener {
//            applyFilter(srcFile, destFile, FilterList(this).getFilter(FilterEnum.SATURATION_FILTER).videoFilter, binding.mySimpleExoPlayer)
            applyFilter(srcFile, destFile, GlFilterGroup(GlRGBFilter().apply {
                this.setRed(convertScale(103))
                this.setGreen(convertScale(143))
                this.setBlue(convertScale(117))
            }), binding.mySimpleExoPlayer)
        }

/*
        binding.btnSolarizeFilter.setOnClickListener {
            val glFilterGroup = GlFilterGroup(GlSolarizeFilter().apply {
                //customize filter here
            })
            applyFilter(srcFile, destFile, glFilterGroup, binding.mySimpleExoPlayer)
        }
*/

        binding.btnColorFour.setOnClickListener {
            applyFilter(srcFile, destFile, GlFilterGroup(GlRGBFilter().apply {
                this.setRed(convertScale(55))
                this.setGreen(convertScale(87))
                this.setBlue(convertScale(115))
            }), binding.mySimpleExoPlayer)
//            applyFilter(srcFile, destFile, FilterList(this).getFilter(FilterEnum.VIGNETTE_FILTER).videoFilter, binding.mySimpleExoPlayer)

        }

    }

    private fun applyFilter(
        srcFile: File,
        destFile: File,
        glFilterGroup: GlFilterGroup,
        exoPlayer: MySimpleExoPlayer
    ) {
        Toast.makeText(this@FilterActivity, "Filtering Start", Toast.LENGTH_SHORT).show()
        gpuPlayerView.setGlFilter(glFilterGroup)

        /*val metaRetriever = MediaMetadataRetriever()
        metaRetriever.setDataSource(srcFile.absolutePath)
        val originalHeight =
            metaRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_HEIGHT)
        val originalWidth =
            metaRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_WIDTH)


        val scale: Float = 1080 / originalHeight.toString().toFloat()
        val rotate = 0f
        val translateX = 0f
        val translateY: Float = (1080 - originalHeight.toString().toFloat() * scale) / 2.0f
        val videoWidth = 1080f
        val videoHeight = 1080f


        Mp4Composer(srcFile.absolutePath, destFile.absolutePath).apply {
            size(1080, 1080)
                .fillMode(FillMode.CUSTOM)
                .customFillMode(
                    FillModeCustomItem(
                        scale,
                        rotate,
                        translateX,
                        translateY,
                        videoWidth,
                        videoHeight
                    )
                )
                .filter(glFilterGroup)
                .listener(object : Mp4Composer.Listener {
                    override fun onProgress(progress: Double) {
                    }

                    override fun onCurrentWrittenVideoTime(timeUs: Long) {

                    }

                    override fun onCompleted() {
                        runOnUiThread {
                            Toast.makeText(this@FilterActivity, "End", Toast.LENGTH_SHORT).show()
                            exoPlayer.freeMemory()
                            exoPlayer.play(Uri.fromFile(destFile))
                        }
                    }

                    override fun onCanceled() {
                    }

                    override fun onFailed(exception: Exception?) {
                        runOnUiThread {
                            exoPlayer.freeMemory()
                            Toast.makeText(
                                this@FilterActivity,
                                "Error ${exception.toString()}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                })
            start()
        }*/
    }
}

internal fun Context.getResourceUri(@AnyRes resourceId: Int): Uri =
    Uri.Builder()
        .scheme(ContentResolver.SCHEME_FILE)
        .authority(packageName)
        .path(resourceId.toString())
        .build()

/*
* for more filter refer
* https://github.com/MasayukiSuda/Mp4Composer-android/tree/master/mp4compose/src/main/java/com/daasuu/mp4compose/filter
*
* */

/*
* 150,131,114 ||96,83,72 => brightess
* 114 97 143||72, 61,8F => contrast
* 103 143 117||67,8F,75 => saturation
* 55, 87, 115||37,57,73=>vigentte
* 156, 133, 115||9C,85,73
* 199, 151, 169||c7,97,a9
* */