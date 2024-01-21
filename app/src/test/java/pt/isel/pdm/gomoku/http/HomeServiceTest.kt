package pt.isel.pdm.gomoku.http

import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import org.junit.Rule
import org.junit.Test
import pt.isel.pdm.gomoku.domain.home.about.Author
import pt.isel.pdm.gomoku.domain.home.about.Home
import pt.isel.pdm.gomoku.mock.MockWebServerRule
import pt.isel.pdm.gomoku.services.api.home.HomeService
import pt.isel.pdm.gomoku.services.api.home.models.AuthorOutputModel
import pt.isel.pdm.gomoku.services.api.home.models.GetHomeOutputModel
import pt.isel.pdm.gomoku.services.http.HttpService
import pt.isel.pdm.gomoku.services.http.media.SirenEntity
import kotlin.test.assertEquals

class HomeServiceTest {
    @get:Rule
    val rule = MockWebServerRule()

    private val expectedHome = Home(
        "Gomoku",
        "0.1.0",
        "Gomoku, also called Five in a Row, is an abstract strategy board game",
        listOf(
            Author(
                "Ricardo Costa",
                "rcosta.ms358@gmail.com",
                "https://github.com/R1c4rdCo5t4",
                49511
            ),
            Author(
                "Vasco Costa",
                "vascosta15@gmail.com",
                "https://github.com/VascostaIsel",
                49412
            ),
            Author(
                "Diogo Almeida",
                "diogoalmeida107@hotmail.com",
                "https://github.com/wartuga",
                49449
            )
        ),
        "https://github.com/isel-leic-pdm/gomoku-2023-g28"
    )

    private val mockGetHomeOutputModel = SirenEntity(
        properties = GetHomeOutputModel(
            "Gomoku",
            "0.1.0",
            "Gomoku, also called Five in a Row, is an abstract strategy board game",
            listOf(
                AuthorOutputModel(
                    "Ricardo Costa",
                    "rcosta.ms358@gmail.com",
                    "https://github.com/R1c4rdCo5t4",
                    49511
                ),
                AuthorOutputModel(
                    "Vasco Costa",
                    "vascosta15@gmail.com",
                    "https://github.com/VascostaIsel",
                    49412
                ),
                AuthorOutputModel(
                    "Diogo Almeida",
                    "diogoalmeida107@hotmail.com",
                    "https://github.com/wartuga",
                    49449
                )
            ),
            "https://github.com/isel-leic-pdm/gomoku-2023-g28"
        )
    )

    @Test
    fun `getHome() should call service and return home information`() {
        // Arrange
        rule.webServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .addHeader("Content-Type", "application/vnd.siren+json")
                .setBody(rule.gson.toJson(mockGetHomeOutputModel))
        )

        val sut = HomeService(
            http = HttpService(
                baseUrl = rule.webServer.url("/").toString(),
                client = rule.httpClient,
                gson = rule.gson,
                updateLinks = rule.updateLinks
            )
        )

        // Act
        val actual = runBlocking { sut.getHome("") }

        // Assert
        assertEquals(expectedHome, actual.getOrNull())
    }
}
