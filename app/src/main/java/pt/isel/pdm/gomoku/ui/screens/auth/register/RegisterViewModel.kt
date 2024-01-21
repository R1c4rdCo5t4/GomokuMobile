package pt.isel.pdm.gomoku.ui.screens.auth.register

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import pt.isel.pdm.gomoku.services.GomokuService
import pt.isel.pdm.gomoku.services.http.utils.Rels
import pt.isel.pdm.gomoku.services.http.utils.onFailure
import pt.isel.pdm.gomoku.services.http.utils.onSuccess
import pt.isel.pdm.gomoku.storage.links.Links
import pt.isel.pdm.gomoku.storage.session.Session
import pt.isel.pdm.gomoku.storage.settings.Settings
import pt.isel.pdm.gomoku.ui.GomokuViewModel
import pt.isel.pdm.gomoku.ui.screens.auth.MAX_EMAIL_LEN
import pt.isel.pdm.gomoku.ui.screens.auth.MAX_PASSWORD_LEN
import pt.isel.pdm.gomoku.ui.screens.auth.MIN_EMAIL_LEN
import pt.isel.pdm.gomoku.ui.screens.auth.MIN_PASSWORD_LEN
import pt.isel.pdm.gomoku.ui.screens.auth.isValidEmail
import pt.isel.pdm.gomoku.ui.screens.auth.isValidEmailLength
import pt.isel.pdm.gomoku.ui.screens.auth.isValidPassword
import pt.isel.pdm.gomoku.ui.screens.auth.isValidPasswordLength
import pt.isel.pdm.gomoku.ui.screens.auth.isValidUsername
import pt.isel.pdm.gomoku.ui.screens.auth.isValidUsernameLength

class RegisterViewModel(
    service: GomokuService,
    links: Links,
    session: Session,
    settings: Settings,
    context: Context
) : GomokuViewModel(service, links, session, settings, context) {

    var registerEnable by mutableStateOf(true)
        private set

    fun register(
        name: String,
        email: String,
        password: String,
        confirmPassword: String,
        onSuccess: () -> Unit
    ) {
        if (!areCredentialsValid(name, email, password, confirmPassword)) return
        registerEnable = false
        viewModelScope.launch {
            request {
                service.usersService.register(links[Rels.REGISTER], name, email, password)
            }.onSuccess {
                onSuccess()
            }.onFailure {
                registerEnable = true
            }
        }
    }

    private fun areCredentialsValid(
        name: String,
        email: String,
        password: String,
        confirmPassword: String
    ): Boolean {
        fun showError(message: String): Boolean {
            showToast(message)
            return false
        }
        return when {
            !isValidUsernameLength(name) ->
                showError("Username should be between $MIN_EMAIL_LEN and $MAX_EMAIL_LEN characters")

            !isValidUsername(name) ->
                showError("Invalid username")

            !isValidEmailLength(email) ->
                showError("E-mail should be between $MIN_EMAIL_LEN and $MAX_EMAIL_LEN characters")

            !isValidEmail(email) ->
                showError("Invalid e-mail")

            !isValidPasswordLength(password) ->
                showError("Password should be between $MIN_PASSWORD_LEN and $MAX_PASSWORD_LEN characters")

            !isValidPassword(password) ->
                showError("Invalid password")

            password != confirmPassword ->
                showError("Passwords don't match")

            else -> true
        }
    }
}
