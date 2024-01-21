package pt.isel.pdm.gomoku.navigation

import androidx.compose.ui.test.assertIsNotSelected
import androidx.compose.ui.test.assertIsSelected
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.performTouchInput
import androidx.compose.ui.test.swipeLeft
import androidx.compose.ui.test.swipeRight
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import pt.isel.pdm.gomoku.assertContentExists
import pt.isel.pdm.gomoku.clickButton
import pt.isel.pdm.gomoku.ui.screens.TestTags.Home.homeScreenTabTestTag
import pt.isel.pdm.gomoku.ui.screens.home.HomeScreen

class WatchNavigationTest {
    @get:Rule
    val testRule = createComposeRule()

    @Before
    fun setUp() {
        testRule.setContent { HomeScreen() }
    }

    @Test
    fun watch_screen_to_home_screen_by_swiping() {
        // Arrange
        testRule.assertContentExists(
            "$homeScreenTabTestTag-0"
        )
        testRule.onRoot().performTouchInput {
            swipeLeft()
            // Act
            swipeRight()
        }
        // Assert
        testRule.onNodeWithTag("$homeScreenTabTestTag-0").assertIsSelected()
        testRule.onNodeWithTag("$homeScreenTabTestTag-1").assertIsNotSelected()
    }

    @Test
    fun watch_screen_to_home_screen_by_pressing_tab_button() {
        // Arrange
        testRule.clickButton("$homeScreenTabTestTag-1")
        testRule.assertContentExists(
            "$homeScreenTabTestTag-0"
        )
        testRule.onNodeWithTag("$homeScreenTabTestTag-1").assertIsSelected()
        testRule.onNodeWithTag("$homeScreenTabTestTag-0").assertIsNotSelected()
        // Act
        testRule.clickButton("$homeScreenTabTestTag-0")
        // Assert
        testRule.onNodeWithTag("$homeScreenTabTestTag-0").assertIsSelected()
        testRule.onNodeWithTag("$homeScreenTabTestTag-1").assertIsNotSelected()
    }

    @Test
    fun watch_screen_to_leaderboard_screen_by_swiping() {
        // Arrange
        testRule.assertContentExists(
            "$homeScreenTabTestTag-2"
        )
        testRule.onRoot().performTouchInput {
            swipeLeft()
            // Act
            swipeLeft()
        }
        // Assert
        testRule.onNodeWithTag("$homeScreenTabTestTag-2").assertIsSelected()
        testRule.onNodeWithTag("$homeScreenTabTestTag-1").assertIsNotSelected()
    }

    @Test
    fun watch_screen_to_leaderboard_screen_by_pressing_tab_button() {
        // Arrange
        testRule.clickButton("$homeScreenTabTestTag-1")
        testRule.assertContentExists(
            "$homeScreenTabTestTag-2"
        )
        testRule.onNodeWithTag("$homeScreenTabTestTag-1").assertIsSelected()
        testRule.onNodeWithTag("$homeScreenTabTestTag-2").assertIsNotSelected()
        // Act
        testRule.clickButton("$homeScreenTabTestTag-2")
        // Assert
        testRule.onNodeWithTag("$homeScreenTabTestTag-2").assertIsSelected()
        testRule.onNodeWithTag("$homeScreenTabTestTag-1").assertIsNotSelected()
    }

    @Test
    fun watch_screen_to_about_screen_by_pressing_tab_button() {
        // Arrange
        testRule.clickButton("$homeScreenTabTestTag-1")
        testRule.assertContentExists(
            "$homeScreenTabTestTag-3"
        )
        testRule.onNodeWithTag("$homeScreenTabTestTag-1").assertIsSelected()
        testRule.onNodeWithTag("$homeScreenTabTestTag-3").assertIsNotSelected()
        // Act
        testRule.clickButton("$homeScreenTabTestTag-3")
        // Assert
        testRule.onNodeWithTag("$homeScreenTabTestTag-3").assertIsSelected()
        testRule.onNodeWithTag("$homeScreenTabTestTag-1").assertIsNotSelected()
    }
}
