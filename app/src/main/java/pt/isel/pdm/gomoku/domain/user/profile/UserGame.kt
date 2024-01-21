package pt.isel.pdm.gomoku.domain.user.profile

import pt.isel.pdm.gomoku.domain.game.GameState
import pt.isel.pdm.gomoku.domain.game.Player

data class UserGame(
    val state: GameState,
    val opponent: Player,
    val gameLink: String
)
