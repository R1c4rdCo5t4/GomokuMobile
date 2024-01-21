package pt.isel.pdm.gomoku.ui.screens.home

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import pt.isel.pdm.gomoku.ui.GomokuActivity
import pt.isel.pdm.gomoku.ui.screens.Intents
import pt.isel.pdm.gomoku.ui.screens.auth.login.LoginActivity
import pt.isel.pdm.gomoku.ui.screens.auth.register.RegisterActivity
import pt.isel.pdm.gomoku.ui.screens.game.GameActivity
import pt.isel.pdm.gomoku.ui.screens.lobby.LobbyActivity
import pt.isel.pdm.gomoku.ui.screens.profile.ProfileActivity
import pt.isel.pdm.gomoku.ui.screens.settings.SettingsActivity
import pt.isel.pdm.gomoku.ui.theme.GomokuTheme
import pt.isel.pdm.gomoku.ui.utils.Idle
import pt.isel.pdm.gomoku.ui.utils.Loaded
import pt.isel.pdm.gomoku.ui.utils.idle
import pt.isel.pdm.gomoku.ui.utils.navigateTo

class HomeActivity : GomokuActivity() {
    override val viewModel: HomeViewModel by getViewModel<HomeViewModel>(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            viewModel.home.collectLatest { home ->
                if (home is Idle) {
                    viewModel.getHome()
                }
                if (home is Loaded) {
                    lifecycleScope.launch {
                        viewModel.leaderboard.collectLatest { leaderboard ->
                            if (leaderboard is Idle) viewModel.getLeaderboard()
                        }
                    }
                    lifecycleScope.launch {
                        viewModel.liveGames.collectLatest { liveGames ->
                            if (liveGames is Idle) viewModel.getLiveGames()
                        }
                    }
                }
            }
        }
        setContent {
            val home by viewModel.home.collectAsState(idle())
            val leaderboard by viewModel.leaderboard.collectAsState(idle())
            val liveGames by viewModel.liveGames.collectAsState(idle())
            GomokuTheme(viewModel.darkModeOn) {
                Surface(Modifier.fillMaxSize()) {
                    if (viewModel.settingsLoaded) {
                        HomeScreen(
                            home = home,
                            leaderboard = leaderboard,
                            liveGames = liveGames,
                            isLoggedIn = viewModel.isLoggedIn,
                            username = viewModel.username,
                            onPlayGameRequest = { navigateTo<LobbyActivity>() },
                            onSettingsRequest = { navigateTo<SettingsActivity>() },
                            onExitRequest = ::finishAffinity,
                            onMyProfileRequest = ::navigateToProfile,
                            onProfileRequest = ::navigateToProfile,
                            onWatchGameRequest = ::navigateToGame,
                            onRegisterRequest = { navigateTo<RegisterActivity>() },
                            onLoginRequest = { navigateTo<LoginActivity>() },
                            onHomeRefresh = viewModel::getHome,
                            onLeaderboardRefresh = viewModel::getLeaderboard,
                            onLiveGamesRefresh = viewModel::getLiveGames,
                            onLeaderboardLoadMore = viewModel::loadMoreLeaderboard,
                            onLiveGamesLoadMore = viewModel::loadMoreLiveGames
                        )
                    }
                }
            }
        }
    }

    private fun navigateToProfile(userLink: String? = null) {
        lifecycleScope.launch {
            val link = userLink ?: viewModel.session.getUserLink()
            navigateTo<ProfileActivity> { intent ->
                intent.putExtra(Intents.USER_LINK, link)
            }
        }
    }

    private fun navigateToGame(gameLink: String) {
        navigateTo<GameActivity> { intent ->
            intent.putExtra(Intents.GAME_LINK, gameLink)
        }
    }
}
