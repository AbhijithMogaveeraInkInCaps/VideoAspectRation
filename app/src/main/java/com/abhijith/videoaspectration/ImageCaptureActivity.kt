package com.abhijith.videoaspectration

import android.Manifest
import android.os.Bundle
import android.util.Size
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import com.abhijith.videoaspectration.databinding.ActivityImageCaptureBinding
import com.abhijith.videoaspectration.helper.*
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import java.io.File

class ImageCaptureActivity : AppCompatActivity() {

    private lateinit var camera: Camera
    private lateinit var imageCapture: ImageCapture
    private var currentRation = four_to_five

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ActivityImageCaptureBinding.inflate(layoutInflater).apply {
            setContentView(root)
            requestRuntimePermission()

            btnCaptureImage.setOnClickListener {
                onCaptureImage()
            }

            btnRatioOneToOne.setOnClickListener {
                if (currentRation != one_to_one) {
                    currentRation = one_to_one
                    setUpCamera()
                }
            }

            btnRatioThreeToTwo.setOnClickListener {
                if (currentRation != three_to_two) {
                    currentRation = three_to_two
                    setUpCamera()
                }
            }

            btnRatioFourToFive.setOnClickListener {
                if (currentRation != four_to_five) {
                    currentRation = four_to_five
                    setUpCamera()
                }
            }
        }
    }

    private fun ActivityImageCaptureBinding.requestRuntimePermission() {
        val multiplePermissionsListener = object : ShortenMultiplePermissionListener() {
            override fun onPermissionsChecked(report: MultiplePermissionsReport) {
                if (report.areAllPermissionsGranted()) {
                    setUpCamera()
                } else {
                    onPermissionDenied()
                }
            }
        }

        Dexter.withContext(this@ImageCaptureActivity)
            .withPermissions(Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO)
            .withListener(multiplePermissionsListener)
            .check()

    }

    private fun ActivityImageCaptureBinding.setupCameraProvider() {
        ProcessCameraProvider
            .getInstance(this@ImageCaptureActivity)
            .also { provider ->
                provider.addListener({
                    bindPreview(provider.get())
                }, ContextCompat.getMainExecutor(this@ImageCaptureActivity))
            }
    }

    private fun ActivityImageCaptureBinding.bindPreview(cameraProvider: ProcessCameraProvider) {

        val preview: Preview = Preview
            .Builder()
            .setTargetResolution(currentRation)
            .build()

        imageCapture = ImageCapture
            .Builder()
            .setTargetResolution(currentRation)
            .build()

        val cameraSelector: CameraSelector = CameraSelector.Builder()
            .requireLensFacing(CameraSelector.LENS_FACING_BACK)
            .build()
        previewView.layoutParams.height = currentRation.height
        cameraProvider.unbindAll()
        camera = cameraProvider.bindToLifecycle(
            this@ImageCaptureActivity,
            cameraSelector,
            preview,
            imageCapture
        )

        camera.let {
            preview.setSurfaceProvider(previewView.surfaceProvider)
        }
    }

    private fun onCaptureImage() {

        val imageSavedCallback = object : ImageCapture.OnImageSavedCallback {
            override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                makeToast(getString(R.string.image_capture_success))
            }

            override fun onError(exception: ImageCaptureException) {
                makeToast(
                    getString(
                        R.string.image_capture_error,
                        exception.message,
                        exception.imageCaptureError
                    )
                )
            }
        }
        val file = File(filesDir.absoluteFile, "temp.jpg")
        val outputFileOptions: ImageCapture.OutputFileOptions =
            ImageCapture.OutputFileOptions.Builder(file).build()
        imageCapture.takePicture(
            outputFileOptions,
            ContextCompat.getMainExecutor(this),
            imageSavedCallback
        )
    }

    private fun ActivityImageCaptureBinding.setUpCamera() {
        setupCameraProvider()
    }

    private fun ActivityImageCaptureBinding.onPermissionDenied() {
        makeToast(getString(R.string.permission_denied))
        finish()
    }
}

fun map(
    value: Int,
    rangeFromStart: Int,
    RangeFromEnd: Int,
    rangeToStart: Int,
    rangeTwoEnd: Int
): Int {
    return rangeToStart + ((rangeTwoEnd - rangeToStart) / (RangeFromEnd - rangeFromStart)) * (value - rangeFromStart)
}
