package pt.isel.pdm.gomoku.ui.screens.game.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import pt.isel.pdm.gomoku.R
import pt.isel.pdm.gomoku.domain.game.Board
import pt.isel.pdm.gomoku.domain.game.Color
import pt.isel.pdm.gomoku.domain.game.Game
import pt.isel.pdm.gomoku.domain.game.GameState
import pt.isel.pdm.gomoku.domain.game.Player
import pt.isel.pdm.gomoku.domain.user.Stats
import pt.isel.pdm.gomoku.domain.user.User
import pt.isel.pdm.gomoku.ui.utils.Popup
import pt.isel.pdm.gomoku.ui.utils.TextButton

@Composable
fun EndGamePopup(
    game: Game,
    isSpectating: Boolean,
    onClose: () -> Unit,
    onLeaveGame: () -> Unit,
    onPlayAgain: () -> Unit
) {
    val reason = when (game.state) {
        GameState.DRAW -> "It's a draw!"
        else -> {
            val winner =
                if (game.state.getWinner() == game.me.color) {
                    game.me.name
                } else {
                    game.opponent.name
                }
            "$winner wins!"
        }
    }
    Popup(
        title = reason,
        onClose = onClose
    ) {
        if (!isSpectating) {
            TextButton(
                onClick = onPlayAgain,
                text = stringResource(R.string.play_again),
                fontSize = 15.sp
            )
            TextButton(
                onClick = onLeaveGame,
                text = stringResource(R.string.leave_game),
                fontSize = 15.sp
            )
        } else {
            TextButton(
                onClick = onLeaveGame,
                text = stringResource(R.string.back_to_home),
                fontSize = 15.sp
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun EndGamePopupPreview() {
    EndGamePopup(
        game = Game(
            board = Board(19),
            me = Player(User("me", "me@email.com", Stats(600, 0, 0, 0, 0)), Color.BLACK),
            opponent = Player(User("you", "you@email.com", Stats(600, 0, 0, 0, 0)), Color.WHITE),
            turn = Color.BLACK,
            state = GameState.BLACK_WON
        ),
        isSpectating = false,
        onClose = {},
        onLeaveGame = {},
        onPlayAgain = {}
    )
}
