package pt.isel.pdm.gomoku.ui.screens.game

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import pt.isel.pdm.gomoku.R
import pt.isel.pdm.gomoku.domain.game.Turn.Companion.toTurn
import pt.isel.pdm.gomoku.ui.GomokuActivity
import pt.isel.pdm.gomoku.ui.controllers.SoundController
import pt.isel.pdm.gomoku.ui.screens.Intents
import pt.isel.pdm.gomoku.ui.screens.home.HomeActivity
import pt.isel.pdm.gomoku.ui.screens.lobby.LobbyActivity
import pt.isel.pdm.gomoku.ui.theme.GomokuTheme
import pt.isel.pdm.gomoku.ui.utils.Idle
import pt.isel.pdm.gomoku.ui.utils.getOrNull
import pt.isel.pdm.gomoku.ui.utils.idle
import pt.isel.pdm.gomoku.ui.utils.navigateTo

class GameActivity : GomokuActivity() {
    override val viewModel: GameViewModel by getViewModel<GameViewModel>(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            viewModel.game.collectLatest { game ->
                if (game is Idle) {
                    val gameLink = intent.getStringExtra(Intents.GAME_LINK)
                    val playerColor = intent.getStringExtra(Intents.PLAYER_COLOR)?.toTurn()
                    viewModel.getGame(playerColor, gameLink)
                }
            }
        }
        setContent {
            val game by viewModel.game.collectAsState(idle())
            val gameValue = game.getOrNull()
            var prevGame by remember { mutableStateOf(gameValue) }
            if (gameValue != null && gameValue != prevGame) {
                val sound = if (gameValue.isOver) R.raw.whoosh_sound else R.raw.play_sound
                SoundController.play(this, sound)
                prevGame = gameValue
            }
            GomokuTheme(viewModel.darkModeOn) {
                Surface(Modifier.fillMaxSize()) {
                    GameScreen(
                        gameState = game,
                        isSpectating = viewModel.isSpectating,
                        validCoordinateSelected = viewModel.validCoordinateSelected,
                        playEnable = viewModel.playEnable,
                        leaveEnable = viewModel.leaveEnable,
                        onCoordinatePressed = viewModel::onCoordinatePressed,
                        onPlayRequest = viewModel::playGame,
                        onLeaveGameRequest = {
                            viewModel.leaveGame {
                                navigateTo<HomeActivity> { intent ->
                                    intent.flags = FLAG_CLEAR_BACK_STACK
                                }
                            }
                        },
                        onPlayAgainRequest = { navigateTo<LobbyActivity>() }
                    )
                }
            }
        }
    }
}
