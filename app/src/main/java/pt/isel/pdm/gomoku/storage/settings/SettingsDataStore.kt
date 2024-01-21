package pt.isel.pdm.gomoku.storage.settings

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.first
import pt.isel.pdm.gomoku.ui.controllers.SoundController
import pt.isel.pdm.gomoku.ui.controllers.VibrationController

class SettingsDataStore(private val store: DataStore<Preferences>) : Settings {

    override suspend fun isDarkModeOn() = store.data.first()[DARK_MODE_KEY]?.toBoolean() ?: true

    override suspend fun isSoundOn() = (store.data.first()[SOUND_KEY]?.toBoolean() ?: true).also {
        SoundController.enabled = it
    }

    override suspend fun isVibrationOn() = (store.data.first()[VIBRATION_KEY]?.toBoolean() ?: true).also {
        VibrationController.enabled = it
    }

    override suspend fun setDarkMode(isDarkMode: Boolean) {
        store.edit {
            it[DARK_MODE_KEY] = isDarkMode.toString()
        }
    }

    override suspend fun setSound(isSoundOn: Boolean) {
        store.edit {
            it[SOUND_KEY] = isSoundOn.toString()
        }
        SoundController.enabled = isSoundOn
    }

    override suspend fun setVibration(isVibrationOn: Boolean) {
        store.edit {
            it[VIBRATION_KEY] = isVibrationOn.toString()
        }
        VibrationController.enabled = isVibrationOn
    }

    companion object {
        private val DARK_MODE_KEY = stringPreferencesKey("darkModeKey")
        private val SOUND_KEY = stringPreferencesKey("soundKey")
        private val VIBRATION_KEY = stringPreferencesKey("vibrationKey")
    }
}
