package pt.isel.pdm.gomoku.ui.screens.auth.register

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
import androidx.compose.material.icons.filled.AlternateEmail
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
import pt.isel.pdm.gomoku.ui.screens.TestTags.Register.registerButtonTestTag
import pt.isel.pdm.gomoku.ui.screens.TestTags.Register.registerConfirmPasswordTestTag
import pt.isel.pdm.gomoku.ui.screens.TestTags.Register.registerEmailTestTag
import pt.isel.pdm.gomoku.ui.screens.TestTags.Register.registerPasswordTestTag
import pt.isel.pdm.gomoku.ui.screens.TestTags.Register.registerTestTag
import pt.isel.pdm.gomoku.ui.screens.TestTags.Register.registerUsernameTestTag
import pt.isel.pdm.gomoku.ui.utils.PasswordTextField
import pt.isel.pdm.gomoku.ui.utils.Spacer
import pt.isel.pdm.gomoku.ui.utils.TextButton
import pt.isel.pdm.gomoku.ui.utils.TextField

@Composable
fun RegisterScreen(
    registerEnable: Boolean = true,
    onRegisterRequest: (String, String, String, String) -> Unit
) {
    var username by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var confirmPassword by rememberSaveable { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .testTag(registerTestTag),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.register),
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
                    value = username,
                    label = stringResource(R.string.username),
                    icon = Icons.Filled.AccountCircle,
                    modifier = Modifier.testTag(registerUsernameTestTag),
                    onValueChange = { username = it }
                )
                TextField(
                    value = email,
                    label = stringResource(R.string.email),
                    icon = Icons.Filled.AlternateEmail,
                    modifier = Modifier.testTag(registerEmailTestTag),
                    onValueChange = { email = it }
                )
                PasswordTextField(
                    value = password,
                    label = stringResource(R.string.password),
                    modifier = Modifier.testTag(registerPasswordTestTag),
                    onValueChange = { password = it }
                )
                PasswordTextField(
                    value = confirmPassword,
                    label = stringResource(R.string.confirm_password),
                    modifier = Modifier.testTag(registerConfirmPasswordTestTag),
                    onValueChange = { confirmPassword = it }
                )
                Spacer(20.dp)
                TextButton(
                    onClick = {
                        onRegisterRequest(
                            username.trim(),
                            email.trim(),
                            password.trim(),
                            confirmPassword.trim()
                        )
                    },
                    text = stringResource(R.string.register),
                    enabled = registerEnable,
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag(registerButtonTestTag)
                )
            }
        }
        Spacer(50.dp)
    }
}

@Preview(showBackground = true)
@Composable
fun RegisterScreenPreview() {
    RegisterScreen(onRegisterRequest = { _, _, _, _ -> })
}
