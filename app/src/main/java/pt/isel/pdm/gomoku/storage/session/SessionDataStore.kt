package pt.isel.pdm.gomoku.storage.session

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.google.gson.Gson
import kotlinx.coroutines.flow.first
import pt.isel.pdm.gomoku.domain.user.Stats
import pt.isel.pdm.gomoku.domain.user.User

class SessionDataStore(private val store: DataStore<Preferences>) : Session {

    override suspend fun getToken() =
        store.data.first()[TOKEN_KEY] ?: throw UserNotLoggedInException()

    override suspend fun getUser() =
        store.data.first()[USER_KEY]?.let {
                json ->
            gson.fromJson(json, User::class.java)
        } ?: throw UserNotLoggedInException()

    override suspend fun getUserLink() =
        store.data.first()[USER_LINK_KEY] ?: throw UserNotLoggedInException()

    override suspend fun getUserName() = getUser().name

    override suspend fun isLoggedIn() = store.data.first()[TOKEN_KEY] != null

    override suspend fun save(token: String, user: User, userLink: String) {
        store.edit {
            it[TOKEN_KEY] = token
            it[USER_KEY] = gson.toJson(user)
            it[USER_LINK_KEY] = userLink
        }
    }

    override suspend fun updateUserStats(stats: Stats) {
        val user = getUser().copy(stats = stats)
        store.edit {
            it[USER_KEY] = gson.toJson(user)
        }
    }

    override suspend fun delete() {
        store.edit {
            it.remove(TOKEN_KEY)
            it.remove(USER_KEY)
            it.remove(USER_LINK_KEY)
        }
    }

    companion object {
        private val gson = Gson()
        private val TOKEN_KEY = stringPreferencesKey("tokenKey")
        private val USER_KEY = stringPreferencesKey("userKey")
        private val USER_LINK_KEY = stringPreferencesKey("userLinkKey")
    }
}
