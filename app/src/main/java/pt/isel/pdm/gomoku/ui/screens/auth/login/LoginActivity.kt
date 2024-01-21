package pt.isel.pdm.gomoku.ui.screens.auth.login

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import pt.isel.pdm.gomoku.ui.GomokuActivity
import pt.isel.pdm.gomoku.ui.screens.home.HomeActivity
import pt.isel.pdm.gomoku.ui.theme.GomokuTheme
import pt.isel.pdm.gomoku.ui.utils.navigateTo

class LoginActivity : GomokuActivity() {
    override val viewModel: LoginViewModel by getViewModel<LoginViewModel>(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GomokuTheme(viewModel.darkModeOn) {
                Surface(Modifier.fillMaxSize()) {
                    LoginScreen(
                        loginEnable = viewModel.loginEnable,
                        onLoginRequest = { nameOrEmail, password ->
                            viewModel.login(nameOrEmail, password) {
                                navigateTo<HomeActivity> { intent ->
                                    intent.flags = FLAG_CLEAR_BACK_STACK
                                }
                            }
                        },
                        onBackRequest = {
                            navigateTo<HomeActivity>()
                        }
                    )
                }
            }
        }
    }
}
