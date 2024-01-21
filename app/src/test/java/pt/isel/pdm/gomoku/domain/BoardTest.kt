package pt.isel.pdm.gomoku.domain

import pt.isel.pdm.gomoku.domain.game.Board
import pt.isel.pdm.gomoku.domain.game.Color
import pt.isel.pdm.gomoku.domain.game.Coordinate
import pt.isel.pdm.gomoku.domain.game.Piece
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertNull

class BoardTest {

    @Test
    fun `test board creation`() {
        val board = Board(15)
        assertEquals(15, board.size)
    }

    @Test
    fun `try creating a board with an invalid size`() {
        assertFailsWith<IllegalArgumentException> { Board(10) }
    }

    @Test
    fun `test board getting and setting pieces`() {
        val board = Board(15)
        val piece = Piece(Coordinate(1, 1), Color.BLACK)
        val piece2 = Piece(Coordinate(1, 2), Color.WHITE)
        val newBoard = board.setPieceAt(piece.coord, piece.color)
        val newBoard2 = newBoard.setPieceAt(piece2.coord.row, piece2.coord.col, piece.color)
        assertEquals(piece, newBoard.getPieceAt(piece.coord))
        assertEquals(piece, newBoard2.getPieceAt(piece.coord.row, piece.coord.col))
    }

    @Test
    fun `try getting a piece with invalid coordinates`() {
        val board = Board(15)
        assertNull(board.getPieceAt(16, 0))
    }

    @Test
    fun `try setting a piece with invalid coordinates`() {
        val board = Board(15)
        assertFailsWith<IllegalArgumentException> { board.setPieceAt(-1, -1, Color.BLACK) }
    }

    @Test
    fun `try setting the same piece twice`() {
        val board = Board(15)
        val piece = Piece(Coordinate(1, 1), Color.BLACK)
        val newBoard = board.setPieceAt(piece.coord, piece.color)
        assertFailsWith<IllegalArgumentException> { newBoard.setPieceAt(piece.coord, piece.color) }
    }

    @Test
    fun `try setting a piece where there is already one`() {
        val board = Board(15)
        val piece = Piece(Coordinate(1, 1), Color.BLACK)
        val piece2 = Piece(Coordinate(1, 1), Color.WHITE)
        val newBoard = board.setPieceAt(piece.coord, piece.color)
        assertFailsWith<IllegalArgumentException> { newBoard.setPieceAt(piece2.coord, piece2.color) }
    }
}
