package pt.isel.pdm.gomoku.services.api.games.models

data class GetGameStateOutputModel(
    val board: BoardOutputModel,
    val turn: String,
    val state: String
)
