package pt.isel.pdm.gomoku.storage.session

import pt.isel.pdm.gomoku.domain.user.Stats
import pt.isel.pdm.gomoku.domain.user.User

interface Session {
    suspend fun getToken(): String
    suspend fun getUser(): User
    suspend fun getUserLink(): String
    suspend fun getUserName(): String
    suspend fun isLoggedIn(): Boolean
    suspend fun save(token: String, user: User, userLink: String)
    suspend fun updateUserStats(stats: Stats)
    suspend fun delete()
}
