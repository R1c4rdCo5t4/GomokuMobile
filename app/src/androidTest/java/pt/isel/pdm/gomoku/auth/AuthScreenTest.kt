package pt.isel.pdm.gomoku.auth

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import junit.framework.TestCase.assertTrue
import org.junit.Rule
import org.junit.Test
import pt.isel.pdm.gomoku.assertContentExists
import pt.isel.pdm.gomoku.ui.screens.TestTags.Auth.authLoginButtonTestTag
import pt.isel.pdm.gomoku.ui.screens.TestTags.Auth.authRegisterButtonTestTag
import pt.isel.pdm.gomoku.ui.screens.TestTags.Auth.authTestTag
import pt.isel.pdm.gomoku.ui.screens.auth.AuthScreen
import pt.isel.pdm.gomoku.ui.screens.home.HomeScreen

class AuthScreenTest {

    @get:Rule
    val testRule = createComposeRule()

    @Test
    fun home_screen_to_auth_navigation() {
        testRule.setContent { HomeScreen() }
        testRule.assertContentExists(authTestTag)
    }

    @Test
    fun auth_screen_initial_state() {
        testRule.setContent { AuthScreen(onRegisterRequest = {}, onLoginRequest = {}) }
        testRule.assertContentExists(authTestTag, authRegisterButtonTestTag, authLoginButtonTestTag)
    }

    @Test
    fun clicking_to_register_in_auth_screen() {
        var registerButtonClicked = false
        testRule.setContent {
            AuthScreen(onRegisterRequest = { registerButtonClicked = true }, onLoginRequest = {})
        }
        testRule.onNodeWithTag(authRegisterButtonTestTag).performClick()
        assertTrue(registerButtonClicked)
    }

    @Test
    fun clicking_to_login_in_auth_screen() {
        var loginButtonClicked = false
        testRule.setContent {
            AuthScreen(onRegisterRequest = {}, onLoginRequest = { loginButtonClicked = true })
        }
        testRule.onNodeWithTag(authLoginButtonTestTag).performClick()
        assertTrue(loginButtonClicked)
    }
}
