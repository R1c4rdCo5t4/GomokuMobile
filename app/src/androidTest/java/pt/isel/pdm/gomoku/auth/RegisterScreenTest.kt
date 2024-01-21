package pt.isel.pdm.gomoku.auth

import androidx.compose.ui.test.junit4.createComposeRule
import junit.framework.TestCase.assertTrue
import org.junit.Rule
import org.junit.Test
import pt.isel.pdm.gomoku.assertContentExists
import pt.isel.pdm.gomoku.clickButton
import pt.isel.pdm.gomoku.ui.screens.TestTags.Register.registerButtonTestTag
import pt.isel.pdm.gomoku.ui.screens.TestTags.Register.registerConfirmPasswordTestTag
import pt.isel.pdm.gomoku.ui.screens.TestTags.Register.registerEmailTestTag
import pt.isel.pdm.gomoku.ui.screens.TestTags.Register.registerPasswordTestTag
import pt.isel.pdm.gomoku.ui.screens.TestTags.Register.registerTestTag
import pt.isel.pdm.gomoku.ui.screens.TestTags.Register.registerUsernameTestTag
import pt.isel.pdm.gomoku.ui.screens.auth.register.RegisterScreen

class RegisterScreenTest {

    @get:Rule
    val testRule = createComposeRule()

    @Test
    fun auth_screen_initial_state() {
        testRule.setContent { RegisterScreen(onRegisterRequest = { _, _, _, _ -> }) }
        testRule.assertContentExists(
            registerTestTag,
            registerUsernameTestTag,
            registerEmailTestTag,
            registerPasswordTestTag,
            registerConfirmPasswordTestTag,
            registerButtonTestTag
        )
    }

    @Test
    fun checking_clicks_on_auth_screen_buttons() {
        var registerRequested = false
        testRule.setContent {
            RegisterScreen(
                onRegisterRequest = { _, _, _, _ -> registerRequested = true }
            )
        }
        testRule.clickButton(registerButtonTestTag)
        assertTrue(registerRequested)
    }
}
