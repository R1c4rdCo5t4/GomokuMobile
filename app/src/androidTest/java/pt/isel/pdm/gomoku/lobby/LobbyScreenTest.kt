package pt.isel.pdm.gomoku.lobby

import androidx.compose.ui.test.junit4.createComposeRule
import junit.framework.TestCase.assertTrue
import org.junit.Rule
import org.junit.Test
import pt.isel.pdm.gomoku.assertContentExists
import pt.isel.pdm.gomoku.clickButton
import pt.isel.pdm.gomoku.services.http.utils.APIResult
import pt.isel.pdm.gomoku.ui.screens.TestTags.Lobby.gameConfigBoardSizeButtonTestTag
import pt.isel.pdm.gomoku.ui.screens.TestTags.Lobby.gameConfigConfirmButtonTestTag
import pt.isel.pdm.gomoku.ui.screens.TestTags.Lobby.gameConfigOpeningButtonTestTag
import pt.isel.pdm.gomoku.ui.screens.TestTags.Lobby.gameConfigScreenTestTag
import pt.isel.pdm.gomoku.ui.screens.TestTags.Lobby.gameConfigVariantButtonTestTag
import pt.isel.pdm.gomoku.ui.screens.TestTags.Lobby.lobbyCancelButtonTestTag
import pt.isel.pdm.gomoku.ui.screens.TestTags.Lobby.lobbyScreenTestTag
import pt.isel.pdm.gomoku.ui.screens.lobby.LobbyScreen
import pt.isel.pdm.gomoku.ui.utils.idle
import pt.isel.pdm.gomoku.ui.utils.loaded
import pt.isel.pdm.gomoku.ui.utils.loading

class LobbyScreenTest {

    @get:Rule
    val testRule = createComposeRule()

    private val loadedLobby = APIResult.success(Unit)

    @Test
    fun lobby_screen_initial_state_when_idle() {
        testRule.setContent { LobbyScreen(idle()) {} }
        testRule.assertContentExists(
            gameConfigScreenTestTag,
            gameConfigBoardSizeButtonTestTag,
            gameConfigVariantButtonTestTag,
            gameConfigOpeningButtonTestTag,
            gameConfigConfirmButtonTestTag
        )
    }

    @Test
    fun lobby_screen_initial_state_when_loading() {
        testRule.setContent { LobbyScreen(loading()) {} }
        testRule.assertContentExists(
            lobbyScreenTestTag,
            lobbyCancelButtonTestTag
        )
    }

    @Test
    fun lobby_screen_initial_state_when_loaded() {
        testRule.setContent { LobbyScreen(loaded(loadedLobby)) {} }
        testRule.assertContentExists(lobbyScreenTestTag)
    }

    @Test
    fun click_in_cancel_button() {
        var cancelClicked = false
        testRule.setContent {
            LobbyScreen(loading(), onLeaveLobbyRequest = { cancelClicked = true })
        }
        testRule.clickButton(lobbyCancelButtonTestTag)
        assertTrue(cancelClicked)
    }

    @Test
    fun click_in_board_size_button() {
        var confirmButtonClicked = false
        testRule.setContent {
            LobbyScreen(
                idle(),
                onJoinLobbyRequest = { confirmButtonClicked = true }
            ) {}
        }
        testRule.clickButton(gameConfigConfirmButtonTestTag)
        assertTrue(confirmButtonClicked)
    }
}
