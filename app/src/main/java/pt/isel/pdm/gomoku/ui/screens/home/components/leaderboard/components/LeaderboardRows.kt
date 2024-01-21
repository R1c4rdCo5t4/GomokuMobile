package pt.isel.pdm.gomoku.ui.screens.home.components.leaderboard.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import pt.isel.pdm.gomoku.domain.home.leaderboard.LeaderboardUser
import pt.isel.pdm.gomoku.ui.utils.Spacer

private val leaderboardRowColor = Color.Black.copy(alpha = 0.1f)

@Composable
fun LeaderboardRow(
    index: Int,
    user: LeaderboardUser,
    username: String?,
    onProfileRequest: (String) -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .clip(RoundedCornerShape(30))
            .background(if (user.name == username) colorScheme.inverseSurface.copy(alpha = 0.3f) else leaderboardRowColor)
            .clickable { onProfileRequest(user.link) }
            .padding(20.dp)
    ) {
        LeaderboardText("#${index + 1}", 0.2f)
        LeaderboardText(user.name, 0.6f, color = colorScheme.inverseOnSurface)
        LeaderboardText(user.stats.rating.toString(), 0.2f, color = colorScheme.inverseOnSurface)
    }
    Spacer(5.dp)
}
