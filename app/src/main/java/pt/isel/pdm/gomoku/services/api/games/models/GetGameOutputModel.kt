package pt.isel.pdm.gomoku.services.api.games.models

data class GetGameOutputModel(
    val gameState: String,
    val opponentColor: String?
)
