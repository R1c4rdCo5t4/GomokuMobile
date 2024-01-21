package pt.isel.pdm.gomoku.domain.game

import pt.isel.pdm.gomoku.domain.user.User

data class Player(
    val user: User,
    val color: Color
) {
    val name get() = user.name
    val stats get() = user.stats
}
