package pt.isel.pdm.gomoku.services.api.games.models

import pt.isel.pdm.gomoku.domain.game.Coordinate
import pt.isel.pdm.gomoku.domain.game.Piece
import pt.isel.pdm.gomoku.domain.game.Turn.Companion.toTurn

data class PieceOutputModel(val row: Int, val col: Int, val color: String) {

    companion object {
        fun PieceOutputModel.toPiece() =
            Piece(
                coord = Coordinate(row, col),
                color = color.toTurn()
            )
    }
}
