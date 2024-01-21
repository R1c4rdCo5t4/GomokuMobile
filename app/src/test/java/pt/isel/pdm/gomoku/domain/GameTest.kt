package pt.isel.pdm.gomoku.domain

import pt.isel.pdm.gomoku.domain.game.Board
import pt.isel.pdm.gomoku.domain.game.Color
import pt.isel.pdm.gomoku.domain.game.Coordinate
import pt.isel.pdm.gomoku.domain.game.Game
import pt.isel.pdm.gomoku.domain.game.GameState
import pt.isel.pdm.gomoku.domain.game.Piece
import pt.isel.pdm.gomoku.domain.game.Player
import pt.isel.pdm.gomoku.domain.game.Turn
import pt.isel.pdm.gomoku.domain.user.Stats
import pt.isel.pdm.gomoku.domain.user.User
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class GameTest {

    @Test
    fun `test game creation`() {
        val user1 = User("user1", "user1@gmail.com", Stats(0, 0, 0, 0, 0))
        val user2 = User("user2", "user2@gmail.com", Stats(0, 0, 0, 0, 0))
        val game = Game(Board(15), Player(user1, Color.BLACK), Player(user2, Color.WHITE), Turn.BLACK, GameState.RUNNING)
        assertEquals(Board(15), game.board)
        assertEquals(Player(user1, Color.BLACK), game.me)
        assertEquals(Player(user2, Color.WHITE), game.opponent)
        assertEquals(Turn.BLACK, game.turn)
        assertEquals(GameState.RUNNING, game.state)
    }

    @Test
    fun `try creating a game with the same color for both players`() {
        val user1 = User("user1", "user1@gmail.com", Stats(0, 0, 0, 0, 0))
        val user2 = User("user2", "user2@gmail.com", Stats(0, 0, 0, 0, 0))
        assertFailsWith<IllegalArgumentException> {
            Game(Board(15), Player(user1, Color.BLACK), Player(user2, Color.BLACK), Turn.BLACK, GameState.RUNNING)
        }
    }

    @Test
    fun `create game and simulate a swap of turns by making a move`() {
        val user1 = User("user1", "user1@gmail.com", Stats(0, 0, 0, 0, 0))
        val user2 = User("user2", "user2@gmail.com", Stats(0, 0, 0, 0, 0))
        val game = Game(Board(15), Player(user1, Color.BLACK), Player(user2, Color.WHITE), Turn.BLACK, GameState.RUNNING)

        assertEquals(Turn.BLACK, game.turn)
        assertEquals(Board(15, emptyList()), game.board)

        val newBoard = game.board.setPieceAt(Coordinate(1, 1), Color.BLACK)
        val newGame = game.copy(board = newBoard, turn = Turn.WHITE)

        assertEquals(Turn.WHITE, newGame.turn)
        assertEquals(Board(15, listOf(Piece(Coordinate(1, 1), Color.BLACK))), newGame.board)
    }
}
