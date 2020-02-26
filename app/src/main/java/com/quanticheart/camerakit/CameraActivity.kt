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
 *  * Copyright(c) Developed by John Alves at 2020/2/15 at 11:40:2 for quantic heart studios
 *
 */

package com.quanticheart.camerakit

import android.os.Bundle
import com.quanticheart.camera.BaseCameraActivity
import com.quanticheart.camera.extentions.setSeekBarListener
import com.quanticheart.camera.extentions.setThumb
import kotlinx.android.synthetic.main.activity_simple.*

class CameraActivity : BaseCameraActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_simple)
        startCamera(camera)
        /**
         * init
         */
        initListeners()
    }

    private fun initListeners() {
        btnTrade.setOnClickListener { changeCameraStatus() }
        btnFlash.setOnClickListener { changeFlashStatus() }

        btnFlashOn.setOnClickListener { setFlashOn() }
        btnFlashAuto.setOnClickListener { setFlashAuto() }

        btnAction.setOnClickListener {
            takePicture {
                thunb.setThumb(it!!)
            }
        }

        zoom.setSeekBarListener {
            setZoon(it)
        }

        aspect.setSeekBarListener {
            setAspectRatio(it)
        }

        pixels.setSeekBarListener {
            setImageMegaPixels(it)
        }
    }
}
