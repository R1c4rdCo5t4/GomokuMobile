package pt.isel.pdm.gomoku.domain.home.watch

import pt.isel.pdm.gomoku.domain.user.User

data class LiveGame(
    val black: User,
    val white: User,
    val gameLink: String
)
