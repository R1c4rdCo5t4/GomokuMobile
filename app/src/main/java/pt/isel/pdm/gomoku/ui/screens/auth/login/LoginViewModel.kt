package pt.isel.pdm.gomoku.ui.screens.auth.login

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
import pt.isel.pdm.gomoku.ui.screens.auth.isValidEmail
import pt.isel.pdm.gomoku.ui.screens.auth.isValidUsername

class LoginViewModel(
    service: GomokuService,
    links: Links,
    session: Session,
    settings: Settings,
    context: Context
) : GomokuViewModel(service, links, session, settings, context) {

    var loginEnable by mutableStateOf(true)
        private set

    fun login(
        nameOrEmail: String,
        password: String,
        onSuccess: () -> Unit
    ) {
        if (!areCredentialsValid(nameOrEmail, password)) return
        loginEnable = false
        val (name, email) = getNameOrEmail(nameOrEmail)
        viewModelScope.launch {
            request {
                service.usersService.login(links[Rels.LOGIN], name, email, password)
            }.onSuccess { (token, userHomeLink) ->
                request { service.usersService.getUserHome(userHomeLink, token) }
                val userLink = links[Rels.USER]
                val user = request { service.usersService.getUser(userLink) }.getOrThrow()
                session.save(token, user, userLink)
                isLoggedIn = true
                onSuccess()
            }.onFailure {
                loginEnable = true
            }
        }
    }

    private fun getNameOrEmail(nameOrEmail: String): Pair<String?, String?> {
        val name: String?
        val email: String?
        if (isValidEmail(nameOrEmail)) {
            name = null
            email = nameOrEmail
        } else {
            name = nameOrEmail
            email = null
        }
        return Pair(name, email)
    }

    private fun areCredentialsValid(
        nameOrEmail: String,
        password: String
    ): Boolean {
        fun showError(message: String): Boolean {
            showToast(message)
            return false
        }
        return when {
            nameOrEmail.isBlank() -> showError("Username or email cannot be empty")
            password.isBlank() -> showError("Password cannot be empty")
            !isValidEmail(nameOrEmail) && !isValidUsername(nameOrEmail) ->
                showError("Invalid username or email")
            else -> true
        }
    }
}
