package pt.isel.pdm.gomoku.main

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import junit.framework.TestCase
import org.junit.Rule
import org.junit.Test
import pt.isel.pdm.gomoku.ui.screens.TestTags.General.settingsButtonTestTag
import pt.isel.pdm.gomoku.ui.screens.TestTags.Home.mainExitButtonTestTag
import pt.isel.pdm.gomoku.ui.screens.TestTags.Home.mainPlayButtonTestTag
import pt.isel.pdm.gomoku.ui.screens.home.components.MainScreen

class MainScreenTest {

    @get:Rule
    val testRule = createComposeRule()

    @Test
    fun clicking_to_play_in_main_screen() {
        var playButtonClicked = false
        testRule.setContent { MainScreen(username = "", onPlayGame = { playButtonClicked = true }) }
        testRule.onNodeWithTag(mainPlayButtonTestTag).performClick()
        TestCase.assertTrue(playButtonClicked)
    }

    @Test
    fun clicking_to_settings_in_main_screen() {
        var settingsButtonClicked = false
        testRule.setContent {
            MainScreen(username = "", onSettings = { settingsButtonClicked = true })
        }
        testRule.onNodeWithTag(settingsButtonTestTag).performClick()
        TestCase.assertTrue(settingsButtonClicked)
    }

    @Test
    fun clicking_to_logout_in_main_screen() {
        var onExitButtonClicked = false
        testRule.setContent { MainScreen(username = "", onExit = { onExitButtonClicked = true }) }
        testRule.onNodeWithTag(mainExitButtonTestTag).performClick()
        TestCase.assertTrue(onExitButtonClicked)
    }
}
