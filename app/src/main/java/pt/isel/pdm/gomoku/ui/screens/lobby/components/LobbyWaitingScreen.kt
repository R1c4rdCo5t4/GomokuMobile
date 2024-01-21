package pt.isel.pdm.gomoku.ui.screens.lobby.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pt.isel.pdm.gomoku.R
import pt.isel.pdm.gomoku.ui.screens.TestTags.Lobby.lobbyCancelButtonTestTag
import pt.isel.pdm.gomoku.ui.screens.TestTags.Lobby.lobbyScreenTestTag
import pt.isel.pdm.gomoku.ui.utils.Idle
import pt.isel.pdm.gomoku.ui.utils.LoadState
import pt.isel.pdm.gomoku.ui.utils.Loaded
import pt.isel.pdm.gomoku.ui.utils.Loading
import pt.isel.pdm.gomoku.ui.utils.LoadingAnimatedIndicator
import pt.isel.pdm.gomoku.ui.utils.Spacer

@Composable
fun LobbyWaitingScreen(
    lobbyState: LoadState<Unit>,
    onLeaveLobbyRequest: () -> Unit = {},
    onRetryRequest: () -> Unit = {}
) {
    val matchmakingFailed = (lobbyState is Loaded && lobbyState.value.isFailure)
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .testTag(lobbyScreenTestTag)
    ) {
        Text(
            text = when (lobbyState) {
                is Idle -> stringResource(R.string.joining_lobby)
                is Loading -> stringResource(R.string.waiting_for_opponent)
                is Loaded ->
                    if (lobbyState.value.isSuccess) {
                        stringResource(R.string.match_found)
                    } else {
                        stringResource(R.string.matchmaking_failed)
                    }
            },
            fontSize = 28.sp,
            textAlign = TextAlign.Center
        )
        Spacer(50.dp)
        if (lobbyState !is Loaded) {
            LoadingAnimatedIndicator()
        }
        Spacer(80.dp)
        if (lobbyState is Loading || matchmakingFailed) {
            Button(
                onClick = if (lobbyState is Loading) onLeaveLobbyRequest else onRetryRequest,
                modifier = Modifier.testTag(lobbyCancelButtonTestTag)
            ) {
                Text(if (lobbyState is Loading) stringResource(R.string.cancel) else stringResource(R.string.retry))
            }
        }
    }
}
