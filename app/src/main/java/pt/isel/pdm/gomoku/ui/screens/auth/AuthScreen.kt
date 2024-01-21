package pt.isel.pdm.gomoku.ui.screens.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import pt.isel.pdm.gomoku.R
import pt.isel.pdm.gomoku.ui.screens.TestTags.Auth.authLoginButtonTestTag
import pt.isel.pdm.gomoku.ui.screens.TestTags.Auth.authRegisterButtonTestTag
import pt.isel.pdm.gomoku.ui.screens.TestTags.Auth.authTestTag
import pt.isel.pdm.gomoku.ui.screens.home.components.SettingsButton
import pt.isel.pdm.gomoku.ui.utils.GomokuIcon
import pt.isel.pdm.gomoku.ui.utils.Spacer
import pt.isel.pdm.gomoku.ui.utils.TextButton

@Composable
fun AuthScreen(
    onRegisterRequest: () -> Unit,
    onLoginRequest: () -> Unit,
    onSettings: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .testTag(authTestTag),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SettingsButton(onSettings)
        GomokuIcon(size = 400.dp)
        Spacer(50.dp)
        TextButton(
            onClick = onRegisterRequest,
            text = stringResource(R.string.register),
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .testTag(authRegisterButtonTestTag)
        )
        Spacer(10.dp)
        TextButton(
            onClick = onLoginRequest,
            text = stringResource(R.string.login),
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .testTag(authLoginButtonTestTag)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun AuthScreenPreview() {
    AuthScreen(onRegisterRequest = {}, onLoginRequest = {})
}
