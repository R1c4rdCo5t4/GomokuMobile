package pt.isel.pdm.gomoku.ui.screens.profile

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
import pt.isel.pdm.gomoku.ui.screens.game.GameActivity
import pt.isel.pdm.gomoku.ui.theme.GomokuTheme
import pt.isel.pdm.gomoku.ui.utils.Idle
import pt.isel.pdm.gomoku.ui.utils.idle
import pt.isel.pdm.gomoku.ui.utils.navigateTo

class ProfileActivity : GomokuActivity() {
    override val viewModel: ProfileViewModel by getViewModel<ProfileViewModel>(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val userLink = intent.getStringExtra(Intents.USER_LINK) ?: error("User link is null")
        lifecycleScope.launch {
            viewModel.profile.collectLatest { profile ->
                if (profile is Idle) viewModel.getProfile(userLink)
            }
        }
        setContent {
            val profile by viewModel.profile.collectAsState(idle())
            GomokuTheme(viewModel.darkModeOn) {
                Surface(Modifier.fillMaxSize()) {
                    ProfileScreen(
                        profileState = profile,
                        onProfileRefresh = { viewModel.getProfile(userLink) },
                        onGetGame = ::navigateToGame
                    )
                }
            }
        }
    }

    private fun navigateToGame(gameLink: String) {
        navigateTo<GameActivity> { intent ->
            intent.putExtra(Intents.GAME_LINK, gameLink)
        }
    }
}
