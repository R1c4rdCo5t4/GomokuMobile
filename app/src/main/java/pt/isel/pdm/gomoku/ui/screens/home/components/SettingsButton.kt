package pt.isel.pdm.gomoku.ui.screens.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import pt.isel.pdm.gomoku.ui.screens.TestTags.General.settingsButtonTestTag
import pt.isel.pdm.gomoku.ui.theme.GomokuTheme
import pt.isel.pdm.gomoku.ui.utils.IconButton

@Composable
fun SettingsButton(onSettingsRequest: () -> Unit = {}) {
    Row(
        horizontalArrangement = Arrangement.End,
        modifier = Modifier.fillMaxWidth()
    ) {
        IconButton(
            onClick = onSettingsRequest,
            icon = Icons.Filled.Settings,
            contentDescription = "Settings",
            buttonColor = Color.Transparent,
            iconColor = MaterialTheme.colorScheme.secondary,
            modifier = Modifier
                .clip(CircleShape)
                .size(80.dp)
                .testTag(settingsButtonTestTag)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SettingsButtonPreview() {
    GomokuTheme {
        Surface(Modifier.fillMaxSize()) {
            SettingsButton()
        }
    }
}
