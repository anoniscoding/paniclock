package com.anoniscoding.paniclock.ui.screens.service


import android.Manifest
import android.annotation.SuppressLint
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.PixelFormat
import android.hardware.Camera
import android.hardware.camera2.CameraCharacteristics
import android.hardware.camera2.CameraManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.media.CamcorderProfile
import android.media.MediaRecorder
import android.os.*
import android.view.*
import androidx.core.content.ContextCompat
import com.anoniscoding.paniclock.domain.repositories.UserRepository
import com.anoniscoding.paniclock.models.CachedVideoInfo
import com.anoniscoding.paniclock.ui.screens.service.getParams
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class BackgroundVideoRecorder: Service(), SurfaceHolder.Callback {
    @Inject
    lateinit var _repository: UserRepository
    private var mCamera: Camera? = null

    private var windowManager: WindowManager? = null
    private var surfaceView: SurfaceView? = null
    var filepath = ""
    var recorder: MediaRecorder? = null
    var recording = false

    val videoInfo = CachedVideoInfo(path = "", timestamp = "", latlng = "")

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        windowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager
        recorder = MediaRecorder()
        surfaceView = SurfaceView(this)
        windowManager?.addView(surfaceView, getParams())
        surfaceView?.holder?.addCallback(this)

        initRecorder()
    }

    private fun initRecorder() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
            == PackageManager.PERMISSION_GRANTED) {
            mCamera = Camera.open(1)
            mCamera?.unlock()
            recorder?.setPreviewDisplay(surfaceView?.holder?.surface)
            recorder?.setCamera(mCamera)
            recorder?.setVideoSource(MediaRecorder.VideoSource.DEFAULT)
            recorder?.setAudioSource(MediaRecorder.AudioSource.DEFAULT)
            val cpHigh = CamcorderProfile.get(CamcorderProfile.QUALITY_HIGH)
            recorder?.setProfile(cpHigh)
            filepath = createVideoFile().absolutePath
            recorder?.setOutputFile(filepath)
            recorder?.setMaxDuration(RECORDING_LENGTH_IN_MS) // 60 seconds
        }
    }

    @Throws(IOException::class)
    private fun createVideoFile(): File {
        // Create a video file name
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File? = this.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "VIDEO_${timeStamp}_",
            ".mp4",
            storageDir
        )
    }

    private fun prepareRecorder() {
        try {
            recorder?.prepare()
            recorder?.start()
            recording = true
            if (isLocationEnabled()) requestLocationData()
            startCountDownToEndRecording()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun startCountDownToEndRecording() {
        Handler().postDelayed({
            recorder?.stop()
            recorder?.release()
            recording = false
            val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
            videoInfo.apply { 
                path = filepath
                timestamp = timeStamp
            }
            _repository.saveVideoPath(videoInfo)
            stopSelf()
        }, RECORDING_LENGTH_IN_MS.toLong())
    }

    private fun isLocationEnabled() = getSystemService(LocationManager::class.java)
        .isProviderEnabled(LocationManager.GPS_PROVIDER)
            || getSystemService(LocationManager::class.java)
        .isProviderEnabled(LocationManager.NETWORK_PROVIDER)

    private fun requestLocationData() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_GRANTED) {
            val mLocationManager =
                this.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            mLocationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                0,
                100.0f,
                _locationListener
            )
        }
    }

    private val _locationListener: LocationListener = LocationListener { p0 ->
        videoInfo.apply {
            latlng = "Lat: ${p0.latitude}\nLng: ${p0.longitude}"
        }
    }

    override fun surfaceChanged(p0: SurfaceHolder, p1: Int, p2: Int, p3: Int) {}

    override fun surfaceDestroyed(p0: SurfaceHolder) {
        if (recording) {
            recorder?.stop()
            recording = false
        }
        recorder?.release()
        mCamera?.release()
        stopSelf()
    }

    override fun surfaceCreated(p0: SurfaceHolder) {
        prepareRecorder()
    }

    companion object {
        const val RECORDING_LENGTH_IN_MS = 60000
    }
}