package pt.isel.pdm.gomoku.ui.screens.settings

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import pt.isel.pdm.gomoku.ui.GomokuActivity
import pt.isel.pdm.gomoku.ui.screens.home.HomeActivity
import pt.isel.pdm.gomoku.ui.theme.GomokuTheme
import pt.isel.pdm.gomoku.ui.utils.navigateTo

class SettingsActivity : GomokuActivity() {
    override val viewModel: SettingsViewModel by getViewModel<SettingsViewModel>(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GomokuTheme(viewModel.darkModeOn) {
                Surface(Modifier.fillMaxSize()) {
                    SettingsScreen(
                        isLoggedIn = viewModel.isLoggedIn,
                        soundOn = viewModel.soundOn,
                        vibrationOn = viewModel.vibrationOn,
                        darkTheme = viewModel.darkModeOn,
                        onSoundToggle = viewModel::toggleSound,
                        onVibrationToggle = viewModel::toggleVibration,
                        onThemeToggle = viewModel::toggleTheme,
                        onLogoutRequest = {
                            viewModel.logout {
                                navigateTo<HomeActivity> { intent ->
                                    intent.flags = FLAG_CLEAR_BACK_STACK
                                }
                            }
                        },
                        onBackRequest = ::finish
                    )
                }
            }
        }
    }
}
