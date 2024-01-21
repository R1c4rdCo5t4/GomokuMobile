package pt.isel.pdm.gomoku.navigation

import androidx.compose.ui.test.assertIsNotSelected
import androidx.compose.ui.test.assertIsSelected
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.performTouchInput
import androidx.compose.ui.test.swipeLeft
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import pt.isel.pdm.gomoku.assertContentExists
import pt.isel.pdm.gomoku.assertContentNotExists
import pt.isel.pdm.gomoku.clickButton
import pt.isel.pdm.gomoku.ui.screens.TestTags.About.aboutScreenTestTag
import pt.isel.pdm.gomoku.ui.screens.TestTags.Home.homeScreenTabTestTag
import pt.isel.pdm.gomoku.ui.screens.TestTags.Home.homeScreenTestTag
import pt.isel.pdm.gomoku.ui.screens.TestTags.Home.watchLiveScreenTestTag
import pt.isel.pdm.gomoku.ui.screens.TestTags.Leaderboard.leaderBoardScreenTestTag
import pt.isel.pdm.gomoku.ui.screens.home.HomeScreen

class HomeNavigationTest {
    @get:Rule
    val testRule = createComposeRule()

    @Before
    fun setUp() {
        testRule.setContent { HomeScreen() }
    }

    @Test
    fun home_screen_to_watch_screen_by_swiping() {
        // Arrange
        testRule.assertContentExists(
            homeScreenTestTag,
            "$homeScreenTabTestTag-1"
        )
        testRule.assertContentNotExists(
            watchLiveScreenTestTag
        )
        testRule.onNodeWithTag("$homeScreenTabTestTag-0").assertIsSelected()
        testRule.onNodeWithTag("$homeScreenTabTestTag-1").assertIsNotSelected()
        // Act
        testRule.onRoot().performTouchInput { swipeLeft() }
        // Assert
        testRule.onNodeWithTag("$homeScreenTabTestTag-1").assertIsSelected()
        testRule.onNodeWithTag("$homeScreenTabTestTag-0").assertIsNotSelected()
    }

    @Test
    fun home_screen_to_watch_screen_by_pressing_tab_button() {
        // Arrange
        testRule.assertContentExists(
            homeScreenTestTag,
            "$homeScreenTabTestTag-1"
        )
        testRule.assertContentNotExists(
            watchLiveScreenTestTag
        )
        testRule.onNodeWithTag("$homeScreenTabTestTag-0").assertIsSelected()
        testRule.onNodeWithTag("$homeScreenTabTestTag-1").assertIsNotSelected()
        // Act
        testRule.clickButton("$homeScreenTabTestTag-1")
        // Assert
        testRule.onNodeWithTag("$homeScreenTabTestTag-1").assertIsSelected()
        testRule.onNodeWithTag("$homeScreenTabTestTag-0").assertIsNotSelected()
    }

    @Test
    fun home_screen_to_leaderboard_screen_by_swiping() {
        // Arrange
        testRule.assertContentExists(
            homeScreenTestTag,
            "$homeScreenTabTestTag-2"
        )
        testRule.assertContentNotExists(
            leaderBoardScreenTestTag
        )
        testRule.onNodeWithTag("$homeScreenTabTestTag-0").assertIsSelected()
        testRule.onNodeWithTag("$homeScreenTabTestTag-2").assertIsNotSelected()
        // Act
        testRule.onRoot().performTouchInput {
            swipeLeft()
            swipeLeft()
        }
        // Assert
        testRule.onNodeWithTag("$homeScreenTabTestTag-2").assertIsSelected()
        testRule.onNodeWithTag("$homeScreenTabTestTag-0").assertIsNotSelected()
    }

    @Test
    fun home_screen_to_leaderboard_screen_by_pressing_tab_button() {
        // Arrange
        testRule.assertContentExists(
            homeScreenTestTag,
            "$homeScreenTabTestTag-2"
        )
        testRule.assertContentNotExists(
            leaderBoardScreenTestTag
        )
        testRule.onNodeWithTag("$homeScreenTabTestTag-0").assertIsSelected()
        testRule.onNodeWithTag("$homeScreenTabTestTag-2").assertIsNotSelected()
        // Act
        testRule.clickButton("$homeScreenTabTestTag-2")
        // Assert
        testRule.onNodeWithTag("$homeScreenTabTestTag-2").assertIsSelected()
        testRule.onNodeWithTag("$homeScreenTabTestTag-0").assertIsNotSelected()
    }

    @Test
    fun home_screen_to_about_screen_by_pressing_tab_button() {
        // Arrange
        testRule.assertContentExists(
            homeScreenTestTag,
            "$homeScreenTabTestTag-3"
        )
        testRule.assertContentNotExists(
            aboutScreenTestTag
        )
        testRule.onNodeWithTag("$homeScreenTabTestTag-0").assertIsSelected()
        testRule.onNodeWithTag("$homeScreenTabTestTag-3").assertIsNotSelected()
        // Act
        testRule.clickButton("$homeScreenTabTestTag-3")
        // Assert
        testRule.onNodeWithTag("$homeScreenTabTestTag-3").assertIsSelected()
        testRule.onNodeWithTag("$homeScreenTabTestTag-0").assertIsNotSelected()
    }
}
