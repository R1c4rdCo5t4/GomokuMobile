package pt.isel.pdm.gomoku.navigation

import androidx.compose.ui.test.assertIsNotSelected
import androidx.compose.ui.test.assertIsSelected
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import pt.isel.pdm.gomoku.assertContentExists
import pt.isel.pdm.gomoku.clickButton
import pt.isel.pdm.gomoku.ui.screens.TestTags.Home.homeScreenTabTestTag
import pt.isel.pdm.gomoku.ui.screens.home.HomeScreen

class InfoNavigationTest {
    @get:Rule
    val testRule = createComposeRule()

    @Before
    fun setUp() {
        testRule.setContent { HomeScreen() }
        testRule.clickButton("$homeScreenTabTestTag-3")
    }

    @Test
    fun info_screen_to_home_screen_by_pressing_tab_button() {
        // Arrange
        testRule.assertContentExists(
            "$homeScreenTabTestTag-0"
        )
        testRule.onNodeWithTag("$homeScreenTabTestTag-3").assertIsSelected()
        testRule.onNodeWithTag("$homeScreenTabTestTag-0").assertIsNotSelected()
        // Act
        testRule.clickButton("$homeScreenTabTestTag-0")
        // Assert
        testRule.onNodeWithTag("$homeScreenTabTestTag-0").assertIsSelected()
        testRule.onNodeWithTag("$homeScreenTabTestTag-3").assertIsNotSelected()
    }

    @Test
    fun info_screen_to_watch_screen_by_pressing_tab_button() {
        // Arrange
        testRule.assertContentExists(
            "$homeScreenTabTestTag-1"
        )
        testRule.onNodeWithTag("$homeScreenTabTestTag-3").assertIsSelected()
        testRule.onNodeWithTag("$homeScreenTabTestTag-1").assertIsNotSelected()
        // Act
        testRule.clickButton("$homeScreenTabTestTag-1")
        // Assert
        testRule.onNodeWithTag("$homeScreenTabTestTag-1").assertIsSelected()
        testRule.onNodeWithTag("$homeScreenTabTestTag-3").assertIsNotSelected()
    }

    @Test
    fun info_screen_to_leaderboard_screen_by_pressing_tab_button() {
        // Arrange
        testRule.assertContentExists(
            "$homeScreenTabTestTag-2"
        )
        testRule.onNodeWithTag("$homeScreenTabTestTag-3").assertIsSelected()
        testRule.onNodeWithTag("$homeScreenTabTestTag-2").assertIsNotSelected()
        // Act
        testRule.clickButton("$homeScreenTabTestTag-2")
        // Assert
        testRule.onNodeWithTag("$homeScreenTabTestTag-2").assertIsSelected()
        testRule.onNodeWithTag("$homeScreenTabTestTag-3").assertIsNotSelected()
    }
}
