package pt.isel.pdm.gomoku.home

import androidx.compose.ui.test.junit4.createComposeRule
import org.junit.Rule
import org.junit.Test
import pt.isel.pdm.gomoku.assertContentExists
import pt.isel.pdm.gomoku.assertContentNotExists
import pt.isel.pdm.gomoku.ui.screens.TestTags.Auth.authLoginButtonTestTag
import pt.isel.pdm.gomoku.ui.screens.TestTags.Auth.authRegisterButtonTestTag
import pt.isel.pdm.gomoku.ui.screens.TestTags.Auth.authTestTag
import pt.isel.pdm.gomoku.ui.screens.TestTags.General.settingsButtonTestTag
import pt.isel.pdm.gomoku.ui.screens.TestTags.Home.homeScreenPagerButtonsTestTag
import pt.isel.pdm.gomoku.ui.screens.TestTags.Home.homeScreenTabTestTag
import pt.isel.pdm.gomoku.ui.screens.TestTags.Home.homeScreenTestTag
import pt.isel.pdm.gomoku.ui.screens.TestTags.Home.mainExitButtonTestTag
import pt.isel.pdm.gomoku.ui.screens.TestTags.Home.mainPlayButtonTestTag
import pt.isel.pdm.gomoku.ui.screens.TestTags.Home.mainScreenTestTag
import pt.isel.pdm.gomoku.ui.screens.home.HomeScreen

class HomeScreenTest {

    @get:Rule
    val testRule = createComposeRule()

    @Test
    fun home_screen_initial_state_not_logged_in() {
        testRule.setContent { HomeScreen() }
        testRule.assertContentExists(
            homeScreenTestTag,
            homeScreenPagerButtonsTestTag,
            "$homeScreenTabTestTag-0",
            "$homeScreenTabTestTag-1",
            "$homeScreenTabTestTag-2",
            "$homeScreenTabTestTag-3",
            authLoginButtonTestTag,
            authRegisterButtonTestTag,
            authTestTag,
            settingsButtonTestTag
        )
        testRule.assertContentNotExists(
            mainScreenTestTag,
            mainPlayButtonTestTag,
            mainExitButtonTestTag
        )
    }

    @Test
    fun home_screen_initial_state_logged_in() {
        testRule.setContent { HomeScreen(isLoggedIn = true, username = "") }
        testRule.assertContentExists(
            homeScreenTestTag,
            homeScreenPagerButtonsTestTag,
            "$homeScreenTabTestTag-0",
            "$homeScreenTabTestTag-1",
            "$homeScreenTabTestTag-2",
            "$homeScreenTabTestTag-3",
            mainScreenTestTag,
            mainPlayButtonTestTag,
            settingsButtonTestTag,
            mainExitButtonTestTag
        )
        testRule.assertContentNotExists(
            authLoginButtonTestTag,
            authRegisterButtonTestTag,
            authTestTag
        )
    }
}
