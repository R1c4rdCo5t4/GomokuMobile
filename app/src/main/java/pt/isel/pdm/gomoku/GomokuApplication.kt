package pt.isel.pdm.gomoku

import android.app.Application
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import pt.isel.pdm.gomoku.services.GomokuService
import pt.isel.pdm.gomoku.services.http.HttpService
import pt.isel.pdm.gomoku.services.http.media.SubEntity
import pt.isel.pdm.gomoku.services.http.utils.SubEntityDeserializer
import pt.isel.pdm.gomoku.storage.links.LinksMem
import pt.isel.pdm.gomoku.storage.session.SessionDataStore
import pt.isel.pdm.gomoku.storage.settings.SettingsDataStore
import java.util.logging.Logger

class GomokuApplication : Application(), Dependencies {

    private val sessionDataStore: DataStore<Preferences> by preferencesDataStore(name = "session")
    private val settingsDataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

    override val session: SessionDataStore get() = SessionDataStore(sessionDataStore)
    override val settings: SettingsDataStore get() = SettingsDataStore(settingsDataStore)

    override val links = LinksMem()
    val http = HttpService(
        baseUrl = BASE_URL,
        client = OkHttpClient(),
        gson = GsonBuilder().registerTypeAdapter(SubEntity::class.java, SubEntityDeserializer()).create(),
        updateLinks = links::updateLinks
    )
    override val service = GomokuService(http)

    companion object {
        private const val NGROK_URL = "https://17ed-2001-818-e359-fe00-d806-a4c0-f885-d6da.ngrok-free.app"
        private const val BASE_URL = "$NGROK_URL/api"
        val logger: Logger = Logger.getLogger("GomokuApplication")
    }
}
