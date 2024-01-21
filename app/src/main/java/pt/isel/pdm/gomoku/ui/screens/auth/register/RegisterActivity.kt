package pt.isel.pdm.gomoku.ui.screens.auth.register

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import pt.isel.pdm.gomoku.ui.GomokuActivity
import pt.isel.pdm.gomoku.ui.screens.auth.login.LoginActivity
import pt.isel.pdm.gomoku.ui.theme.GomokuTheme
import pt.isel.pdm.gomoku.ui.utils.navigateTo

class RegisterActivity : GomokuActivity() {
    override val viewModel: RegisterViewModel by getViewModel<RegisterViewModel>(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GomokuTheme(viewModel.darkModeOn) {
                Surface(Modifier.fillMaxSize()) {
                    RegisterScreen(
                        registerEnable = viewModel.registerEnable,
                        onRegisterRequest = { name, email, password, confirmPassword ->
                            viewModel.register(name, email, password, confirmPassword) {
                                navigateTo<LoginActivity> { intent ->
                                    intent.flags = FLAG_CLEAR_BACK_STACK
                                }
                            }
                        }
                    )
                }
            }
        }
    }
}
