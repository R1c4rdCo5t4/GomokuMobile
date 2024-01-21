package pt.isel.pdm.gomoku.leaderboard

import androidx.compose.ui.test.junit4.createComposeRule
import org.junit.Rule
import org.junit.Test
import pt.isel.pdm.gomoku.assertContentExists
import pt.isel.pdm.gomoku.domain.home.leaderboard.Leaderboard
import pt.isel.pdm.gomoku.ui.screens.TestTags.Leaderboard.leaderBoardScreenTestTag
import pt.isel.pdm.gomoku.ui.screens.home.components.leaderboard.LeaderboardScreen

class LeaderboardScreenTest {

    @get:Rule
    val testRule = createComposeRule()

    private val testLeaderBoard = Leaderboard(emptyList())

    @Test
    fun leaderboard_screen_initial_state() {
        testRule.setContent { LeaderboardScreen(testLeaderBoard, "") }
        testRule.assertContentExists(leaderBoardScreenTestTag)
    }
}
