package pt.isel.pdm.gomoku.http

import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import org.junit.Rule
import pt.isel.pdm.gomoku.domain.game.Board
import pt.isel.pdm.gomoku.domain.game.Color
import pt.isel.pdm.gomoku.domain.game.Coordinate
import pt.isel.pdm.gomoku.domain.game.GameModel
import pt.isel.pdm.gomoku.domain.game.GameState
import pt.isel.pdm.gomoku.domain.game.GameStateModel
import pt.isel.pdm.gomoku.domain.game.Piece
import pt.isel.pdm.gomoku.domain.user.Stats
import pt.isel.pdm.gomoku.domain.user.User
import pt.isel.pdm.gomoku.mock.MockWebServerRule
import pt.isel.pdm.gomoku.services.api.games.GamesService
import pt.isel.pdm.gomoku.services.api.games.models.BoardOutputModel
import pt.isel.pdm.gomoku.services.api.games.models.GetGameOutputModel
import pt.isel.pdm.gomoku.services.api.games.models.GetGameStateOutputModel
import pt.isel.pdm.gomoku.services.api.games.models.GetGamesOutputModel
import pt.isel.pdm.gomoku.services.api.games.models.PieceOutputModel
import pt.isel.pdm.gomoku.services.api.users.models.GetUserOutputModel
import pt.isel.pdm.gomoku.services.http.HttpService
import pt.isel.pdm.gomoku.services.http.media.Action
import pt.isel.pdm.gomoku.services.http.media.SirenEntity
import pt.isel.pdm.gomoku.services.http.media.SubEntity
import pt.isel.pdm.gomoku.services.http.utils.Rels
import java.net.URI
import kotlin.test.Test
import kotlin.test.assertEquals

class GameServiceTest {
    @get:Rule
    val rule = MockWebServerRule()

    private val expectedGameStateModel = GameStateModel(
        Board(15, listOf(Piece(Coordinate(1, 1), Color.BLACK), Piece(Coordinate(1, 2), Color.WHITE))),
        Color.BLACK,
        GameState.RUNNING
    )

    private val expectedGameModel = GameModel(
        GameState.RUNNING,
        User("user1", "user1@gmail.com", Stats(600, 0, 0, 0, 0)),
        User("user2", "user2@gmail.com", Stats(600, 0, 0, 0, 0)),
        Color.WHITE,
        ""
    )

    private val mockGameStateOutputModel = SirenEntity(
        properties = GetGameStateOutputModel(
            BoardOutputModel(
                15,
                listOf(
                    PieceOutputModel(1, 1, "B"),
                    PieceOutputModel(1, 2, "W")
                )
            ),
            "B",
            "R"
        )
    )

    private val mockGamesOutputModel =
        SirenEntity(
            properties = GetGamesOutputModel(1),
            entities = listOf(
                SubEntity.EmbeddedRepresentation(
                    rel = listOf(Rels.GAME, Rels.ITEM),
                    properties = GetGameOutputModel(
                        gameState = "R",
                        opponentColor = "W"
                    ),
                    actions = listOf(
                        Action(
                            name = Rels.GAME,
                            href = URI("")
                        )
                    ),
                    entities = listOf(
                        SubEntity.EmbeddedRepresentation(
                            rel = listOf(Rels.BLACK_PLAYER),
                            properties = GetUserOutputModel(
                                name = "user1",
                                email = "user1@gmail.com",
                                stats = Stats(600, 0, 0, 0, 0)
                            )
                        ),
                        SubEntity.EmbeddedRepresentation(
                            rel = listOf(Rels.WHITE_PLAYER),
                            properties = GetUserOutputModel(
                                name = "user2",
                                email = "user2@gmail.com",
                                stats = Stats(600, 0, 0, 0, 0)
                            )
                        )
                    )
                )
            )
        )

    @Test
    fun `play() should call service and return unit`() {
        // Arrange
        rule.webServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .addHeader("Content-Type", "application/vnd.siren+json")
                .setBody(rule.gson.toJson(SirenEntity<Unit>()))
        )
        val sut = GamesService(
            http = HttpService(
                baseUrl = rule.webServer.url("/").toString(),
                client = rule.httpClient,
                gson = rule.gson,
                updateLinks = rule.updateLinks
            )
        )
        // Act
        val actual = runBlocking { sut.play("", 0, 0, "") }
        // Assert
        assertEquals(Unit, actual.getOrNull())
    }

    @Test
    fun `leave() should call service and return unit`() {
        // Arrange
        rule.webServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .addHeader("Content-Type", "application/vnd.siren+json")
                .setBody(rule.gson.toJson(SirenEntity<Unit>()))
        )
        val sut = GamesService(
            http = HttpService(
                baseUrl = rule.webServer.url("/").toString(),
                client = rule.httpClient,
                gson = rule.gson,
                updateLinks = rule.updateLinks
            )
        )
        // Act
        val actual = runBlocking { sut.leave("", "") }
        // Assert
        assertEquals(Unit, actual.getOrNull())
    }

    @Test
    fun `getGame() should call service and return game`() {
        // Arrange
        rule.webServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .addHeader("Content-Type", "application/vnd.siren+json")
                .setBody(rule.gson.toJson(mockGameStateOutputModel))
        )
        val sut = GamesService(
            http = HttpService(
                baseUrl = rule.webServer.url("/").toString(),
                client = rule.httpClient,
                gson = rule.gson,
                updateLinks = rule.updateLinks
            )
        )
        // Act
        val actual = runBlocking { sut.getGame("") }
        // Assert
        assertEquals(expectedGameStateModel, actual.getOrNull())
    }

    @Test
    fun `getGames() should call service and return games`() {
        // Arrange
        rule.webServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .addHeader("Content-Type", "application/vnd.siren+json")
                .setBody(rule.gson.toJson(mockGamesOutputModel))
        )
        val sut = GamesService(
            http = HttpService(
                baseUrl = rule.webServer.url("/").toString(),
                client = rule.httpClient,
                gson = rule.gson,
                updateLinks = rule.updateLinks
            )
        )
        // Act
        val actual = runBlocking {
            sut.getGames("", "user1", GameState.RUNNING, null, null, null)
        }
        // Assert
        assertEquals(listOf(expectedGameModel), actual.getOrNull())
    }
}
