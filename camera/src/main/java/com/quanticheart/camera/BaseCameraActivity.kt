/*
 *
 *  *                                     /@
 *  *                      __        __   /\/
 *  *                     /==\      /  \_/\/
 *  *                   /======\    \/\__ \__
 *  *                 /==/\  /\==\    /\_|__ \
 *  *              /==/    ||    \=\ / / / /_/
 *  *            /=/    /\ || /\   \=\/ /
 *  *         /===/   /   \||/   \   \===\
 *  *       /===/   /_________________ \===\
 *  *    /====/   / |                /  \====\
 *  *  /====/   /   |  _________    /      \===\
 *  *  /==/   /     | /   /  \ / / /         /===/
 *  * |===| /       |/   /____/ / /         /===/
 *  *  \==\             /\   / / /          /===/
 *  *  \===\__    \    /  \ / / /   /      /===/   ____                    __  _         __  __                __
 *  *    \==\ \    \\ /____/   /_\ //     /===/   / __ \__  ______  ____ _/ /_(_)____   / / / /__  ____ ______/ /_
 *  *    \===\ \   \\\\\\\/   ///////     /===/  / / / / / / / __ \/ __ `/ __/ / ___/  / /_/ / _ \/ __ `/ ___/ __/
 *  *      \==\/     \\\\/ / //////       /==/  / /_/ / /_/ / / / / /_/ / /_/ / /__   / __  /  __/ /_/ / /  / /_
 *  *      \==\     _ \\/ / /////        |==/   \___\_\__,_/_/ /_/\__,_/\__/_/\___/  /_/ /_/\___/\__,_/_/   \__/
 *  *        \==\  / \ / / ///          /===/
 *  *        \==\ /   / / /________/    /==/
 *  *          \==\  /               | /==/
 *  *          \=\  /________________|/=/
 *  *            \==\     _____     /==/
 *  *           / \===\   \   /   /===/
 *  *          / / /\===\  \_/  /===/
 *  *         / / /   \====\ /====/
 *  *        / / /      \===|===/
 *  *        |/_/         \===/
 *  *                       =
 *  *
 *  * Copyright(c) Developed by John Alves at 2020/2/16 at 3:36:25 for quantic heart studios
 *
 */

package com.quanticheart.camera

import androidx.appcompat.app.AppCompatActivity
import com.camerakit.CameraKit
import com.camerakit.CameraKitView
import com.camerakit.CameraKitView.*
import com.camerakit.type.CameraFlash
import com.quanticheart.camera.file.getExternalFile
import java.io.File
import java.io.FileOutputStream
import java.io.IOException


abstract class BaseCameraActivity : AppCompatActivity() {
    private lateinit var cameraKitView: CameraKitView

    /**
     * Default configurations
     */
    private var cameraState =
        CameraState.BACK
    private var flashState =
        FlashState.OFF

    protected fun startCamera(
        cameraView: CameraKitView,
        startCameraState: CameraState = cameraState,
        startFlashState: FlashState = flashState
    ) {
        cameraState = startCameraState
        flashState = startFlashState
        getConfig(cameraView)
        cameraKitView.requestPermissions(this)
    }

