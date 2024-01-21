package pt.isel.pdm.gomoku.ui.screens.lobby

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import pt.isel.pdm.gomoku.domain.game.GameConfig
import pt.isel.pdm.gomoku.ui.screens.lobby.components.GameConfigScreen
import pt.isel.pdm.gomoku.ui.screens.lobby.components.LobbyWaitingScreen
import pt.isel.pdm.gomoku.ui.utils.Idle
import pt.isel.pdm.gomoku.ui.utils.LoadState
import pt.isel.pdm.gomoku.ui.utils.Loading
import pt.isel.pdm.gomoku.ui.utils.idle

@Composable
fun LobbyScreen(
    lobbyState: LoadState<Unit>,
    onJoinLobbyRequest: (GameConfig) -> Unit = { _ -> },
    onLeaveLobbyRequest: () -> Unit = {},
    onRetryRequest: () -> Unit = {}
) {
    BackHandler(
        enabled = lobbyState is Loading,
        onBack = onLeaveLobbyRequest
    )
    if (lobbyState is Idle) {
        GameConfigScreen(
            lobbyState = lobbyState,
            onJoinLobbyRequest = onJoinLobbyRequest
        )
    } else {
        LobbyWaitingScreen(
            lobbyState = lobbyState,
            onLeaveLobbyRequest = onLeaveLobbyRequest,
            onRetryRequest = onRetryRequest
        )
    }
}

@Preview(showBackground = true)
@Composable
fun LobbyScreenPreview() {
    LobbyScreen(idle())
}
