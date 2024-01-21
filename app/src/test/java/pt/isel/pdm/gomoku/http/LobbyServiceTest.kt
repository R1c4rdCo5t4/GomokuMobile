package pt.isel.pdm.gomoku.http

import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import org.junit.Rule
import org.junit.Test
import pt.isel.pdm.gomoku.domain.game.Opening
import pt.isel.pdm.gomoku.domain.game.Variant
import pt.isel.pdm.gomoku.mock.MockWebServerRule
import pt.isel.pdm.gomoku.services.api.lobby.LobbyService
import pt.isel.pdm.gomoku.services.api.lobby.models.FindMatchOutputModel
import pt.isel.pdm.gomoku.services.http.HttpService
import pt.isel.pdm.gomoku.services.http.media.SirenEntity

class LobbyServiceTest {
    @get:Rule
    val rule = MockWebServerRule()

    private val mockColor = SirenEntity(properties = FindMatchOutputModel(color = "B"))

    @Test
    fun `join() should call service and return unit`() {
        // Arrange
        rule.webServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .addHeader("Content-Type", "application/vnd.siren+json")
                .setBody(rule.gson.toJson(SirenEntity<Unit>()))
        )
        val sut = LobbyService(
            http = HttpService(
                baseUrl = rule.webServer.url("/").toString(),
                client = rule.httpClient,
                gson = rule.gson,
                updateLinks = rule.updateLinks
            )
        )
        // Act
        val actual = runBlocking {
            sut.join("", 15, Variant.FREESTYLE, Opening.FREESTYLE, "")
        }
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
        val sut = LobbyService(
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
    fun `findMatch() should call service and return the representation of the color`() {
        // Arrange
        rule.webServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .addHeader("Content-Type", "application/vnd.siren+json")
                .setBody(rule.gson.toJson(mockColor))
        )
        val sut = LobbyService(
            http = HttpService(
                baseUrl = rule.webServer.url("/").toString(),
                client = rule.httpClient,
                gson = rule.gson,
                updateLinks = rule.updateLinks
            )
        )
        // Act
        val actual = runBlocking { sut.findMatch("", "") }
        // Assert
        assertEquals("B", actual.getOrNull())
    }
}
