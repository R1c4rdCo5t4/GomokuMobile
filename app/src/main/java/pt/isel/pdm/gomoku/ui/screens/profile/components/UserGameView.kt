package pt.isel.pdm.gomoku.ui.screens.profile.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import pt.isel.pdm.gomoku.domain.game.GameState
import pt.isel.pdm.gomoku.domain.user.profile.UserGame

val winColor = Color(34, 177, 76)
val lossColor = Color(237, 62, 62)
val drawColor = Color(150, 150, 150)
val onGoingColor = Color(250, 190, 0)

@Composable
fun UserGameView(
    game: UserGame,
    onClick: (String) -> Unit = {}
) {
    val color = when (game.state) {
        GameState.RUNNING -> onGoingColor
        GameState.DRAW -> drawColor
        else -> if (game.state.getWinner() == game.opponent.color) lossColor else winColor
    }
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 35.dp, vertical = 5.dp)
            .clip(RoundedCornerShape(50))
            .background(color)
            .clickable { onClick(game.gameLink) }
    ) {
        Text(
            text = "${game.opponent.name} (${game.opponent.stats.rating})",
            modifier = Modifier.padding(5.dp)
        )
    }
}
