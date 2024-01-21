package pt.isel.pdm.gomoku.ui.screens.home

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Leaderboard
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import pt.isel.pdm.gomoku.R
import pt.isel.pdm.gomoku.domain.home.about.Home
import pt.isel.pdm.gomoku.domain.home.leaderboard.Leaderboard
import pt.isel.pdm.gomoku.domain.home.watch.WatchList
import pt.isel.pdm.gomoku.ui.screens.auth.AuthScreen
import pt.isel.pdm.gomoku.ui.screens.home.components.HomeHorizontalPager
import pt.isel.pdm.gomoku.ui.screens.home.components.MainScreen
import pt.isel.pdm.gomoku.ui.screens.home.components.about.AboutScreen
import pt.isel.pdm.gomoku.ui.screens.home.components.leaderboard.LeaderboardScreen
import pt.isel.pdm.gomoku.ui.screens.home.components.watch.WatchLiveScreen
import pt.isel.pdm.gomoku.ui.utils.LoadState
import pt.isel.pdm.gomoku.ui.utils.LoadStateRenderer
import pt.isel.pdm.gomoku.ui.utils.idle

@Composable
fun HomeScreen(
    home: LoadState<Home> = idle(),
    leaderboard: LoadState<Leaderboard> = idle(),
    liveGames: LoadState<WatchList> = idle(),
    isLoggedIn: Boolean = false,
    username: String? = null,
    onPlayGameRequest: () -> Unit = {},
    onSettingsRequest: () -> Unit = {},
    onExitRequest: () -> Unit = {},
    onMyProfileRequest: () -> Unit = {},
    onProfileRequest: (String) -> Unit = {},
    onWatchGameRequest: (String) -> Unit = {},
    onRegisterRequest: () -> Unit = {},
    onLoginRequest: () -> Unit = {},
    onHomeRefresh: () -> Unit = {},
    onLeaderboardRefresh: () -> Unit = {},
    onLiveGamesRefresh: () -> Unit = {},
    onLeaderboardLoadMore: () -> Unit = {},
    onLiveGamesLoadMore: () -> Unit = {}
) {
    HomeHorizontalPager(
        screens = mapOf(
            Icons.Filled.Home to {
                if (isLoggedIn) {
                    MainScreen(
                        username = username ?: error("User not logged in"),
                        onPlayGame = onPlayGameRequest,
                        onMyProfileRequest = onMyProfileRequest,
                        onSettings = onSettingsRequest,
                        onExit = onExitRequest
                    )
                } else {
                    AuthScreen(
                        onRegisterRequest = onRegisterRequest,
                        onLoginRequest = onLoginRequest,
                        onSettings = onSettingsRequest
                    )
                }
            },
            ImageVector.vectorResource(R.drawable.spectate_icon) to {
                LoadStateRenderer(
                    loadState = liveGames,
                    pullToRefresh = onLiveGamesRefresh
                ) {
                    WatchLiveScreen(it, onWatchGameRequest, onLiveGamesLoadMore)
                }
            },
            Icons.Filled.Leaderboard to {
                LoadStateRenderer(
                    loadState = leaderboard,
                    pullToRefresh = onLeaderboardRefresh
                ) {
                    LeaderboardScreen(it, username, onProfileRequest, onLeaderboardLoadMore)
                }
            },
            Icons.Filled.Info to {
                LoadStateRenderer(
                    loadState = home,
                    pullToRefresh = onHomeRefresh
                ) {
                    AboutScreen(it)
                }
            }
        )
    )
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen()
}
