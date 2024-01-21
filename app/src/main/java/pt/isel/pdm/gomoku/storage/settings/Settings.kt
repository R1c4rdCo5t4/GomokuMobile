package pt.isel.pdm.gomoku.storage.settings

interface Settings {
    suspend fun isDarkModeOn(): Boolean
    suspend fun isSoundOn(): Boolean
    suspend fun isVibrationOn(): Boolean
    suspend fun setDarkMode(isDarkMode: Boolean)
    suspend fun setSound(isSoundOn: Boolean)
    suspend fun setVibration(isVibrationOn: Boolean)
}
