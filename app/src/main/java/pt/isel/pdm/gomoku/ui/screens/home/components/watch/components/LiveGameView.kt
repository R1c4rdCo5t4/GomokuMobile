package pt.isel.pdm.gomoku.ui.screens.home.components.watch.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import pt.isel.pdm.gomoku.domain.home.watch.LiveGame
import pt.isel.pdm.gomoku.ui.utils.AutoResizedText

@Composable
fun LiveGameView(
    game: LiveGame,
    modifier: Modifier = Modifier,
    onWatchGameRequest: (String) -> Unit = {}
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(30))
            .background(MaterialTheme.colorScheme.primary)
            .clickable { onWatchGameRequest(game.gameLink) }
            .padding(10.dp)
    ) {
        val black = "${game.black.name} (${game.black.stats.rating})"
        val white = "${game.white.name} (${game.white.stats.rating})"
        AutoResizedText(
            text = "$black  vs  $white",
            color = MaterialTheme.colorScheme.onPrimary
        )
    }
}
