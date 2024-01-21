package pt.isel.pdm.gomoku.ui.screens.profile.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pt.isel.pdm.gomoku.R
import pt.isel.pdm.gomoku.domain.user.profile.Profile
import pt.isel.pdm.gomoku.ui.screens.TestTags
import pt.isel.pdm.gomoku.ui.utils.Spacer

@Composable
fun ProfileView(
    profile: Profile,
    onGameClick: (String) -> Unit = {}
) {
    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(start = 20.dp, end = 20.dp, top = 20.dp)
            .testTag(TestTags.Profile.profileScreenTestTag)
    ) {
        item {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxHeight(0.5f)
                    .border(1.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(5))
                    .padding(20.dp)
            ) {
                UserView(profile.user)
            }
        }
        item {
            Spacer(35.dp)
            Text(
                text = if (profile.games.isNotEmpty()) {
                    stringResource(R.string.games_played)
                } else {
                    stringResource(R.string.no_games_played)
                },
                fontSize = 18.sp
            )
            Spacer(20.dp)
        }
        items(profile.games.size) { index ->
            val game = profile.games[index]
            UserGameView(game, onGameClick)
        }
        item {
            Spacer(20.dp)
        }
    }
}
