package pt.isel.pdm.gomoku.ui.screens.game.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import pt.isel.pdm.gomoku.R
import pt.isel.pdm.gomoku.domain.game.Game
import pt.isel.pdm.gomoku.ui.screens.TestTags.Game.gameBoardTestTag
import pt.isel.pdm.gomoku.ui.screens.TestTags.Game.gamePlayButtonTestTag
import pt.isel.pdm.gomoku.ui.screens.TestTags.Game.gameScreenTestTag
import pt.isel.pdm.gomoku.ui.screens.game.OnCoordinatePressed
import pt.isel.pdm.gomoku.ui.utils.IconButton
import pt.isel.pdm.gomoku.ui.utils.PopupState
import pt.isel.pdm.gomoku.ui.utils.Spacer

@Composable
fun GameView(
    game: Game,
    isSpectating: Boolean,
    validCoordinateSelected: Boolean,
    onCoordinatePressed: OnCoordinatePressed,
    playEnable: Boolean = true,
    leaveEnable: Boolean = true,
    onPlayRequest: () -> Unit = {},
    onLeaveGameRequest: () -> Unit = {},
    onPlayAgainRequest: () -> Unit = {},
    leaveGamePopupState: PopupState,
    endGamePopupState: PopupState
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .testTag(gameScreenTestTag)
    ) {
        Row(verticalAlignment = Alignment.Top) {
            PlayerView(game.opponent)
        }
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxHeight(0.7f)
                .testTag(gameBoardTestTag)
        ) {
            BoardView(
                board = game.board,
                playerColor = game.me.color,
                onCoordinatePressed = onCoordinatePressed
            )
        }
        if (!isSpectating) {
            IconButton(
                onClick = onPlayRequest,
                icon = Icons.Filled.Check,
                contentDescription = stringResource(R.string.play_button_icon),
                enabled = validCoordinateSelected && game.isMyTurn && playEnable,
                modifier = Modifier.testTag(gamePlayButtonTestTag)
            )
        }
        Spacer(10.dp)
        Row(
            modifier = Modifier.fillMaxHeight(),
            verticalAlignment = Alignment.Bottom
        ) {
            PlayerView(game.me)
        }
        if (leaveGamePopupState.isVisible) {
            LeaveGamePopup(
                leaveEnable = leaveEnable,
                onClose = leaveGamePopupState::hide,
                onLeaveGame = onLeaveGameRequest
            )
        }
        if (endGamePopupState.isVisible) {
            EndGamePopup(
                game = game,
                isSpectating = isSpectating,
                onClose = endGamePopupState::hide,
                onLeaveGame = onLeaveGameRequest,
                onPlayAgain = onPlayAgainRequest
            )
        }
    }
}
