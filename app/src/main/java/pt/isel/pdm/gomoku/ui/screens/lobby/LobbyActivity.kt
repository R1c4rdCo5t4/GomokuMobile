package pt.isel.pdm.gomoku.ui.screens.lobby

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import pt.isel.pdm.gomoku.domain.game.GameConfig
import pt.isel.pdm.gomoku.ui.GomokuActivity
import pt.isel.pdm.gomoku.ui.controllers.VibrationController
import pt.isel.pdm.gomoku.ui.screens.Intents
import pt.isel.pdm.gomoku.ui.screens.game.GameActivity
import pt.isel.pdm.gomoku.ui.theme.GomokuTheme
import pt.isel.pdm.gomoku.ui.utils.idle
import pt.isel.pdm.gomoku.ui.utils.navigateTo

class LobbyActivity : GomokuActivity() {
    override val viewModel: LobbyViewModel by getViewModel<LobbyViewModel>(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        VibrationController.init(this)
        viewModel.reconnectIfInGame(::joinGame)
        lifecycleScope.launch {
            viewModel.lobbyState.collectLatest {
                viewModel.lobbyState
            }
        }
        setContent {
            val lobbyState by viewModel.lobbyState.collectAsState(idle())
            GomokuTheme(viewModel.darkModeOn) {
                Surface(Modifier.fillMaxSize()) {
                    LobbyScreen(
                        lobbyState = lobbyState,
                        onJoinLobbyRequest = ::joinLobby,
                        onRetryRequest = ::joinLobby,
                        onLeaveLobbyRequest = viewModel::leaveLobby
                    )
                }
            }
        }
    }

    private fun joinLobby(gameConfig: GameConfig? = null) {
        viewModel.joinLobby(
            gameConfig = gameConfig,
            onJoinGame = ::joinGame
        )
    }

    private fun joinGame(playerColor: String) {
        VibrationController.vibrate(500)
        navigateTo<GameActivity> { intent ->
            intent.putExtra(Intents.PLAYER_COLOR, playerColor)
        }
    }
}
