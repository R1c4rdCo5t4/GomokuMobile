package pt.isel.pdm.gomoku.ui.screens.settings.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Switch
import androidx.compose.material.SwitchDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import pt.isel.pdm.gomoku.R
import pt.isel.pdm.gomoku.ui.controllers.SoundController
import pt.isel.pdm.gomoku.ui.theme.PrimaryTransparent
import pt.isel.pdm.gomoku.ui.utils.Spacer

@Composable
fun SettingsSwitch(
    name: String,
    isOn: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    val context = LocalContext.current
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(name)
        Spacer(20.dp)
        Switch(
            checked = isOn,
            modifier = modifier,
            onCheckedChange = {
                onClick()
                SoundController.play(context, R.raw.switch_sound)
            },
            colors = SwitchDefaults.colors(
                checkedThumbColor = MaterialTheme.colorScheme.primary,
                checkedTrackColor = PrimaryTransparent,
                uncheckedThumbColor = MaterialTheme.colorScheme.inverseSurface,
                uncheckedTrackColor = MaterialTheme.colorScheme.inverseOnSurface
            )
        )
    }
}
