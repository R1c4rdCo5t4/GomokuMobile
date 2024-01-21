package pt.isel.pdm.gomoku.services.api.games.models

import pt.isel.pdm.gomoku.domain.game.Board
import pt.isel.pdm.gomoku.services.api.games.models.PieceOutputModel.Companion.toPiece

data class BoardOutputModel(val size: Int, val pieces: List<PieceOutputModel>) {

    companion object {
        fun BoardOutputModel.toBoard() = Board(size, pieces.map { it.toPiece() })
    }
}
