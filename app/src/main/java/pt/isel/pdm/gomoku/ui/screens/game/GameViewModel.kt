package pt.isel.pdm.gomoku.ui.screens.game

import android.content.Context
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import pt.isel.pdm.gomoku.domain.game.Color
import pt.isel.pdm.gomoku.domain.game.Coordinate
import pt.isel.pdm.gomoku.domain.game.Game
import pt.isel.pdm.gomoku.domain.game.Player
import pt.isel.pdm.gomoku.domain.user.User
import pt.isel.pdm.gomoku.services.GomokuService
import pt.isel.pdm.gomoku.services.http.utils.Rels
import pt.isel.pdm.gomoku.services.http.utils.map
import pt.isel.pdm.gomoku.services.http.utils.onSuccess
import pt.isel.pdm.gomoku.storage.links.Links
import pt.isel.pdm.gomoku.storage.session.Session
import pt.isel.pdm.gomoku.storage.settings.Settings
import pt.isel.pdm.gomoku.ui.GomokuViewModel
import pt.isel.pdm.gomoku.ui.utils.LoadState
import pt.isel.pdm.gomoku.ui.utils.getOrThrow
import pt.isel.pdm.gomoku.ui.utils.idle
import pt.isel.pdm.gomoku.ui.utils.loaded
import pt.isel.pdm.gomoku.ui.utils.loading

class GameViewModel(
    service: GomokuService,
    links: Links,
    session: Session,
    settings: Settings,
    context: Context
) : GomokuViewModel(service, links, session, settings, context) {

    private val loadedGame get() = gameFlow.value.getOrThrow()
    private var lastClickedSquare: MutableState<Boolean>? = null
    private var lastClickedCoordinate: Coordinate? = null
    private val gameFlow = MutableStateFlow<LoadState<Game>>(idle())

    val game: Flow<LoadState<Game>> get() = gameFlow.asStateFlow()

    var validCoordinateSelected by mutableStateOf(false)
        private set

    var isSpectating = false
        private set

    var playEnable by mutableStateOf(true)
        private set

    var leaveEnable by mutableStateOf(true)
        private set

    fun getGame(playerColor: Color?, gameLink: String?) {
        gameFlow.value = loading()
        viewModelScope.launch(Dispatchers.IO) {
            if (playerColor != null) {
                val opponent = getOpponent()
                gamePolling(playerColor, session.getUser(), opponent, links[Rels.GAME])
            } else {
                isSpectating = true
                requireNotNull(gameLink) { "Game link cannot be null when spectating" }
                val (black, white) = getGamePlayers(gameLink)
                gamePolling(Color.BLACK, black, white, gameLink)
            }
        }
    }

    fun playGame() {
        check(!isSpectating) { "Cannot play game while spectating" }
        val (row, col) = lastClickedCoordinate ?: return
        playEnable = false
        validCoordinateSelected = false
        viewModelScope.launch {
            request {
                service.gamesService.play(links[Rels.PLAY_GAME], row, col, session.getToken())
            }.onSuccess {
                lastClickedSquare = null
            }
            playEnable = true
        }
    }

    fun leaveGame(onSuccess: () -> Unit) {
        if (loadedGame.isOver || isSpectating) return onSuccess()
        leaveEnable = false
        viewModelScope.launch {
            request {
                service.gamesService.leave(links[Rels.LEAVE_GAME], session.getToken())
            }.onSuccess { onSuccess() }
            leaveEnable = true
        }
    }

    /**
     * Polls the game until it is over.
     * The poll does not stop when it's the player's turn because the other player can forfeit
     */
    private suspend fun gamePolling(color: Color, me: User, opponent: User, gameLink: String) {
        do {
            loadGame(color, me, opponent, gameLink)
            delay(GAME_POLLING_DELAY)
        } while (loadedGame.isRunning)
        if (!isSpectating) updateUserStats()
    }

    private suspend fun loadGame(color: Color, me: User, opponent: User, gameLink: String) {
        val result = request {
            service.gamesService.getGame(gameLink)
        }.map {
            Game(it.board, Player(me, color), Player(opponent, color.other()), it.turn, it.state)
        }
        gameFlow.value = loaded(result)
    }

    private suspend fun getOpponent(): User =
        request { service.usersService.getUser(links[Rels.OPPONENT]) }.getOrThrow()

    private suspend fun updateUserStats() {
        request {
            service.usersService.getUser(session.getUserLink())
        }.onSuccess {
            session.updateUserStats(it.stats)
        }
    }

    fun onCoordinatePressed(coord: Coordinate, selected: MutableState<Boolean>) {
        if (loadedGame.isOver || isSpectating) return
        selected.value = true
        lastClickedSquare?.value = false
        lastClickedSquare = selected
        lastClickedCoordinate = coord
        validCoordinateSelected = loadedGame.board.getPieceAt(coord) == null
    }

    private suspend fun getGamePlayers(gameLink: String): Pair<User, User> {
        request { service.gamesService.getGame(gameLink) }
        val black = request { service.usersService.getUser(links[Rels.BLACK_PLAYER]) }.getOrThrow()
        val white = request { service.usersService.getUser(links[Rels.WHITE_PLAYER]) }.getOrThrow()
        return Pair(black, white)
    }

    companion object {
        const val GAME_POLLING_DELAY = 1000L
    }
}
