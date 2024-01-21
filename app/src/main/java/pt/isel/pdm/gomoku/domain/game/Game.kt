package pt.isel.pdm.gomoku.domain.game

data class Game(
    val board: Board,
    val me: Player,
    val opponent: Player,
    val turn: Turn,
    val state: GameState
) {
    val isRunning get() = state == GameState.RUNNING
    val isOver get() = !isRunning
    val isMyTurn get() = turn == me.color

    init {
        require(me.color != opponent.color) { "Players must have different colors" }
    }
}
