package com.abhijith.videoaspectration

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.opengl.GLSurfaceView
import android.os.Bundle
import android.os.Environment
import android.widget.FrameLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.Camera
import androidx.camera.core.CameraSelector
import androidx.camera.core.Preview
import androidx.camera.core.VideoCapture
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import com.abhijith.videoaspectration.databinding.ActivityVideoCaptureBinding
import com.abhijith.videoaspectration.helper.*
import com.daasuu.gpuv.camerarecorder.GPUCameraRecorderBuilder
import com.daasuu.gpuv.camerarecorder.LensFacing
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import java.io.File


const val video_path="VideoPath"
const val folder_main = "Filtered Videos"

class VideoCaptureActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityVideoCaptureBinding.inflate(layoutInflater)
    }

    private var camera: Camera? = null
    private var videoCapture: VideoCapture? = null
    private var currentRation = three_to_two
    private val recordedVideoFile:File by lazy{
        val f = File(Environment.getExternalStorageDirectory(), folder_main)
        if (!f.exists()) {
            f.mkdirs()
        }
        File(f.absolutePath, "temp.mp4")
    }

    @SuppressLint("RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        var isRecording = false

        binding.buttonRecordVideo.setOnClickListener {
            if (!isRecording) {
                isRecording = true
                makeToast("Recording started")
                startVideoRecording()
            }else{
                isRecording = false
                videoCapture?.stopRecording()
            }
        }
        binding.apply {
            btnRatioFourToFive.setOnClickListener {
                if(currentRation!= four_to_five){
                    currentRation = four_to_five
                    onPermissionGrant()
                }
            }
            btnRatioThreeToTwo.setOnClickListener {
                if(currentRation!= three_to_two){
                    currentRation = three_to_two
                    onPermissionGrant()
                }
            }
            btnRatioOneToOne.setOnClickListener {
                if(currentRation!= one_to_one){
                    currentRation = one_to_one
                    onPermissionGrant()
                }
            }
        }
        requestRuntimePermission()
    }

    private fun requestRuntimePermission() {
        Dexter.withContext(this)
            .withPermissions(Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO)
            .withListener(multiplePermissionsListener)
            .check()
    }

    private fun setupCameraProvider() {
        ProcessCameraProvider.getInstance(this).also { provider ->
            provider.addListener({
                bindPreview(provider.get())
            }, ContextCompat.getMainExecutor(this))
        }
    }

    @SuppressLint("RestrictedApi")
    private fun bindPreview(cameraProvider: ProcessCameraProvider) {

        binding.previewView.layoutParams.height = currentRation.height

        val preview: Preview = Preview.Builder()
            .setTargetResolution(currentRation)
            .build()

        videoCapture = VideoCapture.Builder()
            .setTargetResolution(currentRation)
            .build()

        val cameraSelector: CameraSelector = CameraSelector.Builder()
            .requireLensFacing(CameraSelector.LENS_FACING_BACK)
            .build()

        cameraProvider.unbindAll()

        camera = cameraProvider
            .bindToLifecycle(
                this@VideoCaptureActivity,
                cameraSelector,
                preview,
                videoCapture
            )
        camera?.let {
            preview.setSurfaceProvider(binding.previewView.surfaceProvider)
        }
    }

    @SuppressLint("RestrictedApi")
    private fun startVideoRecording() {
        val data: VideoCapture.OutputFileOptions =
            VideoCapture.OutputFileOptions.Builder(recordedVideoFile).build()
        videoCapture?.startRecording(data, ContextCompat.getMainExecutor(this), videoSavedCallback)
    }

    private val multiplePermissionsListener = object : ShortenMultiplePermissionListener() {
        override fun onPermissionsChecked(report: MultiplePermissionsReport) {
            if (report.areAllPermissionsGranted()) {
                onPermissionGrant()
            } else {
                onPermissionDenied()
            }
        }
    }

    private val videoSavedCallback = object : VideoCapture.OnVideoSavedCallback {
        override fun onVideoSaved(outputFileResults: VideoCapture.OutputFileResults) {
            showResultMessage(getString(R.string.video_record_success))
            Intent(this@VideoCaptureActivity, FilterActivity::class.java).apply {
                putExtra(video_path, recordedVideoFile.absolutePath)
                startActivity(this)
                finish()
            }
        }

        override fun onError(videoCaptureError: Int, message: String, cause: Throwable?) {
            showResultMessage(getString(R.string.video_record_error, message, videoCaptureError))
        }
    }

    private fun onPermissionGrant() {
        setupCameraProvider()
    }

    private fun onPermissionDenied() {
        showResultMessage(getString(R.string.permission_denied))
        finish()
    }

    private fun showResultMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}

/*
*
* app:ci_min_crop_width="40dp"
app:ci_min_crop_height="40dp"

cropView.configureOverlay()
  .setMinWidth(dps)
  .setMinHeight(dps)
  .apply();
* */