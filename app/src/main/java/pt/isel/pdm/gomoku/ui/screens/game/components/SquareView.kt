package pt.isel.pdm.gomoku.ui.screens.game.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import pt.isel.pdm.gomoku.domain.game.Color
import pt.isel.pdm.gomoku.domain.game.Piece
import pt.isel.pdm.gomoku.ui.screens.game.OnCoordinatePressed
import pt.isel.pdm.gomoku.ui.utils.BottomLeftCorner
import pt.isel.pdm.gomoku.ui.utils.BottomRightCorner
import pt.isel.pdm.gomoku.ui.utils.Cross
import pt.isel.pdm.gomoku.ui.utils.HorizontalLine
import pt.isel.pdm.gomoku.ui.utils.TopLeftCorner
import pt.isel.pdm.gomoku.ui.utils.TopRightCorner
import pt.isel.pdm.gomoku.ui.utils.VerticalLine

val SQUARE_SIZE_19 = 18.dp
val SQUARE_SIZE_15 = 22.dp

@Composable
fun SquareView(
    row: Int,
    col: Int,
    boardSize: Int,
    piece: Piece?,
    playerColor: Color,
    onCoordinatePressed: OnCoordinatePressed
) {
    val squareSize = when (boardSize) {
        15 -> SQUARE_SIZE_15
        19 -> SQUARE_SIZE_19
        else -> throw IllegalArgumentException("Invalid board size")
    }
    Row(modifier = Modifier.size(squareSize)) {
        Box(contentAlignment = Alignment.Center) {
            when {
                row == 1 && col == 1 -> TopLeftCorner()
                row == 1 && col == boardSize -> TopRightCorner()
                row == boardSize && col == 1 -> BottomLeftCorner()
                row == boardSize && col == boardSize -> BottomRightCorner()
                row == 1 || row == boardSize -> HorizontalLine(row)
                col == 1 || col == boardSize -> VerticalLine(col)
                else -> Cross()
            }
            PieceView(row, col, piece, playerColor, squareSize, onCoordinatePressed)
        }
    }
}
