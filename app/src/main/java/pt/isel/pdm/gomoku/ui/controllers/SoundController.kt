package pt.isel.pdm.gomoku.ui.controllers

import android.content.Context
import android.media.MediaPlayer

object SoundController : Controller {

    private var mediaPlayer: MediaPlayer? = null
    override var enabled = true

    fun play(context: Context, resId: Int) {
        if (!enabled) return
        mediaPlayer = MediaPlayer.create(context, resId)
        mediaPlayer?.start()
    }

    override fun release() {
        mediaPlayer?.release()
        mediaPlayer = null
    }
}
