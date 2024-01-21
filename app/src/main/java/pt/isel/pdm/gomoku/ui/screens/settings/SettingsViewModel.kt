package pt.isel.pdm.gomoku.ui.screens.settings

import android.content.Context
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import pt.isel.pdm.gomoku.services.GomokuService
import pt.isel.pdm.gomoku.services.http.utils.Rels
import pt.isel.pdm.gomoku.storage.links.Links
import pt.isel.pdm.gomoku.storage.session.Session
import pt.isel.pdm.gomoku.storage.settings.Settings
import pt.isel.pdm.gomoku.ui.GomokuViewModel

class SettingsViewModel(
    service: GomokuService,
    links: Links,
    session: Session,
    settings: Settings,
    context: Context
) : GomokuViewModel(service, links, session, settings, context) {

    fun toggleSound() {
        soundOn = !soundOn
        viewModelScope.launch {
            settings.setSound(soundOn)
        }
    }

    fun toggleVibration() {
        vibrationOn = !vibrationOn
        viewModelScope.launch {
            settings.setVibration(vibrationOn)
        }
    }

    fun toggleTheme() {
        darkModeOn = !darkModeOn
        viewModelScope.launch {
            settings.setDarkMode(darkModeOn)
        }
    }

    fun logout(onSuccess: () -> Unit) {
        viewModelScope.launch {
            request { service.usersService.logout(links[Rels.LOGOUT], session.getToken()) }
            session.delete()
            isLoggedIn = false
            onSuccess()
        }
    }
}
