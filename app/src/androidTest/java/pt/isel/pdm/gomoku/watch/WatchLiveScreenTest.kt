package pt.isel.pdm.gomoku.watch

import androidx.compose.ui.test.junit4.createComposeRule
import org.junit.Rule
import org.junit.Test
import pt.isel.pdm.gomoku.assertContentExists
import pt.isel.pdm.gomoku.clickButton
import pt.isel.pdm.gomoku.domain.home.watch.LiveGame
import pt.isel.pdm.gomoku.domain.home.watch.WatchList
import pt.isel.pdm.gomoku.domain.user.Stats
import pt.isel.pdm.gomoku.domain.user.User
import pt.isel.pdm.gomoku.ui.screens.TestTags.Home.liveGameViewTestTag
import pt.isel.pdm.gomoku.ui.screens.TestTags.Home.watchLiveScreenTestTag
import pt.isel.pdm.gomoku.ui.screens.home.components.watch.WatchLiveScreen

class WatchLiveScreenTest {

    @get:Rule
    val testRule = createComposeRule()

    private val liveGames = WatchList(
        listOf(
            LiveGame(
                User(
                    "Ricardo",
                    "Ricardo@mail.com",
                    Stats(0, 0, 0, 0, 0)
                ),
                User(
                    "Diogo",
                    "Diogo@mail.com",
                    Stats(0, 0, 0, 0, 0)
                ),
                "/game/1"
            )
        )
    )

    @Test
    fun watch_live_screen_initial_state_with_no_games() {
        testRule.setContent {
            WatchLiveScreen(WatchList(emptyList()))
        }
        testRule.assertContentExists(
            watchLiveScreenTestTag
        )
    }

    @Test
    fun watch_live_screen_initial_state() {
        testRule.setContent {
            WatchLiveScreen(liveGames)
        }
        testRule.assertContentExists(
            watchLiveScreenTestTag,
            "$liveGameViewTestTag-0"
        )
    }

    @Test
    fun press_to_spectate_a_game_button_should_work_if_exists() {
        var viewGameClicked = false
        testRule.setContent {
            WatchLiveScreen(liveGames, onWatchGameRequest = { viewGameClicked = true })
        }
        testRule.clickButton("$liveGameViewTestTag-0")
        assert(viewGameClicked)
    }
}
