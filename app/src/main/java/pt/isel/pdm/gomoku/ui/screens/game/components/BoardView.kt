package pt.isel.pdm.gomoku.ui.screens.game.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import pt.isel.pdm.gomoku.domain.game.Board
import pt.isel.pdm.gomoku.domain.game.Color
import pt.isel.pdm.gomoku.ui.screens.game.OnCoordinatePressed

@Composable
fun BoardView(
    board: Board,
    playerColor: Color,
    onCoordinatePressed: OnCoordinatePressed
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.background(colorScheme.surface)
    ) {
        Row {
            (1..board.size).map { col ->
                Column {
                    (1..board.size).map { row ->
                        SquareView(
                            row = row,
                            col = col,
                            boardSize = board.size,
                            piece = board.getPieceAt(row, col),
                            playerColor = playerColor,
                            onCoordinatePressed = onCoordinatePressed
                        )
                    }
                }
            }
        }
    }
}
