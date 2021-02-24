package com.abhijith.videoaspectration

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.AnyRes
import androidx.appcompat.app.AppCompatActivity
import com.abhijith.videoaspectration.databinding.FilterActivityBinding
import com.abhijith.filters.FillMode
import com.abhijith.filters.composer.Mp4Composer
import com.abhijith.filters.filter.*
import com.zomato.photofilters.imageprocessors.Filter
import java.io.File

class FilterActivity : AppCompatActivity() {
    @SuppressLint("SdCardPath")
    lateinit var binding: FilterActivityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = FilterActivityBinding.inflate(layoutInflater)

        setContentView(binding.root)

        val srcFile = File(intent.getStringExtra(video_path)!!)
        val destFile = File(srcFile.parent, "mono.mp4")

        binding.mySimpleExoPlayer.apply {
            play(Uri.fromFile(srcFile))
        }

        binding.btnMonochrome.setOnClickListener {
            val glFilterGroup = GlFilterGroup(GlMonochromeFilter().apply {
                //customize filter here
            })
            applyFilter(srcFile, destFile, glFilterGroup, binding.mySimpleExoPlayer)
        }

        binding.btnGlayScale.setOnClickListener {
            val glFilterGroup = GlFilterGroup(GlGrayScaleFilter().apply {
                //customize filter here
            })
            applyFilter(srcFile, destFile, glFilterGroup, binding.mySimpleExoPlayer)
        }

        binding.btnHaze.setOnClickListener {
            val glFilterGroup = GlFilterGroup(GlHazeFilter().apply {
                //customize filter here
            })
            applyFilter(srcFile, destFile, glFilterGroup, binding.mySimpleExoPlayer)
        }

        binding.btnSaturation.setOnClickListener {
            val glFilterGroup = GlFilterGroup(GlSaturationFilter().apply {
                setSaturation(10f)
            })
            applyFilter(srcFile, destFile, glFilterGroup, binding.mySimpleExoPlayer)
        }

        binding.btnSolarizeFilter.setOnClickListener {
            val glFilterGroup = GlFilterGroup(GlSolarizeFilter().apply {
                //customize filter here
            })
            applyFilter(srcFile, destFile, glFilterGroup, binding.mySimpleExoPlayer)
        }

        binding.btnVignette.setOnClickListener {
            val glFilterGroup = GlFilterGroup(GlVignetteFilter().apply {
                //customize filter here
            })
            applyFilter(srcFile, destFile, glFilterGroup, binding.mySimpleExoPlayer)
        }

    }

    private fun applyFilter(
        srcFile: File,
        destFile: File,
        glFilterGroup: GlFilterGroup,
        exoPlayer: MySimpleExoPlayer
    ) {
        Toast.makeText(this@FilterActivity, "Filtering Start", Toast.LENGTH_SHORT).show()
        Mp4Composer(srcFile.absolutePath, destFile.absolutePath).apply {
            size(540, 540)
                .fillMode(FillMode.PRESERVE_ASPECT_FIT)
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
        }
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