package pt.isel.pdm.gomoku.ui.screens.game

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import pt.isel.pdm.gomoku.domain.game.Board
import pt.isel.pdm.gomoku.domain.game.Coordinate
import pt.isel.pdm.gomoku.domain.game.Game
import pt.isel.pdm.gomoku.domain.game.GameState
import pt.isel.pdm.gomoku.domain.game.Player
import pt.isel.pdm.gomoku.domain.game.Turn
import pt.isel.pdm.gomoku.domain.user.Stats
import pt.isel.pdm.gomoku.domain.user.User
import pt.isel.pdm.gomoku.services.http.utils.APIResult
import pt.isel.pdm.gomoku.ui.screens.game.components.GameView
import pt.isel.pdm.gomoku.ui.utils.LoadState
import pt.isel.pdm.gomoku.ui.utils.LoadStateRenderer
import pt.isel.pdm.gomoku.ui.utils.loaded
import pt.isel.pdm.gomoku.ui.utils.rememberSaveablePopupState

typealias OnCoordinatePressed = (coord: Coordinate, selected: MutableState<Boolean>) -> Unit

@Composable
fun GameScreen(
    gameState: LoadState<Game>,
    isSpectating: Boolean,
    validCoordinateSelected: Boolean,
    playEnable: Boolean = true,
    leaveEnable: Boolean = true,
    onCoordinatePressed: OnCoordinatePressed = { _, _ -> },
    onPlayRequest: () -> Unit = {},
    onLeaveGameRequest: () -> Unit = {},
    onPlayAgainRequest: () -> Unit = {}
) {
    val leaveGamePopupState = rememberSaveablePopupState()
    val endGamePopupState = rememberSaveablePopupState()
    var gameEnded by rememberSaveable { mutableStateOf(false) }
    BackHandler(
        enabled = !isSpectating,
        onBack = { leaveGamePopupState.show() }
    )
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        LoadStateRenderer(gameState) { game ->
            if (game.isOver && !gameEnded) {
                endGamePopupState.show()
                gameEnded = true
            }
            GameView(
                game = game,
                isSpectating = isSpectating,
                validCoordinateSelected = validCoordinateSelected,
                onCoordinatePressed = onCoordinatePressed,
                playEnable = playEnable,
                leaveEnable = leaveEnable,
                onPlayRequest = onPlayRequest,
                onLeaveGameRequest = onLeaveGameRequest,
                onPlayAgainRequest = onPlayAgainRequest,
                leaveGamePopupState = leaveGamePopupState,
                endGamePopupState = endGamePopupState
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GameScreenPreview() {
    GameScreen(
        gameState = loaded(
            APIResult.success(
                Game(
                    board = Board(15, emptyList()),
                    me = Player(
                        User(
                            "test",
                            "test@gmail.com",
                            Stats(0, 0, 0, 0, 0)
                        ),
                        Turn.BLACK
                    ),
                    opponent = Player(
                        User(
                            "test2",
                            "test2@gmail.com",
                            Stats(0, 0, 0, 0, 0)
                        ),
                        Turn.WHITE
                    ),
                    turn = Turn.BLACK,
                    state = GameState.RUNNING
                )
            )
        ),
        isSpectating = false,
        validCoordinateSelected = true
    )
}
