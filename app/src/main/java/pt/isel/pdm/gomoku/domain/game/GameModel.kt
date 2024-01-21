package pt.isel.pdm.gomoku.domain.game

import pt.isel.pdm.gomoku.domain.user.User

data class GameModel(
    val state: GameState? = null,
    val black: User? = null,
    val white: User? = null,
    val opponent: Color? = null,
    val link: String? = null
)
