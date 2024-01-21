package pt.isel.pdm.gomoku.ui.screens.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pt.isel.pdm.gomoku.R
import pt.isel.pdm.gomoku.ui.screens.TestTags.Home.mainExitButtonTestTag
import pt.isel.pdm.gomoku.ui.screens.TestTags.Home.mainPlayButtonTestTag
import pt.isel.pdm.gomoku.ui.screens.TestTags.Home.mainScreenTestTag
import pt.isel.pdm.gomoku.ui.utils.GomokuIcon
import pt.isel.pdm.gomoku.ui.utils.Spacer
import pt.isel.pdm.gomoku.ui.utils.TextButton

@Composable
fun MainScreen(
    username: String,
    onPlayGame: () -> Unit = {},
    onSettings: () -> Unit = {},
    onMyProfileRequest: () -> Unit = {},
    onExit: () -> Unit = {}
) {
    Column(
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .testTag(mainScreenTestTag)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SettingsButton(onSettings)
            Text(stringResource(R.string.app_name), fontSize = 55.sp)
            GomokuIcon(size = 250.dp)
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextButton(
                onClick = onPlayGame,
                text = stringResource(R.string.play),
                fontSize = 20.sp,
                modifier = Modifier
                    .fillMaxWidth(0.35f)
                    .height(50.dp)
                    .testTag(mainPlayButtonTestTag)
            )
            Spacer(10.dp)
            TextButton(
                onClick = onExit,
                text = stringResource(R.string.exit),
                modifier = Modifier
                    .fillMaxWidth(0.25f)
                    .height(40.dp)
                    .testTag(mainExitButtonTestTag)
            )
            Spacer(50.dp)
            ProfileButton(
                onClick = onMyProfileRequest,
                username = username
            )
            Spacer(100.dp)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    MainScreen(username = "Ricardo")
}
