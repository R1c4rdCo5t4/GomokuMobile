package pt.isel.pdm.gomoku.ui.screens.auth.login

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pt.isel.pdm.gomoku.R
import pt.isel.pdm.gomoku.ui.screens.TestTags.Login.loginButtonTestTag
import pt.isel.pdm.gomoku.ui.screens.TestTags.Login.loginPasswordTestTag
import pt.isel.pdm.gomoku.ui.screens.TestTags.Login.loginTestTag
import pt.isel.pdm.gomoku.ui.screens.TestTags.Login.loginUsernameOrEmailTestTag
import pt.isel.pdm.gomoku.ui.utils.PasswordTextField
import pt.isel.pdm.gomoku.ui.utils.Spacer
import pt.isel.pdm.gomoku.ui.utils.TextButton
import pt.isel.pdm.gomoku.ui.utils.TextField

@Composable
fun LoginScreen(
    loginEnable: Boolean = true,
    onLoginRequest: (String, String) -> Unit = { _, _ -> },
    onBackRequest: () -> Unit = {}
) {
    var usernameOrEmail by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }

    BackHandler(onBack = onBackRequest)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .testTag(loginTestTag),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.login),
            modifier = Modifier,
            fontSize = 50.sp,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(50.dp)
        Box(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .border(1.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(5))
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 35.dp)
            ) {
                TextField(
                    value = usernameOrEmail,
                    label = stringResource(R.string.username_or_email),
                    icon = Icons.Filled.AccountCircle,
                    modifier = Modifier.testTag(loginUsernameOrEmailTestTag),
                    onValueChange = { usernameOrEmail = it }
                )
                Spacer(10.dp)
                PasswordTextField(
                    value = password,
                    label = stringResource(R.string.password),
                    modifier = Modifier.testTag(loginPasswordTestTag),
                    onValueChange = { password = it }
                )
                Spacer(20.dp)
                TextButton(
                    onClick = { onLoginRequest(usernameOrEmail.trim(), password.trim()) },
                    text = stringResource(R.string.login),
                    enabled = loginEnable,
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag(loginButtonTestTag)
                )
            }
        }
        Spacer(50.dp)
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    LoginScreen(onLoginRequest = { _, _ -> })
}
