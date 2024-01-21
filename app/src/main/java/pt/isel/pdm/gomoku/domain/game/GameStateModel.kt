package pt.isel.pdm.gomoku.domain.game

data class GameStateModel(
    val board: Board,
    val turn: Turn,
    val state: GameState
)
