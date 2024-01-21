package pt.isel.pdm.gomoku.domain.home.leaderboard

import pt.isel.pdm.gomoku.domain.user.Stats

data class LeaderboardUser(
    val name: String,
    val stats: Stats,
    val link: String
)
