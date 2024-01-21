package pt.isel.pdm.gomoku.ui.screens.profile.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pt.isel.pdm.gomoku.R
import pt.isel.pdm.gomoku.domain.user.Stats
import pt.isel.pdm.gomoku.domain.user.User
import pt.isel.pdm.gomoku.ui.utils.Spacer
import pt.isel.pdm.gomoku.ui.utils.UserIconView

@Composable
fun UserView(user: User) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier.fillMaxWidth(0.9f)
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            UserIconView(user.name.first().uppercaseChar())
            Spacer(10.dp)
            UserInfoView(user.name, user.email)
        }
        Spacer(20.dp)
        Row(
            horizontalArrangement = Arrangement.Center
        ) {
            UserStatsView(user.stats)
        }
    }
}

@Composable
fun UserInfoView(name: String, email: String) {
    Column {
        Text(name, fontSize = 20.sp)
        Text(email, fontSize = 16.sp)
    }
}

@Composable
fun UserStatsView(stats: Stats) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.5f),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth(0.8f)
        ) {
            UserStatView(stringResource(R.string.rating), stats.rating)
            UserStatView(stringResource(R.string.games), stats.gamesPlayed)
        }
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth(0.8f)
        ) {
            UserStatView(stringResource(R.string.wins), stats.wins)
            UserStatView(stringResource(R.string.draws), stats.draws)
            UserStatView(stringResource(R.string.losses), stats.losses)
        }
    }
}

@Composable
fun UserStatView(name: String, value: Int) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = name,
            color = MaterialTheme.colorScheme.primary,
            fontSize = 20.sp
        )
        Text(
            text = value.toString(),
            fontSize = 18.sp
        )
    }
}
