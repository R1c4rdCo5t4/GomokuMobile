package pt.isel.pdm.gomoku.services.api.lobby.models

data class JoinLobbyInputModel(
    val boardSize: Int,
    val variant: String,
    val opening: String
)
