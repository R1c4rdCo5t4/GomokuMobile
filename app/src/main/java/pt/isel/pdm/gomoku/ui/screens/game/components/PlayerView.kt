package pt.isel.pdm.gomoku.ui.screens.game.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import pt.isel.pdm.gomoku.domain.game.Player
import pt.isel.pdm.gomoku.ui.utils.AutoResizedText
import pt.isel.pdm.gomoku.ui.utils.Spacer
import pt.isel.pdm.gomoku.ui.utils.UserIconView
import pt.isel.pdm.gomoku.ui.utils.toRgb

@Composable
fun PlayerView(player: Player) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.tertiary)
            .padding(20.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Box(contentAlignment = Alignment.Center) {
                    UserIconView(
                        letter = player.name.first().uppercaseChar(),
                        size = 50.dp
                    )
                }
            }
            Spacer(20.dp)
            Column {
                AutoResizedText(
                    text = "${player.name} (${player.stats.rating})",
                    color = MaterialTheme.colorScheme.primary
                )
                Text(player.stats.toString())
            }
            Spacer(10.dp)
        }
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(50))
                .background(player.color.toRgb())
                .size(20.dp)
        )
    }
}
