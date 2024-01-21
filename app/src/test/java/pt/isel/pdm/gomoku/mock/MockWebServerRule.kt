package pt.isel.pdm.gomoku.mock

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockWebServer
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import pt.isel.pdm.gomoku.services.http.media.SubEntity
import pt.isel.pdm.gomoku.services.http.utils.SubEntityDeserializer

const val CALL_TIMEOUT_IN_SECS = 10L

/**
 * A JUnit rule that starts (and shuts down) a mock web to be used in tests.
 */
class MockWebServerRule : TestWatcher() {

    val webServer = MockWebServer()
    val httpClient: OkHttpClient =
        OkHttpClient.Builder()
            .callTimeout(CALL_TIMEOUT_IN_SECS, java.util.concurrent.TimeUnit.SECONDS)
            .build()
    val gson: Gson = GsonBuilder().registerTypeAdapter(SubEntity::class.java, SubEntityDeserializer()).create()
    val updateLinks = { _: Map<String, String> -> }

    override fun starting(description: Description) {
        super.starting(description)
        webServer.start()
    }

    override fun finished(description: Description) {
        super.finished(description)
        webServer.shutdown()
    }
}
