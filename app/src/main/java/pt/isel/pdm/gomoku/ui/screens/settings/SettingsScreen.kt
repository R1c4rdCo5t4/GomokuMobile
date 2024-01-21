package pt.isel.pdm.gomoku.ui.screens.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import pt.isel.pdm.gomoku.R
import pt.isel.pdm.gomoku.ui.screens.TestTags.Settings.settingsDarkThemeButtonTestTag
import pt.isel.pdm.gomoku.ui.screens.TestTags.Settings.settingsLogoutButtonTestTag
import pt.isel.pdm.gomoku.ui.screens.TestTags.Settings.settingsScreenTestTag
import pt.isel.pdm.gomoku.ui.screens.TestTags.Settings.settingsSoundButtonTestTag
import pt.isel.pdm.gomoku.ui.screens.TestTags.Settings.settingsVibrationButtonTestTag
import pt.isel.pdm.gomoku.ui.screens.settings.components.SettingsSwitch
import pt.isel.pdm.gomoku.ui.utils.ScaffoldTopAppBar
import pt.isel.pdm.gomoku.ui.utils.Spacer
import pt.isel.pdm.gomoku.ui.utils.TextButton

@Composable
fun SettingsScreen(
    isLoggedIn: Boolean,
    soundOn: Boolean,
    vibrationOn: Boolean,
    darkTheme: Boolean,
    onSoundToggle: () -> Unit = {},
    onVibrationToggle: () -> Unit = {},
    onThemeToggle: () -> Unit = {},
    onLogoutRequest: () -> Unit = {},
    onBackRequest: () -> Unit = {}
) {
    Scaffold(
        topBar = {
            ScaffoldTopAppBar(
                title = stringResource(R.string.settings),
                onNavigationButtonClick = onBackRequest
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(MaterialTheme.colorScheme.surface)
                .testTag(settingsScreenTestTag),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth(0.5f)
            ) {
                SettingsSwitch(
                    stringResource(R.string.sound),
                    soundOn,
                    Modifier.testTag(settingsSoundButtonTestTag),
                    onSoundToggle
                )
                SettingsSwitch(
                    stringResource(R.string.vibration),
                    vibrationOn,
                    Modifier.testTag(settingsVibrationButtonTestTag),
                    onVibrationToggle
                )
                SettingsSwitch(
                    stringResource(R.string.dark_theme),
                    darkTheme,
                    Modifier.testTag(settingsDarkThemeButtonTestTag),
                    onThemeToggle
                )
                if (isLoggedIn) {
                    Spacer(50.dp)
                    TextButton(
                        onClick = onLogoutRequest,
                        text = stringResource(R.string.logout),
                        modifier = Modifier.testTag(settingsLogoutButtonTestTag)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SettingsScreenPreview() {
    SettingsScreen(
        isLoggedIn = false,
        soundOn = true,
        vibrationOn = true,
        darkTheme = true
    )
}
