package pt.isel.pdm.gomoku.ui.screens.home.components

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pt.isel.pdm.gomoku.R
import pt.isel.pdm.gomoku.ui.utils.Spacer

@Composable
fun ProfileButton(
    onClick: () -> Unit,
    username: String
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
    ) {
        Icon(
            imageVector = Icons.Filled.Person,
            contentDescription = stringResource(R.string.user_icon),
            modifier = Modifier.size(24.dp),
            tint = MaterialTheme.colorScheme.inverseOnSurface
        )
        Spacer(10.dp)
        Text(
            text = username,
            color = MaterialTheme.colorScheme.inverseOnSurface,
            fontSize = 16.sp
        )
    }
}
