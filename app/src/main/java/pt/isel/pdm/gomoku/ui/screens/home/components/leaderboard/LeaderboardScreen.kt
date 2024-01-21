package pt.isel.pdm.gomoku.ui.screens.home.components.leaderboard

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pt.isel.pdm.gomoku.R
import pt.isel.pdm.gomoku.domain.home.leaderboard.Leaderboard
import pt.isel.pdm.gomoku.ui.screens.TestTags.Leaderboard.leaderBoardScreenTestTag
import pt.isel.pdm.gomoku.ui.screens.home.HomeViewModel.Companion.LEADERBOARD_MAX_USERS
import pt.isel.pdm.gomoku.ui.screens.home.components.leaderboard.components.LeaderboardHeader
import pt.isel.pdm.gomoku.ui.screens.home.components.leaderboard.components.LeaderboardRow
import pt.isel.pdm.gomoku.ui.utils.Spacer
import pt.isel.pdm.gomoku.ui.utils.TextButton

@Composable
fun LeaderboardScreen(
    leaderboard: Leaderboard,
    username: String?,
    onProfileRequest: (String) -> Unit = {},
    onLeaderboardLoadMore: () -> Unit = {}
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 30.dp, vertical = 20.dp)
            .testTag(leaderBoardScreenTestTag)
    ) {
        item {
            Text(
                text = stringResource(R.string.leaderboard),
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(20.dp)
                    .fillMaxWidth()
            )
            LeaderboardHeader()
            Spacer(10.dp)
        }
        items(leaderboard.users.size) { index ->
            val user = leaderboard.users[index]
            LeaderboardRow(index, user, username, onProfileRequest)
            Spacer(5.dp)
        }
        item {
            if ((leaderboard.users.size) % LEADERBOARD_MAX_USERS == 0) {
                TextButton(
                    text = "Load More",
                    onClick = onLeaderboardLoadMore,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
                )
            }
            Spacer(50.dp)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LeaderboardScreenPreview() {
    LeaderboardScreen(Leaderboard(emptyList()), "")
}
