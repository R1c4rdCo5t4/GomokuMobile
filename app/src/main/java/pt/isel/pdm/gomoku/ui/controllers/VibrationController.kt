package pt.isel.pdm.gomoku.ui.controllers

import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import java.lang.ref.WeakReference

object VibrationController : Controller {

    private var contextRef: WeakReference<Context?> = WeakReference(null)
    private val context get() = contextRef.get()
    override var enabled = false

    fun init(ctx: Context) {
        val hasVibrator = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val vibratorManager = ctx.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
            vibratorManager.defaultVibrator.hasVibrator()
        } else {
            @Suppress("DEPRECATION")
            val vibrator = ctx.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            vibrator.hasVibrator()
        }
        contextRef = WeakReference(if (hasVibrator) ctx.applicationContext else null)
    }

    @Suppress("DEPRECATION")
    fun vibrate(duration: Long) {
        if (!enabled) return
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val vibratorManager = context?.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
            vibratorManager.defaultVibrator.run {
                cancel()
                vibrate(VibrationEffect.createOneShot(duration, VibrationEffect.DEFAULT_AMPLITUDE))
            }
        } else {
            val vibrator = context?.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            vibrator.cancel()
            if (Build.VERSION.SDK_INT >= 26) {
                vibrator.vibrate(VibrationEffect.createOneShot(duration, VibrationEffect.DEFAULT_AMPLITUDE))
            } else {
                vibrator.vibrate(duration)
            }
        }
    }

    override fun release() {
        contextRef.clear()
    }
}
