package pt.isel.pdm.gomoku.http

import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import org.junit.Rule
import org.junit.Test
import pt.isel.pdm.gomoku.domain.user.Stats
import pt.isel.pdm.gomoku.domain.user.User
import pt.isel.pdm.gomoku.domain.user.login.LoginModel
import pt.isel.pdm.gomoku.mock.MockWebServerRule
import pt.isel.pdm.gomoku.services.api.users.UsersService
import pt.isel.pdm.gomoku.services.api.users.models.GetUserOutputModel
import pt.isel.pdm.gomoku.services.api.users.models.GetUsersOutputModel
import pt.isel.pdm.gomoku.services.api.users.models.LoginOutputModel
import pt.isel.pdm.gomoku.services.http.HttpService
import pt.isel.pdm.gomoku.services.http.media.Action
import pt.isel.pdm.gomoku.services.http.media.Link
import pt.isel.pdm.gomoku.services.http.media.SirenEntity
import pt.isel.pdm.gomoku.services.http.media.SubEntity
import pt.isel.pdm.gomoku.services.http.utils.Rels
import java.net.URI

class UserServiceTest {
    @get:Rule
    val rule = MockWebServerRule()

    private val expectedLogin = LoginModel("", "")

    private val expectedUser1 = User(
        "user1",
        "user1@gmail.com",
        Stats(600, 0, 0, 0, 0)
    )

    private val expectedUsers = listOf(
        User(
            "user1",
            "user1@gmail.com",
            Stats(600, 0, 0, 0, 0),
            ""
        ),
        User(
            "user2",
            "user2@gmail.com",
            Stats(600, 0, 0, 0, 0),
            ""
        )
    )

    private val mockLoginOutputModel = SirenEntity(
        properties = LoginOutputModel("", 0),
        links = listOf(Link(listOf("user-home"), URI("")))
    )

    private val mockGetUserOutputModel = SirenEntity(
        properties = GetUserOutputModel(
            "user1",
            "user1@gmail.com",
            Stats(600, 0, 0, 0, 0)
        )
    )

    private val mockGetUsersOutputModel = SirenEntity(
        properties = GetUsersOutputModel(2),
        entities = listOf(
            SubEntity.EmbeddedRepresentation(
                rel = listOf(Rels.USER, Rels.ITEM),
                properties = GetUserOutputModel(
                    "user1",
                    "user1@gmail.com",
                    Stats(600, 0, 0, 0, 0)
                ),
                actions = listOf(
                    Action(
                        name = Rels.USER,
                        href = URI("")
                    )
                )
            ),
            SubEntity.EmbeddedRepresentation(
                rel = listOf(Rels.USER, Rels.ITEM),
                properties = GetUserOutputModel(
                    "user2",
                    "user2@gmail.com",
                    Stats(600, 0, 0, 0, 0)
                ),
                actions = listOf(
                    Action(
                        name = Rels.USER,
                        href = URI("")
                    )
                )
            )
        )
    )

    @Test
    fun `getUserHome() should call service and return unit`() {
        // Arrange
        rule.webServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .addHeader("Content-Type", "application/vnd.siren+json")
                .setBody(rule.gson.toJson(SirenEntity<Unit>()))
        )
        val sut = UsersService(
            http = HttpService(
                baseUrl = rule.webServer.url("/").toString(),
                client = rule.httpClient,
                gson = rule.gson,
                updateLinks = rule.updateLinks
            )
        )
        // Act
        val actual = runBlocking { sut.getUserHome("", "") }
        // Assert
        assertEquals(Unit, actual.getOrNull())
    }

    @Test
    fun `register should call service and return unit`() {
        // Arrange
        rule.webServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .addHeader("Content-Type", "application/vnd.siren+json")
                .setBody(rule.gson.toJson(SirenEntity<Unit>()))
        )
        val sut = UsersService(
            http = HttpService(
                baseUrl = rule.webServer.url("/").toString(),
                client = rule.httpClient,
                gson = rule.gson,
                updateLinks = rule.updateLinks
            )
        )
        // Act
        val actual = runBlocking { sut.register("", "", "", "") }
        // Assert
        assertEquals(Unit, actual.getOrNull())
    }

    @Test
    fun `login should call service and return login information`() {
        // Arrange
        rule.webServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .addHeader("Content-Type", "application/vnd.siren+json")
                .setBody(rule.gson.toJson(mockLoginOutputModel))
        )
        val sut = UsersService(
            http = HttpService(
                baseUrl = rule.webServer.url("/").toString(),
                client = rule.httpClient,
                gson = rule.gson,
                updateLinks = rule.updateLinks
            )
        )
        // Act
        val actual = runBlocking { sut.login("", "", "", "") }
        // Assert
        assertEquals(expectedLogin, actual.getOrNull())
    }

    @Test
    fun `logout should call service and return unit`() {
        // Arrange
        rule.webServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .addHeader("Content-Type", "application/vnd.siren+json")
                .setBody(rule.gson.toJson(SirenEntity<Unit>()))
        )
        val sut = UsersService(
            http = HttpService(
                baseUrl = rule.webServer.url("/").toString(),
                client = rule.httpClient,
                gson = rule.gson,
                updateLinks = rule.updateLinks
            )
        )
        // Act
        val actual = runBlocking { sut.logout("", "") }
        // Assert
        assertEquals(Unit, actual.getOrNull())
    }

    @Test
    fun `getUser() should call service and return user information`() {
        // Arrange
        rule.webServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .addHeader("Content-Type", "application/vnd.siren+json")
                .setBody(rule.gson.toJson(mockGetUserOutputModel))
        )
        val sut = UsersService(
            http = HttpService(
                baseUrl = rule.webServer.url("/").toString(),
                client = rule.httpClient,
                gson = rule.gson,
                updateLinks = rule.updateLinks
            )
        )
        // Act
        val actual = runBlocking { sut.getUser("") }
        // Assert
        assertEquals(expectedUser1, actual.getOrNull())
    }

    @Test
    fun `getUsers() should call service and return list of users`() {
        // Arrange
        rule.webServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .addHeader("Content-Type", "application/vnd.siren+json")
                .setBody(rule.gson.toJson(mockGetUsersOutputModel))
        )

        val sut = UsersService(
            http = HttpService(
                baseUrl = rule.webServer.url("/").toString(),
                client = rule.httpClient,
                gson = rule.gson,
                updateLinks = rule.updateLinks
            )
        )
        // Act
        val actual = runBlocking { sut.getUsers("") }
        // Assert
        assertEquals(expectedUsers, actual.getOrNull())
    }
}
