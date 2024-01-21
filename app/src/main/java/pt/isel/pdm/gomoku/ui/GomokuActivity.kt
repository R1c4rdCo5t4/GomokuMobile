package pt.isel.pdm.gomoku.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import pt.isel.pdm.gomoku.Dependencies
import pt.isel.pdm.gomoku.services.GomokuService
import pt.isel.pdm.gomoku.storage.links.Links
import pt.isel.pdm.gomoku.storage.session.Session
import pt.isel.pdm.gomoku.storage.settings.Settings
import pt.isel.pdm.gomoku.ui.controllers.SoundController
import pt.isel.pdm.gomoku.ui.controllers.VibrationController

abstract class GomokuActivity : ComponentActivity() {

    val dependencies by lazy { application as Dependencies }
    abstract val viewModel: GomokuViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
    }

    override fun onStart() {
        super.onStart()
        viewModel.loadSettings()
    }

    override fun onDestroy() {
        super.onDestroy()
        SoundController.release()
        VibrationController.release()
    }

    inline fun <reified T : ViewModel> getViewModel(context: Context) =
        createViewModel {
            T::class.java
                .getConstructor(
                    GomokuService::class.java,
                    Links::class.java,
                    Session::class.java,
                    Settings::class.java,
                    Context::class.java
                )
                .newInstance(
                    dependencies.service,
                    dependencies.links,
                    dependencies.session,
                    dependencies.settings,
                    context
                )
        }

    inline fun <reified T : ViewModel> ComponentActivity.createViewModel(crossinline block: () -> T) =
        viewModels<T> {
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T = block() as T
            }
        }

    companion object {
        const val FLAG_CLEAR_BACK_STACK = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
    }
}
