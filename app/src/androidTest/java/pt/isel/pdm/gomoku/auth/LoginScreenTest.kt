package pt.isel.pdm.gomoku.auth

import androidx.compose.ui.test.junit4.createComposeRule
import junit.framework.TestCase
import org.junit.Rule
import org.junit.Test
import pt.isel.pdm.gomoku.assertContentExists
import pt.isel.pdm.gomoku.clickButton
import pt.isel.pdm.gomoku.ui.screens.TestTags.Login.loginButtonTestTag
import pt.isel.pdm.gomoku.ui.screens.TestTags.Login.loginPasswordTestTag
import pt.isel.pdm.gomoku.ui.screens.TestTags.Login.loginTestTag
import pt.isel.pdm.gomoku.ui.screens.TestTags.Login.loginUsernameOrEmailTestTag
import pt.isel.pdm.gomoku.ui.screens.auth.login.LoginScreen

class LoginScreenTest {

    @get:Rule
    val testRule = createComposeRule()

    @Test
    fun auth_screen_initial_state() {
        testRule.setContent { LoginScreen(onLoginRequest = { _, _ -> }) }
        testRule.assertContentExists(
            loginTestTag,
            loginUsernameOrEmailTestTag,
            loginPasswordTestTag,
            loginButtonTestTag
        )
    }

    @Test
    fun checking_clicks_on_auth_screen_buttons() {
        var loginRequested = false
        testRule.setContent { LoginScreen(onLoginRequest = { _, _ -> loginRequested = true }) }
        testRule.clickButton(loginButtonTestTag)
        TestCase.assertTrue(loginRequested)
    }
}
