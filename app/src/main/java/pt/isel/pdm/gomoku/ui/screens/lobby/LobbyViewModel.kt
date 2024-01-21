package pt.isel.pdm.gomoku.ui.screens.lobby

import android.content.Context
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import pt.isel.pdm.gomoku.domain.game.GameConfig
import pt.isel.pdm.gomoku.services.GomokuService
import pt.isel.pdm.gomoku.services.http.utils.Rels
import pt.isel.pdm.gomoku.services.http.utils.onFailure
import pt.isel.pdm.gomoku.services.http.utils.onSuccess
import pt.isel.pdm.gomoku.storage.links.Links
import pt.isel.pdm.gomoku.storage.session.Session
import pt.isel.pdm.gomoku.storage.settings.Settings
import pt.isel.pdm.gomoku.ui.GomokuViewModel
import pt.isel.pdm.gomoku.ui.utils.LoadState
import pt.isel.pdm.gomoku.ui.utils.Loading
import pt.isel.pdm.gomoku.ui.utils.failure
import pt.isel.pdm.gomoku.ui.utils.idle
import pt.isel.pdm.gomoku.ui.utils.loading
import pt.isel.pdm.gomoku.ui.utils.success

class LobbyViewModel(
    service: GomokuService,
    links: Links,
    session: Session,
    settings: Settings,
    context: Context
) : GomokuViewModel(service, links, session, settings, context) {

    private val lobbyStateFlow = MutableStateFlow<LoadState<Unit>>(idle())

    val lobbyState: Flow<LoadState<Unit>> get() = lobbyStateFlow.asStateFlow()
    private var savedGameConfig: GameConfig? = null
    private var inLobby = false

    fun joinLobby(
        gameConfig: GameConfig? = null,
        onJoinGame: (String) -> Unit = { _ -> }
    ) {
        lobbyStateFlow.value = loading()
        viewModelScope.launch {
            request {
                gameConfig?.let { savedGameConfig = it }
                val config = savedGameConfig
                requireNotNull(config) { "Game config must be provided" }
                service.lobbyService.join(
                    link = links[Rels.JOIN_LOBBY],
                    boardSize = config.boardSize,
                    variant = config.variant,
                    opening = config.opening,
                    token = session.getToken()
                )
            }.onSuccess {
                inLobby = true
                findMatchPolling(onJoinGame)
            }.onFailure {
                lobbyStateFlow.value = failure(it)
            }
        }
    }

    fun reconnectIfInGame(onJoinGame: (String) -> Unit) {
        viewModelScope.launch {
            findMatch(onJoinGame, reconnecting = true)
        }
    }

    /**
     * Tries to find a match
     * @param onJoinGame callback to be called if a match is found
     * @return true if a match was found, false otherwise
     */
    private suspend fun findMatch(
        onJoinGame: (String) -> Unit,
        reconnecting: Boolean = false
    ): Boolean {
        request(showError = !reconnecting) {
            service.lobbyService.findMatch(links[Rels.FIND_MATCH], session.getToken())
        }.onSuccess { playerColor ->
            if (playerColor != null) {
                onJoinGame(playerColor)
                return true
            }
        }
        return false
    }

    /**
     * Polls the lobby until a match is found
     * @param onJoinGame callback to be called when a match is found
     */
    private fun findMatchPolling(onJoinGame: (String) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            while (inLobby) {
                val joined = findMatch(onJoinGame)
                if (joined) {
                    inLobby = false
                    lobbyStateFlow.value = success(Unit)
                    break
                }
                delay(LOBBY_POLLING_DELAY)
            }
        }
    }

    fun leaveLobby() {
        if (lobbyStateFlow.value !is Loading && inLobby) return
        lobbyStateFlow.value = idle()
        viewModelScope.launch {
            request {
                service.lobbyService.leave(links[Rels.LEAVE_LOBBY], session.getToken())
            }.onSuccess {
                inLobby = false
            }
        }
    }

    companion object {
        const val LOBBY_POLLING_DELAY = 2000L
    }
}
