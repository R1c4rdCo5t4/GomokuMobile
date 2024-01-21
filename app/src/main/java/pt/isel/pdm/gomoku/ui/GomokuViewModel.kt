package pt.isel.pdm.gomoku.ui

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.launch
import pt.isel.pdm.gomoku.GomokuApplication.Companion.logger
import pt.isel.pdm.gomoku.R
import pt.isel.pdm.gomoku.services.GomokuService
import pt.isel.pdm.gomoku.services.http.media.Problem
import pt.isel.pdm.gomoku.services.http.utils.APIResult
import pt.isel.pdm.gomoku.services.http.utils.InvalidResponseException
import pt.isel.pdm.gomoku.services.http.utils.onFailure
import pt.isel.pdm.gomoku.storage.links.Links
import pt.isel.pdm.gomoku.storage.session.Session
import pt.isel.pdm.gomoku.storage.settings.Settings
import pt.isel.pdm.gomoku.ui.screens.auth.login.LoginActivity
import pt.isel.pdm.gomoku.ui.utils.navigateTo
import java.lang.ref.WeakReference
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

open class GomokuViewModel(
    val service: GomokuService,
    val links: Links,
    val session: Session,
    val settings: Settings,
    context: Context
) : ViewModel() {

    // prevents context object leak (allows to be garbage collected)
    private val contextRef: WeakReference<Context> = WeakReference(context)
    val context get() = contextRef.get() ?: error("Activity Context is null")

    var settingsLoaded by mutableStateOf(false)
        protected set

    var isLoggedIn by mutableStateOf(false)
        protected set

    var username by mutableStateOf<String?>(null)
        protected set

    var darkModeOn by mutableStateOf(true)
        protected set

    var soundOn by mutableStateOf(false)
        protected set

    var vibrationOn by mutableStateOf(false)
        protected set

    init {
        viewModelScope.launch {
            isLoggedIn = session.isLoggedIn()
            if (isLoggedIn) username = session.getUserName()
        }
        loadSettings()
    }

    inline fun <reified T> request(
        showError: Boolean = true,
        handler: () -> APIResult<T>
    ): APIResult<T> {
        return try {
            handler().onFailure { problem ->
                if (problem.title == "Unauthorized") {
                    onSessionExpired()
                } else if (showError) {
                    showToast(problem.detail ?: context.getString(R.string.something_went_wrong_msg))
                }
            }
        } catch (e: Exception) {
            val message = when (e) {
                is ConnectException,
                is SocketTimeoutException,
                is UnknownHostException,
                is InvalidResponseException -> context.getString(R.string.could_not_connect_to_server_msg)
                is CancellationException -> null
                else -> context.getString(R.string.something_went_wrong_msg)
            }
            if (message != null) showToast(message)
            logger.info("Handled exception: ${e.javaClass.simpleName} - ${e.message}")
            APIResult.failure(Problem(detail = e.message))
        }
    }

    fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    fun loadSettings() {
        viewModelScope.launch {
            soundOn = settings.isSoundOn()
            vibrationOn = settings.isVibrationOn()
            darkModeOn = settings.isDarkModeOn()
            settingsLoaded = true
        }
    }

    fun onSessionExpired() {
        showToast(context.getString(R.string.session_expired_msg))
        viewModelScope.launch { session.delete() }
        context.navigateTo<LoginActivity>()
    }
}
