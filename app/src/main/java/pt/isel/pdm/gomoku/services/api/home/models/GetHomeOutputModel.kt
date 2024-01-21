package pt.isel.pdm.gomoku.services.api.home.models

data class GetHomeOutputModel(
    val title: String,
    val version: String,
    val description: String,
    val authors: List<AuthorOutputModel>,
    val repository: String
)
