package com.abhijith.videoaspectration

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.annotation.AnyRes
import androidx.appcompat.app.AppCompatActivity
import com.daasuu.mp4compose.FillMode
import com.daasuu.mp4compose.composer.Mp4Composer
import com.daasuu.mp4compose.filter.GlFilterGroup
import com.daasuu.mp4compose.filter.GlMonochromeFilter
import java.io.File

class FilterActivity: AppCompatActivity() {
    @SuppressLint("SdCardPath")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.filter_activity)

        val f = File(intent.getStringExtra(video_path)!!)
        Toast.makeText(this, f.absolutePath, Toast.LENGTH_SHORT).show()
        val exoPlayer = findViewById<MySimpleExoPlayer>(R.id.mySimpleExoPlayer)
        exoPlayer.apply {
            play(Uri.fromFile(f))
        }

        findViewById<Button>(R.id.btnMonochrome).setOnClickListener {
            val file = File(f.parent, "mono.mp4")
            Toast.makeText(this@FilterActivity, "Start", Toast.LENGTH_SHORT).show()
            Mp4Composer(
                f.absolutePath,
                file.absolutePath
            ).apply {
//                rotation(Rotation.ROTATION_90)
                size(540, 540)
                    .fillMode(FillMode.PRESERVE_ASPECT_FIT)
                    .filter(GlFilterGroup(GlMonochromeFilter()))
                    .listener(object : Mp4Composer.Listener {
                        override fun onProgress(progress: Double) {
                        }

                        override fun onCurrentWrittenVideoTime(timeUs: Long) {

                        }

                        override fun onCompleted() {
                            runOnUiThread {
                                Toast.makeText(this@FilterActivity, "End", Toast.LENGTH_SHORT).show()
                                exoPlayer.freeMemory()
                                exoPlayer.play(Uri.fromFile(file))
                            }
                        }

                        override fun onCanceled() {

                        }

                        override fun onFailed(exception: Exception?) {
                            runOnUiThread {
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
}
internal fun Context.getResourceUri(@AnyRes resourceId: Int): Uri =
    Uri.Builder()
    .scheme(ContentResolver.SCHEME_FILE)
    .authority(packageName)
    .path(resourceId.toString())
    .build()