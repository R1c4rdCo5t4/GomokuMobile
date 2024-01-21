package pt.isel.pdm.gomoku.services.api.games

import pt.isel.pdm.gomoku.domain.game.Color
import pt.isel.pdm.gomoku.domain.game.GameModel
import pt.isel.pdm.gomoku.domain.game.GameState
import pt.isel.pdm.gomoku.domain.game.GameState.Companion.toGameState
import pt.isel.pdm.gomoku.domain.game.GameStateModel
import pt.isel.pdm.gomoku.domain.game.Turn.Companion.toTurn
import pt.isel.pdm.gomoku.domain.user.User
import pt.isel.pdm.gomoku.services.api.games.models.BoardOutputModel.Companion.toBoard
import pt.isel.pdm.gomoku.services.api.games.models.GetGameOutputModel
import pt.isel.pdm.gomoku.services.api.games.models.GetGameStateOutputModel
import pt.isel.pdm.gomoku.services.api.games.models.GetGamesOutputModel
import pt.isel.pdm.gomoku.services.api.games.models.PlayGameInputModel
import pt.isel.pdm.gomoku.services.api.users.models.GetUserOutputModel
import pt.isel.pdm.gomoku.services.http.HttpService
import pt.isel.pdm.gomoku.services.http.media.Entity
import pt.isel.pdm.gomoku.services.http.media.getEmbeddedRepresentations
import pt.isel.pdm.gomoku.services.http.utils.APIResult
import pt.isel.pdm.gomoku.services.http.utils.Rels
import pt.isel.pdm.gomoku.services.http.utils.map
import pt.isel.pdm.gomoku.services.http.utils.mapUnit

class GamesService(val http: HttpService) {

    suspend fun play(
        link: String,
        row: Int,
        col: Int,
        token: String
    ): APIResult<Unit> =
        http.put<Unit>(
            link = link,
            body = PlayGameInputModel(row, col),
            token = token
        ).mapUnit()

    suspend fun leave(
        link: String,
        token: String
    ): APIResult<Unit> =
        http.put<Unit>(
            link = link,
            token = token
        ).mapUnit()

    suspend fun getGame(
        link: String
    ): APIResult<GameStateModel> =
        http.get<GetGameStateOutputModel>(
            link = link
        ).map {
            val game = it.getPropertiesOrThrow()
            GameStateModel(
                board = game.board.toBoard(),
                turn = game.turn.toTurn(),
                state = game.state.toGameState()
            )
        }

    suspend fun getGames(
        link: String,
        username: String? = null,
        state: GameState? = null,
        page: Int? = null,
        limit: Int? = null,
        sort: String? = null
    ): APIResult<List<GameModel>> =
        http.get<GetGamesOutputModel>(
            link = link,
            params = mapOf(
                "username" to username,
                "state" to state?.toString(),
                "page" to page,
                "limit" to limit,
                "sort" to sort
            )
        ).map { siren ->
            siren
                .getEmbeddedRepresentations<GetGameOutputModel>(Rels.GAME, Rels.ITEM)
                .map {
                    val properties = it.getPropertiesOrThrow()
                    GameModel(
                        state = properties.gameState.toGameState(),
                        black = getGameUser(it, Color.BLACK),
                        white = getGameUser(it, Color.WHITE),
                        link = it.getActionLink(Rels.GAME),
                        opponent = properties.opponentColor?.toTurn()
                    )
                }
        }

    private fun getGameUser(siren: Entity<*>, color: Color): User {
        val rel = if (color == Color.BLACK) Rels.BLACK_PLAYER else Rels.WHITE_PLAYER
        return siren
            .getEmbeddedRepresentations<GetUserOutputModel>(rel)
            .first()
            .getPropertiesOrThrow()
            .let { User(it.name, it.email, it.stats) }
    }
}
