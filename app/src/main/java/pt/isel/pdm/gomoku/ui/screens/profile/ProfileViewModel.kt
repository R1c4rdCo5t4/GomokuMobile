package pt.isel.pdm.gomoku.ui.screens.profile

import android.content.Context
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import pt.isel.pdm.gomoku.domain.game.Color
import pt.isel.pdm.gomoku.domain.game.Player
import pt.isel.pdm.gomoku.domain.user.profile.Profile
import pt.isel.pdm.gomoku.domain.user.profile.UserGame
import pt.isel.pdm.gomoku.services.GomokuService
import pt.isel.pdm.gomoku.services.http.utils.APIResult
import pt.isel.pdm.gomoku.services.http.utils.Rels
import pt.isel.pdm.gomoku.services.http.utils.map
import pt.isel.pdm.gomoku.services.http.utils.onFailure
import pt.isel.pdm.gomoku.services.http.utils.onSuccess
import pt.isel.pdm.gomoku.storage.links.Links
import pt.isel.pdm.gomoku.storage.session.Session
import pt.isel.pdm.gomoku.storage.settings.Settings
import pt.isel.pdm.gomoku.ui.GomokuViewModel
import pt.isel.pdm.gomoku.ui.utils.LoadState
import pt.isel.pdm.gomoku.ui.utils.idle
import pt.isel.pdm.gomoku.ui.utils.loaded
import pt.isel.pdm.gomoku.ui.utils.loading

class ProfileViewModel(
    service: GomokuService,
    links: Links,
    session: Session,
    settings: Settings,
    context: Context
) : GomokuViewModel(service, links, session, settings, context) {

    private val profileFlow = MutableStateFlow<LoadState<Profile>>(idle())

    val profile: Flow<LoadState<Profile>> get() = profileFlow.asStateFlow()

    fun getProfile(userLink: String) {
        profileFlow.value = loading()
        viewModelScope.launch {
            request {
                service.usersService.getUser(userLink)
            }.onSuccess { user ->
                getUserGames(user.name).onSuccess { games ->
                    profileFlow.value = loaded(APIResult.success(Profile(user, games)))
                }.onFailure { problem ->
                    profileFlow.value = loaded(APIResult.failure(problem))
                }
            }.onFailure { problem ->
                profileFlow.value = loaded(APIResult.failure(problem))
            }
        }
    }

    private suspend fun getUserGames(username: String) =
        request {
            service.gamesService.getGames(links[Rels.GAMES], username)
        }.map { games ->
            games.map {
                val state = it.state ?: error("Game state is null")
                val opponentColor = it.opponent ?: error("Opponent is null")
                val opponent = (if (opponentColor == Color.BLACK) it.black else it.white) ?: error("Opponent is null")
                val gameLink = it.link ?: error("Game link is null")
                UserGame(state, Player(opponent, opponentColor), gameLink)
            }
        }
}