    private fun getConfig(cameraView: CameraKitView) {
        cameraKitView = cameraView
//        cameraKitView.apply {
//            flash = if (flashState == FlashState.TORCH) CameraKit.FLASH_ON else CameraKit.FLASH_OFF
//            facing =
//                if (cameraState == CameraState.BACK) CameraKit.FACING_BACK else CameraKit.FACING_FRONT
//            sensorPreset = CameraKit.SENSOR_PRESET_NONE
//            previewEffect = CameraKit.PREVIEW_EFFECT_NONE
//            permissions = CameraKitView.PERMISSION_CAMERA
//            focus = CameraKit.FOCUS_AUTO
////            permissions = CameraKitView.PERMISSION_STORAGE
//        }

        cameraKitView.gestureListener = object : GestureListener {
            override fun onTap(cameraKitView: CameraKitView, v: Float, v1: Float) {}
            override fun onLongTap(cameraKitView: CameraKitView, v: Float, v1: Float) {}
            override fun onDoubleTap(cameraKitView: CameraKitView, v: Float, v1: Float) {}
            override fun onPinch(cameraKitView: CameraKitView, v: Float, v1: Float, v2: Float) {}
        }

        cameraKitView.cameraListener = object : CameraListener {
            override fun onOpened() {}
            override fun onClosed() {}
        }

        cameraKitView.previewListener = object : PreviewListener {
            override fun onStart() {}
            override fun onStop() {}
        }

        cameraKitView.errorListener = ErrorListener { cameraKitView, e -> }
    }

    /**
     * Take picture
     */
    protected fun takePicture(file: File? = null, takeBitmap: ((File?) -> Unit)? = null) {
        val fileFinal = file ?: getExternalFile()
        cameraKitView.captureImage { cameraKitView, capturedImage ->
            try {
                val outputStream = FileOutputStream(fileFinal.path)
                outputStream.write(capturedImage)
                outputStream.close()
                takeBitmap?.let { it(fileFinal) }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    /**
     * func
     */

    protected fun changeFlashStatus() {
        flashState = if (flashState == FlashState.OFF) FlashState.TORCH else FlashState.OFF
        cameraKitView.flash =
            if (flashState == FlashState.TORCH) CameraKit.FLASH_ON else CameraKit.FLASH_OFF
    }

    protected fun changeCameraStatus() {
        cameraState = if (cameraState == CameraState.BACK) CameraState.FRONT else CameraState.BACK
        cameraKitView.facing =
            if (cameraState == CameraState.BACK) CameraKit.FACING_BACK else CameraKit.FACING_FRONT
    }

    protected fun setFlashAuto() {
        flashState = FlashState.OFF
        cameraKitView.flash = CameraKit.FLASH_AUTO
    }

    protected fun setFlashOn() {
        flashState = FlashState.TORCH
        cameraKitView.flash = CameraKit.FLASH_TORCH
    }

    protected fun setFocusAuto() {
        cameraKitView.focus = CameraKit.FOCUS_AUTO
    }

    protected fun setFocusContinuous() {
        cameraKitView.focus = CameraKit.FOCUS_CONTINUOUS
    }

    protected fun setFocusOff() {
        cameraKitView.focus = CameraKit.FOCUS_OFF
    }

    protected fun toggleFacing() {
        cameraKitView.toggleFacing()
    }

    protected fun hasFlash(): Boolean = cameraKitView.hasFlash()
    protected fun getSupportedFlashTypes(): Array<CameraFlash> = cameraKitView.supportedFlashTypes


    protected fun setZoon(zoom: Float) {
        cameraKitView.zoomFactor = zoom
    }

    protected fun setAspectRatio(aspectRatio: Float) {
        cameraKitView.aspectRatio = aspectRatio
    }

    protected fun setImageMegaPixels(megaPixels: Float) {
        cameraKitView.imageMegaPixels = megaPixels
    }

//    protected fun setJpegQuality(quality: Float) {
//        this function have only in xml
//        app:camera_imageJpegQuality="100"
//        Possible values:
//        int: [0,100]
//    }

    /**
     * Enus for states
     */

    enum class CameraState {
        FRONT, BACK
    }

    enum class FlashState {
        TORCH, OFF
    }

    /**
     * init camera
     */

    override fun onStart() {
        super.onStart()
        cameraKitView.onStart()
    }

    override fun onResume() {
        super.onResume()
        cameraKitView.onResume()
    }

    override fun onPause() {
        cameraKitView.onPause()
        super.onPause()
    }

    override fun onStop() {
        cameraKitView.onStop()
        super.onStop()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        cameraKitView.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
}