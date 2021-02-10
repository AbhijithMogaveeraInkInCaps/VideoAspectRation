package com.abhijith.videoaspectration
//
//import android.annotation.SuppressLint
//import android.content.Context
//import android.os.Bundle
//import android.widget.Toast
//import androidx.appcompat.app.AppCompatActivity
//import androidx.camera.core.ImageCapture
//import androidx.camera.core.ImageCaptureException
//import androidx.camera.view.CameraView
//import androidx.camera.view.PreviewView
//import androidx.camera.view.video.OnVideoSavedCallback
//import androidx.camera.view.video.OutputFileResults
//import androidx.core.content.ContextCompat
//import com.abhijith.videoaspectration.databinding.ActivityMainBinding
//import java.io.File
//
//@SuppressLint("UnsafeExperimentalUsageError","MissingPermission")
//class MainActivity : AppCompatActivity() {
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        ActivityMainBinding.inflate(layoutInflater).apply {
//            setContentView(root)
//            setUpViewBinding()
//            bindCamera()
//        }
//    }
//
//    private fun ActivityMainBinding.bindCamera() {
//        cameraView.bindToLifecycle(this@MainActivity)
//        cameraView.scaleType = PreviewView.ScaleType.FILL_CENTER
//        cameraView.isPinchToZoomEnabled = true
//    }
//
//    private fun ActivityMainBinding.setUpViewBinding() {
//
//        buttonToggleCamera.setOnClickListener {
//            cameraView.toggleCamera()
//        }
//
//        btnCaptureImage.setOnClickListener {
//            capturePic()
//        }
//
//        buttonRecordVideo.setOnClickListener {
//            if (cameraView.isRecording) {
//                stopRecordingVideo()
//            } else {
//                startRecordingVideo()
//            }
//        }
//    }
//
//    private fun ActivityMainBinding.startRecordingVideo() {
//
//        buttonToggleCamera.isEnabled = false
//        btnCaptureImage.isEnabled = false
//        buttonRecordVideo.setText(R.string.stop_record_video)
//
//        val file = File(filesDir.absoluteFile, "temp.mp4")
//
//        val callback = object : OnVideoSavedCallback {
//            override fun onVideoSaved(outputFileResults: OutputFileResults) {
//                makeToast(getString(R.string.video_record_success))
//            }
//
//            override fun onError(videoCaptureError: Int, message: String, cause: Throwable?) {
//                makeToast(getString(R.string.video_record_error, message, videoCaptureError))
//            }
//        }
//
//        cameraView.startRecording(file, ContextCompat.getMainExecutor(this@MainActivity), callback)
//
//    }
//
//    private fun ActivityMainBinding.stopRecordingVideo() {
//        cameraView.stopRecording()
//        buttonToggleCamera.isEnabled = true
//        btnCaptureImage.isEnabled = true
//        buttonRecordVideo.setText(R.string.start_record_video)
//    }
//
//    private fun ActivityMainBinding.capturePic() {
//
//        val file = File(filesDir.absoluteFile, "temp.jpg")
//        val outputFileOptions = ImageCapture.OutputFileOptions.Builder(file).build()
//        val callback = object : ImageCapture.OnImageSavedCallback {
//
//            override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
//                makeToast("Image saved")
//            }
//
//            override fun onError(exception: ImageCaptureException) {
//                makeToast("Error...!")
//            }
//        }
//
//        cameraView.takePicture(outputFileOptions, ContextCompat.getMainExecutor(this@MainActivity), callback)
//    }
//
//}
//
//fun Context.makeToast(str: String) {
//    Toast.makeText(this, str, Toast.LENGTH_SHORT).show()
//}