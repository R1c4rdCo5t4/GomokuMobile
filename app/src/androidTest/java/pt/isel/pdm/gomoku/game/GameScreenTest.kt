package pt.isel.pdm.gomoku.game

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import junit.framework.TestCase.assertTrue
import org.junit.Rule
import org.junit.Test
import pt.isel.pdm.gomoku.assertContentExists
import pt.isel.pdm.gomoku.clickButton
import pt.isel.pdm.gomoku.domain.game.Board
import pt.isel.pdm.gomoku.domain.game.Game
import pt.isel.pdm.gomoku.domain.game.GameState
import pt.isel.pdm.gomoku.domain.game.Player
import pt.isel.pdm.gomoku.domain.game.Turn
import pt.isel.pdm.gomoku.domain.user.Stats
import pt.isel.pdm.gomoku.domain.user.User
import pt.isel.pdm.gomoku.services.http.utils.APIResult
import pt.isel.pdm.gomoku.ui.screens.TestTags.Game.gameBoardTestTag
import pt.isel.pdm.gomoku.ui.screens.TestTags.Game.gamePieceTestTag
import pt.isel.pdm.gomoku.ui.screens.TestTags.Game.gamePlayButtonTestTag
import pt.isel.pdm.gomoku.ui.screens.TestTags.Game.gameScreenTestTag
import pt.isel.pdm.gomoku.ui.screens.game.GameScreen
import pt.isel.pdm.gomoku.ui.utils.loaded

class GameScreenTest {

    @get:Rule
    val testRule = createComposeRule()

    private val loadedTestGame: APIResult<Game> = APIResult.success(
        Game(
            board = Board(15, emptyList()),
            me = Player(
                User(
                    "test",
                    "test@gmail.com",
                    Stats(0, 0, 0, 0, 0)
                ),
                Turn.BLACK
            ),
            opponent = Player(
                User(
                    "test2",
                    "test2@gmail.com",
                    Stats(0, 0, 0, 0, 0)
                ),
                Turn.WHITE
            ),
            turn = Turn.BLACK,
            state = GameState.RUNNING
        )
    )

    @Test
    fun game_screen_initial_state() {
        testRule.setContent {
            GameScreen(
                gameState = loaded(loadedTestGame),
                isSpectating = false,
                validCoordinateSelected = true
            )
        }
        testRule.assertContentExists(
            gameScreenTestTag,
            gameBoardTestTag,
            gamePlayButtonTestTag
        )
        val boardSize = 15
        for (row in 1..boardSize) {
            for (col in 1..boardSize) {
                testRule.onNodeWithTag("$gamePieceTestTag-$row-$col")
            }
        }
    }

    @Test
    fun game_screen_make_move() {
        var squareClicked = false
        testRule.setContent {
            GameScreen(
                gameState = loaded(loadedTestGame),
                isSpectating = false,
                validCoordinateSelected = true,
                onCoordinatePressed = { _, _ -> squareClicked = true }
            )
        }

        testRule.clickButton("$gamePieceTestTag-5-5")
        testRule.clickButton(gamePlayButtonTestTag)

        assertTrue(squareClicked)
    }
}
