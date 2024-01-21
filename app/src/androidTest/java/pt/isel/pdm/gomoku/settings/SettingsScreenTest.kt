package pt.isel.pdm.gomoku.settings

import androidx.compose.ui.test.junit4.createComposeRule
import junit.framework.TestCase.assertTrue
import org.junit.Rule
import org.junit.Test
import pt.isel.pdm.gomoku.assertContentExists
import pt.isel.pdm.gomoku.assertContentNotExists
import pt.isel.pdm.gomoku.clickButton
import pt.isel.pdm.gomoku.ui.screens.TestTags.Settings.settingsDarkThemeButtonTestTag
import pt.isel.pdm.gomoku.ui.screens.TestTags.Settings.settingsLogoutButtonTestTag
import pt.isel.pdm.gomoku.ui.screens.TestTags.Settings.settingsScreenTestTag
import pt.isel.pdm.gomoku.ui.screens.TestTags.Settings.settingsSoundButtonTestTag
import pt.isel.pdm.gomoku.ui.screens.TestTags.Settings.settingsVibrationButtonTestTag
import pt.isel.pdm.gomoku.ui.screens.settings.SettingsScreen

class SettingsScreenTest {

    @get:Rule
    val testRule = createComposeRule()

    @Test
    fun settings_screen_initial_state_when_not_logged_in() {
        testRule.setContent {
            SettingsScreen(isLoggedIn = false, soundOn = false, vibrationOn = false, darkTheme = false)
        }
        testRule.assertContentExists(
            settingsScreenTestTag,
            settingsSoundButtonTestTag,
            settingsVibrationButtonTestTag,
            settingsDarkThemeButtonTestTag
        )
        testRule.assertContentNotExists(settingsLogoutButtonTestTag)
    }

    @Test
    fun settings_screen_initial_state_when_logged_in() {
        testRule.setContent {
            SettingsScreen(isLoggedIn = true, soundOn = false, vibrationOn = false, darkTheme = false)
        }
        testRule.assertContentExists(
            settingsScreenTestTag,
            settingsSoundButtonTestTag,
            settingsVibrationButtonTestTag,
            settingsDarkThemeButtonTestTag,
            settingsLogoutButtonTestTag
        )
    }

    @Test
    fun click_on_sound_button() {
        // Arrange
        var soundClicked = false
        testRule.setContent {
            SettingsScreen(
                isLoggedIn = false,
                soundOn = soundClicked,
                vibrationOn = false,
                darkTheme = false,
                onSoundToggle = { soundClicked = true }
            )
        }
        // Act
        testRule.clickButton(settingsSoundButtonTestTag)
        // Assert
        assertTrue(soundClicked)
    }

    @Test
    fun click_on_vibration_button() {
        // Arrange
        var vibrationClicked = false
        testRule.setContent {
            SettingsScreen(
                isLoggedIn = false,
                soundOn = false,
                vibrationOn = vibrationClicked,
                darkTheme = false,
                onVibrationToggle = { vibrationClicked = true }
            )
        }
        // Act
        testRule.clickButton(settingsVibrationButtonTestTag)
        // Assert
        assertTrue(vibrationClicked)
    }

    @Test
    fun click_on_dark_theme_button() {
        // Arrange
        var darkThemeClicked = false
        testRule.setContent {
            SettingsScreen(
                isLoggedIn = false,
                soundOn = false,
                vibrationOn = false,
                darkTheme = darkThemeClicked,
                onThemeToggle = { darkThemeClicked = true }
            )
        }
        // Act
        testRule.clickButton(settingsDarkThemeButtonTestTag)
        // Assert
        assertTrue(darkThemeClicked)
    }

    @Test
    fun click_on_logout_button() {
        // Arrange
        var logoutClicked = false
        testRule.setContent {
            SettingsScreen(
                isLoggedIn = true,
                soundOn = false,
                vibrationOn = false,
                darkTheme = false,
                onLogoutRequest = { logoutClicked = true }
            )
        }
        // Act
        testRule.clickButton(settingsLogoutButtonTestTag)
        // Assert
        assertTrue(logoutClicked)
    }
}
