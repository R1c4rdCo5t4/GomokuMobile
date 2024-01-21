package pt.isel.pdm.gomoku.ui.screens.home.components.leaderboard.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

private val leaderboardTitleColor = Color.Black.copy(alpha = 0.2f)

@Composable
fun LeaderboardHeader() {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .clip(RoundedCornerShape(30))
            .background(leaderboardTitleColor)
            .padding(20.dp)
    ) {
        LeaderboardText("Rank", 0.25f)
        LeaderboardText("Name", 0.5f)
        LeaderboardText("Rating", 0.3f)
    }
}
