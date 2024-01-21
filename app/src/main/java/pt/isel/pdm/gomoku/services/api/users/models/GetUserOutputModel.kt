package pt.isel.pdm.gomoku.services.api.users.models

import pt.isel.pdm.gomoku.domain.user.Stats

data class GetUserOutputModel(
    val name: String,
    val email: String,
    val stats: Stats
)
