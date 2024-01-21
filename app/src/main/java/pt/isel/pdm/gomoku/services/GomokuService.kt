package pt.isel.pdm.gomoku.services

import pt.isel.pdm.gomoku.services.api.games.GamesService
import pt.isel.pdm.gomoku.services.api.home.HomeService
import pt.isel.pdm.gomoku.services.api.lobby.LobbyService
import pt.isel.pdm.gomoku.services.api.users.UsersService
import pt.isel.pdm.gomoku.services.http.HttpService

class GomokuService(val http: HttpService) {
    val homeService = HomeService(http)
    val usersService = UsersService(http)
    val gamesService = GamesService(http)
    val lobbyService = LobbyService(http)
}
