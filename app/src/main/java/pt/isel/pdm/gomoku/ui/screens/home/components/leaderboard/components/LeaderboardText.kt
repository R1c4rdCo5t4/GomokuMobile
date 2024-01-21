package pt.isel.pdm.gomoku.ui.screens.home.components.leaderboard.components

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp

@Composable
fun RowScope.LeaderboardText(
    text: String,
    weight: Float = 1f,
    color: Color = MaterialTheme.colorScheme.primary
) {
    Text(
        text = text,
        fontSize = 15.sp,
        color = color,
        modifier = Modifier.weight(weight)
    )
}
