package pt.isel.pdm.gomoku.services.api.lobby

import pt.isel.pdm.gomoku.domain.game.Opening
import pt.isel.pdm.gomoku.domain.game.Variant
import pt.isel.pdm.gomoku.services.api.lobby.models.FindMatchOutputModel
import pt.isel.pdm.gomoku.services.api.lobby.models.JoinLobbyInputModel
import pt.isel.pdm.gomoku.services.http.HttpService
import pt.isel.pdm.gomoku.services.http.utils.APIResult
import pt.isel.pdm.gomoku.services.http.utils.map
import pt.isel.pdm.gomoku.services.http.utils.mapUnit

class LobbyService(val http: HttpService) {

    suspend fun join(
        link: String,
        boardSize: Int,
        variant: Variant,
        opening: Opening,
        token: String
    ): APIResult<Unit> =
        http.post<Unit>(
            link = link,
            body = JoinLobbyInputModel(boardSize, variant.name, opening.name),
            token = token
        ).mapUnit()

    suspend fun findMatch(
        link: String,
        token: String
    ): APIResult<String?> =
        http.get<FindMatchOutputModel>(
            link = link,
            token = token
        ).map { siren -> siren.properties?.color }

    suspend fun leave(
        link: String,
        token: String
    ): APIResult<Unit> =
        http.delete<Unit>(
            link = link,
            token = token
        ).mapUnit()
}
