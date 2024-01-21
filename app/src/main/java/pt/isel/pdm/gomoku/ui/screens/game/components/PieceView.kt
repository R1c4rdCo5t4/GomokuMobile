package pt.isel.pdm.gomoku.ui.screens.game.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.Dp
import pt.isel.pdm.gomoku.domain.game.Coordinate
import pt.isel.pdm.gomoku.domain.game.Piece
import pt.isel.pdm.gomoku.domain.game.Turn
import pt.isel.pdm.gomoku.ui.screens.TestTags.Game.gamePieceTestTag
import pt.isel.pdm.gomoku.ui.screens.game.OnCoordinatePressed
import pt.isel.pdm.gomoku.ui.utils.toRgb
import pt.isel.pdm.gomoku.ui.utils.toTransparentRgb

@Composable
fun PieceView(
    row: Int,
    col: Int,
    piece: Piece?,
    playerColor: Turn,
    pieceSize: Dp,
    onCoordinatePressed: OnCoordinatePressed
) {
    val selected = rememberSaveable { mutableStateOf(false) }
    Box(
        modifier = Modifier
            .size(pieceSize)
            .clip(RoundedCornerShape(50))
            .testTag("$gamePieceTestTag-$row-$col")
            .background(
                piece?.color?.toRgb()
                    ?: if (selected.value) playerColor.toTransparentRgb() else Color.Transparent
            )
            .clickable(enabled = !selected.value) {
                onCoordinatePressed(Coordinate(row, col), selected)
            }
    )
}
